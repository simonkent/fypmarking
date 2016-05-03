/*
 * This is a static definition of the named ranges used in the marking spreadsheet to extract the relevant information
 * Arguably this could be defined dynamically in some kind of configuration file, however as the logic of the programme 
 * is pretty bound up with the valued extracted from the marking sheet, it is not unreasonable to leave everything statically 
 * defined so people can't break it.
 */
package uk.ac.brunel.cs.fyp

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.util.AreaReference
import org.apache.poi.xssf.usermodel.{XSSFCell, XSSFWorkbook}
import uk.ac.brunel.cs.fyp.model.Marker
import java.io.{FileOutputStream, FileInputStream, File}
import com.sun.org.apache.xpath.internal.objects.XStringForChars

class ExcelSheet(val file: File, val workbook: XSSFWorkbook) {
  def this(file: File) {
    this(file, new XSSFWorkbook(new FileInputStream(file)))
  }

  def this(parser: ExcelSheetParser) {
    this(parser.file, parser.workbook)
  }

  def studentNumber: String = {
    "%7d".format(getIntValueFromNamedCell("Student_Number"))
  }

  def studentNumber_=(number: Int) {
    setIntInNamedCell(number, "Student_Number")
  }
  
 def marker: Marker = {
    new Marker(getStringValueFromNamedCell("Marker"))
  }

  def marker_= (marker: String) {
    setStringInNamedCell(marker, "Marker")
  }
  
  def grade: String = {
    getStringValueFromNamedCell("Grade")
  }

  def grade_=(grade: String) {
    setStringInNamedCell(grade, "Grade")
  }

  def programme: String = {
    getStringValueFromNamedCell("Programme")
  }

  def programme_=(programme: String) {
    setStringInNamedCell(programme, "Programme")
  }

  def title: String = {
    getStringValueFromNamedCell("Title")
  }

  def title_=(title: String) {
    setStringInNamedCell(title, "Title")
  }

  def justification: String = {
    getStringValueFromNamedCell("Justification_Text")
  }

  def justification_=(text: String) {
    setStringInNamedCell(text, "Justification_Text")
  }

   def getIntValueFromNamedCell(name: String): Int = {
    getValueFromNamedCell(name) match {
      case Some(i: Int) => i
      case Some(d: Double) => d.toInt
      case None => 0
      case _ => throw new IllegalArgumentException("Contents of cell named \"" + name + "\" in file " + file.getName() + " cannot be returned as an Integer")
    }
  }

  private def getDoubleValueFromNamedCell(name: String): Double = {
    getValueFromNamedCell(name) match {
      case Some(d: Double) => d
      case None => 0.0
      case _ => throw new IllegalArgumentException("Contents of cell named \"" + name + "\" in file " + file.getName() + " cannot be returned as a Double")
    }
  }

  protected def getBooleanValueFromNamedCell(name: String): Boolean = {
    getValueFromNamedCell(name) match {
      case Some(b: Boolean) => b
      case None => throw new IllegalStateException("Cannot return Boolean from a blank cell in file " + file.getName())
      case _ => throw new IllegalArgumentException("cell named \" " + name + "\" cannot be returned as a Boolean in file " + file.getName())
    }
  }

  protected def getStringValueFromNamedCell(name: String): String = {
    getValueFromNamedCell(name) match {
      case Some(s: String) => s
      case Some(d: Double) => d.toString
      case Some(i: Int) => i.toString
      case None => ""
      case _ => throw new IllegalArgumentException("Contents of cell named \"" + name + "\" in file " + file.getName() + " cannot be returned as a String")
    }
  }

  private def getNamedXSSFCell(name: String):XSSFCell= {
    val namedCell = workbook.getNameAt(workbook.getNameIndex(name))

    val reference = new AreaReference(namedCell.getRefersToFormula())

    if (!reference.isSingleCell()) {
      throw new IllegalArgumentException(name + " reference should refer to a single cell")
    }

    val cellRef = reference.getFirstCell

    val sheet = workbook.getSheet(cellRef.getSheetName);
    val row = sheet.getRow(cellRef.getRow);
    val cell = row.getCell(cellRef.getCol);

    cell
  }
  private def getValueFromNamedCell(name: String): Option[Any] = {

    val cell: XSSFCell = getNamedXSSFCell(name)

    cell.getCellType match {
      case Cell.CELL_TYPE_NUMERIC => Some(cell.getNumericCellValue())
      case Cell.CELL_TYPE_STRING => Some(cell.getStringCellValue())
      case Cell.CELL_TYPE_BOOLEAN => Some(cell.getBooleanCellValue())
      case Cell.CELL_TYPE_BLANK => None
      case _ => throw new IllegalStateException("Cell Value Not Supported in Cell (" + name + ") (R" + cell.getRowIndex + "C" + cell.getColumnIndex + ") in " + file.getName);
    }
  }
      

  protected def setStringInNamedCell(value: String, name: String) {
    val cell: XSSFCell = getNamedXSSFCell(name)

    cell.setCellValue(value)
  }

  protected def setIntInNamedCell(i: Int, name: String) {
    val cell: XSSFCell = getNamedXSSFCell(name)

    cell.setCellValue(i.toDouble)

  }


  def writeToFile(stream: FileOutputStream) = {
    workbook.write(stream)
  }

}

private class ExcelSheetParser(val file: File) {
  val workbook = new XSSFWorkbook(new FileInputStream(file))

  val docType: String = getDocType(workbook)

  def execute: ExcelSheet = {
    if (docType == "MA") {
      new ExcelAgreementSheet(this)
    } else if (docType == "MM") {
      new ExcelModerationSheet(this)
    } else if (docType == "MS"){
      new ExcelMarkingSheet(this)
    } else {
      throw new IllegalStateException("Somthing bad has happened")
    }
  }

  private def getDocType(workbook: XSSFWorkbook): String = {
    val index = workbook.getNameIndex("DocType")
    
    if (index<0) {
      // then we are using an old version of the Marking Sheet Template so just default to "MS"
      "MS"
    } else {
       val docTypeCell = workbook.getNameAt(index)
      
    val reference = new AreaReference(docTypeCell.getRefersToFormula())

    if (!reference.isSingleCell()) {
      throw new IllegalArgumentException("DocType is incorrectly defined")
    }

    val cellRef = reference.getFirstCell

    val sheet = workbook.getSheet(cellRef.getSheetName);
    val row = sheet.getRow(cellRef.getRow);
    val cell = row.getCell(cellRef.getCol);

    cell.getStringCellValue()
    }
  }
}

object ExcelSheet {
  def parse(file: File): ExcelSheet = {
    val parser = new ExcelSheetParser(file)
    parser.execute
  }

}

