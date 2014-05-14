package uk.ac.brunel.cs.fyp

import uk.ac.brunel.cs.fyp.config.Config
import uk.ac.brunel.cs.fyp.model.assessment._
import uk.ac.brunel.cs.fyp.model.Grade
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.ss.usermodel.Cell
import uk.ac.brunel.cs.fyp.model.Submission
import java.io.{FileInputStream, FileOutputStream, File}
import scala.Some
import uk.ac.brunel.cs.fyp.model.assessment.SingleMarkerAssessment
import uk.ac.brunel.cs.fyp.model.assessment.UnconfirmedDoubleMarkerAssessment

class MarkingEngine {

  val readers = List(new File(Config.markingsheets), new File(Config.agreementInDirectory)).
                  map(file => new MarkingSheetReader(file))

  // Initialise the Student Registry
  val reg = StudentRegistry
  
  val markingSheets = readers.map(_.processMarkingSheets).flatten

  val assessments: List[SingleMarkerAssessment] = markingSheets.
      filter(_ match { case m: ExcelMarkingSheet => true case _ => false}).
      map(m => m match {
        case ms: ExcelMarkingSheet => new SingleMarkerAssessment(
          reg.addSubmission(reg.addStudent(ms.studentNumber), ms.programme, ms.title),
          ms.marker,
          ms.programmeRequirements,
          ms.learningOutcomes,
          ms.minimumStandardsText,
          ms.justification,
          Some(new Grade(ms.grade)))
  })
  
  reg.addAssessments(assessments)

  val agreements: List[Agreement] =  markingSheets.
    filter(_ match { case m: ExcelAgreementSheet => true case _ => false}).
    map(m => m match {
      case as: ExcelAgreementSheet => new Agreement(
        reg.getSubmission(reg.students(as.studentNumber)) match {
          case Some(submission: Submission) => submission
          case None => throw new IllegalStateException("No submission found for student " + as.studentNumber)
        },
        if(as.agreed) {
          Some(new Grade(as.grade))
        } else None,
        as.justification)
  })

  reg.addAgreements(agreements)

  def outputToXLSX {
    val outFile: File = new File(Config.outputfile)
    val workbook = new XSSFWorkbook()

    val sheet = workbook.createSheet("Student Marks")

    var rowNumber = 0;

    reg.submissions.values.map(s => { addAssessmentToSheet(s, sheet, rowNumber); rowNumber += 1; })

    val fileOutStream = new FileOutputStream(outFile)
    workbook.write(fileOutStream)
    fileOutStream.close()
  }

  def addAssessmentToSheet(submission: Submission, sheet: XSSFSheet, row: Int): XSSFSheet = {
    submission.student.number

    val assessment = StudentRegistry.assessment(submission) match {
      case Some(a: Assessment) => a
      case None => throw new IllegalStateException("Assessment for submission cannot be found")
    }

    // Submssion Details
    val rb = new RowBuilder(sheet, row)
    rb.addNextCell(submission.student.number)
    rb.addNextCell(submission.programme)
    rb.addNextCell(submission.title)

    // Assessment Details
    assessment match {
      case sma: SingleMarkerAssessment => {
        addSingleMarkerAssessment(rb, sma)
        rb.addBlankCells(10)
      }
      case dma: DoubleMarkerAssessment => {
        addSingleMarkerAssessment(rb, dma.assessment1)
        addSingleMarkerAssessment(rb, dma.assessment2)
        if (dma.isFinal) {
          dma.grade match {
            case Some(g: Grade) => rb.addNextCell(g.gradePoint.toDouble)
            case None => throw new IllegalStateException("If Assessment is final, grade must be present")
          }
        } else {
          rb.addNextCell("X")
        }
      }
    }

    sheet
  }

  def addSingleMarkerAssessment(rb: RowBuilder, sma: SingleMarkerAssessment) {
    rb.addNextCell(sma.marker.name)
    rb.addNextCell(sma.programmeRequirements.justification);
    rb.addNextCell(sma.programmeRequirements.met);
    rb.addNextCell(sma.learningOutcomes.problemDefinition)
    rb.addNextCell(sma.learningOutcomes.backgroundInvestigation)
    rb.addNextCell(sma.learningOutcomes.practicalApplication)
    rb.addNextCell(sma.learningOutcomes.evaluation)
    rb.addNextCell(sma.learningOutcomes.management)
    rb.addNextCell(sma.learningOutcomes.comunication)
    rb.addNextCell(sma.minimumStandards)
    rb.addNextCell(sma.justification)
    sma.grade match {
      case Some(g: Grade) => rb.addNextCell(g.gradePoint.toDouble)
      case None => rb.addBlankCells(1)
    }

  }

