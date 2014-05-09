package uk.ac.brunel.cs.fyp

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import uk.ac.brunel.cs.fyp.model.Marker
import java.io.{FileOutputStream, FileInputStream, File}

class ExcelAgreementSheet(file: File) extends ExcelSheet(file, new XSSFWorkbook(new FileInputStream(file))) {

  def this(parser: ExcelSheetParser) {
    this(parser.file)
  }

  def secondMarker: Marker = {
    new Marker(getStringValueFromNamedCell("Second_Marker"))
  }

  def secondMarker_= (marker: String) {
    setStringInNamedCell(marker, "Second_Marker")
  }
  
  def firstGrade: String = {
    getStringValueFromNamedCell("Grade1")
  }

  def firstGrade_=(grade: String) {
    setStringInNamedCell(grade, "Grade1")
  }
  
  def secondGrade: String = {
    getStringValueFromNamedCell("Grade2")
  }

  def secondGrade_=(grade: String) {
    setStringInNamedCell(grade, "Grade2")
  }

  def agreed: Boolean = {
    getBooleanValueFromNamedCell("Agreed")
  }
}