package uk.ac.brunel.cs.fyp.apps

import uk.ac.brunel.cs.fyp.model.assessment.DoubleMarkerAssessment
import uk.ac.brunel.cs.fyp.StudentRegistry
import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade
import uk.ac.brunel.cs.fyp.model.assessment.Assessment
import uk.ac.brunel.cs.fyp.model.assessment.SingleMarkerAssessment
import uk.ac.brunel.cs.fyp.MarkingSheetReader
import java.io.File
import java.io.FileOutputStream
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.ss.usermodel.Cell
import uk.ac.brunel.cs.fyp.model.assessment.AssessmentException
import uk.ac.brunel.cs.fyp.ExcelMarkingSheet
import uk.ac.brunel.cs.fyp.MarkingEngine

object CollateMarkingSheets extends App {

  val fileList = List(new File("/Users/simonkent/Desktop/Marking Sheets"))
  val engine = new MarkingEngine(fileList)
  
  engine.outputToXLSX(new File("/Users/simonkent/Desktop/Results/out.xlsx"))
  
  //reg.addAgreements(agreements)

  // Here we need confirm assessments that can be automatically agreed
  
}