  class RowBuilder(sheet: XSSFSheet, rowNumber: Int) {
    val row = sheet.createRow(rowNumber)
    var col = 0;
    def addNextCell(value: Any) {
      value match {
        case b: Boolean => row.createCell(col).setCellValue(b)
        case s: String => row.createCell(col).setCellValue(s)
        case d: Double => row.createCell(col).setCellValue(d)
        case _ => throw new IllegalArgumentException(value + " cannot be written to cell")
      }

      col += 1;
    }
    def addBlankCells(n: Int) {
      for (i <- (1 to n)) {
        row.createCell(col, Cell.CELL_TYPE_BLANK)
        col += 1
      }

    }
  }

  def outputAgreementSheet(assessment: Assessment) {
    val templateFile = new File(Config.agreementTemplate)
    val destinationDirectory = new File(Config.agreementOutDirectory)

    if (!destinationDirectory.exists()) {
      println(destinationDirectory + " does not exist")
      System.exit(-1)
    }

    val agreement = new ExcelAgreementSheet(templateFile)

    assessment match {
      case dma: UnconfirmedDoubleMarkerAssessment => {
        agreement.studentNumber = dma.submission.student.number.trim.toInt
        agreement.firstGrade = dma.assessment1.grade match { case Some(g: Grade) => g.grade }
        agreement.secondGrade = dma.assessment2.grade match { case Some(g: Grade) => g.grade }
        agreement.programme = dma.submission.programme
        agreement.title = dma.submission.title
        agreement.marker = dma.assessment1.marker.name;
        agreement.secondMarker = dma.assessment2.marker.name;

        if (dma.requiresAgreement) {
          agreement.grade = "";
          agreement.justification = Config.requiresAgreementText
        } else if (dma.eligibleForAgreement) {
          agreement.grade = (new AutomaticAgreedDoubleMarkerAssessment(dma).grade) match { case Some(g: Grade) => g.grade };
          agreement.justification = Config.eligibleForAgreementText
        }
      }
    }

    def recordAgreementSheets= {

    }

    val outFile = new File(destinationDirectory, "Grade Agreement" + assessment.submission.student.number + ".xlsx")
    val fileOutStream = new FileOutputStream(outFile)

    agreement.writeToFile(fileOutStream)

    fileOutStream.close()
  }

  def outputModerationSheet(assessment: Assessment) {
    val templateFile = new File(Config.moderationTemplate)
    val destinationDirectory = new File(Config.moderationOutDirectory)

    if (!destinationDirectory.exists()) {
      println(destinationDirectory + " does not exist")
      System.exit(-1)
    }

    val moderationSheet = new ExcelModerationSheet(templateFile)


    assessment match {
      case dma: DoubleMarkerAssessment => {
        moderationSheet.studentNumber = dma.submission.student.number.trim.toInt
        moderationSheet.firstGrade = dma.assessment1.grade match { case Some(g: Grade) => g.grade }
        moderationSheet.secondGrade = dma.assessment2.grade match { case Some(g: Grade) => g.grade }
        moderationSheet.programme = dma.submission.programme
        moderationSheet.title = dma.submission.title
        moderationSheet.marker = dma.assessment1.marker.name;
        moderationSheet.secondMarker = dma.assessment2.marker.name;

        moderationSheet.grade = "";

        dma match {
          case unconfirmed: UnconfirmedDoubleMarkerAssessment => {
            moderationSheet.justification = Config.requiresModerationBecauseOfGradeDifference
          }
          case disagreed: DisagreedDoubleMarkerAssessment => {
            moderationSheet.justification = Config.requiresModerationBecauseOfMarkerDisagreement
          }
        }
      }
    }


    val outFile = new File(destinationDirectory, "Grade Moderation" + assessment.submission.student.number + ".xlsx")
    val fileOutStream = new FileOutputStream(outFile)

    moderationSheet.writeToFile(fileOutStream)

    fileOutStream.close()
  }

  def generateAgreementSheets {
	  StudentRegistry.assessments.filter(_._2.requiresAgreement).map(a => outputAgreementSheet(a._2))
  }

  def generateModerationSheets {
    StudentRegistry.assessments.filter(_._2.requiresModeration).map(a => outputModerationSheet(a._2))
  }

  def autoAgree {
    StudentRegistry.addAssessments(
      StudentRegistry.assessments.
        filter(a => a._2.gradeDifference==0 || (a._2.eligibleForAgreement && !a._2.requiresAgreement)).
        filter(a => a._2 match { case dma: UnconfirmedDoubleMarkerAssessment => true; case _ => false}).
      map(b => b._2 match {
            case dma: UnconfirmedDoubleMarkerAssessment => new AutomaticAgreedDoubleMarkerAssessment(dma)
          }).toSeq
    )
  }
}