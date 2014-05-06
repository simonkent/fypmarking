package uk.ac.brunel.cs.fyp

import java.io.File
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import uk.ac.brunel.cs.fyp.model.Marker

class ExcelAgreementSheet(file: File, workbook: XSSFWorkbook) extends ExcelSheet(file, workbook) {
  def secondMarker: Marker = {
    new Marker(getStringValueFromNamedCell("Second_Marker"))
  }
  
  def firstGrade: String = {
    getStringValueFromNamedCell("Grade1")
  }
  
  def secondGrade: String = {
    getStringValueFromNamedCell("Grade2")
  }
	
  def agreed: Boolean = {
    getBooleanValueFromNamedCell("Agreed")
  }
  
  
}