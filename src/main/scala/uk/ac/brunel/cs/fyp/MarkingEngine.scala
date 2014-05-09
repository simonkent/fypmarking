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

  val readers = List(new File(Config.markingsheets)).map(file => new MarkingSheetReader(file))

  // Initialise the Student Registry
  val reg = StudentRegistry
  
  val markingSheets = readers.map(_.processMarkingSheets).flatten

  val assessments: List[SingleMarkerAssessment] = markingSheets.map(m => m match {
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
    val destinationDirectory = new File(Config.agreementDirectory)

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


    val outFile = new File(destinationDirectory, "Grade Agreement" + assessment.submission.student.number + ".xlsx")
    val fileOutStream = new FileOutputStream(outFile)

    agreement.writeToFile(fileOutStream)

    fileOutStream.close()
  }

  def generateAgreementSheets {
	  StudentRegistry.assessments.filter(_._2.requiresAgreement).map(a => outputAgreementSheet(a._2))
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