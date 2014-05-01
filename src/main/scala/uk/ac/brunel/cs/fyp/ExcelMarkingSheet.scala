/*
 * This is a static definition of the named ranges used in the marking spreadsheet to extract the relevant information
 * Arguably this could be defined dynamically in some kind of configuration file, however as the logic of the programme 
 * is pretty bound up with the valued extracted from the marking sheet, it is not unreasonable to leave everything statically 
 * defined so people can't break it.
 */
package uk.ac.brunel.cs.fyp

import java.io.File
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import org.apache.poi.ss.util.AreaReference
import org.apache.poi.ss.usermodel.Cell
import uk.ac.brunel.cs.fyp.model.ProgrammeRequirements
import uk.ac.brunel.cs.fyp.model.Marker
import uk.ac.brunel.cs.fyp.model.LearningOutcomes
import uk.ac.brunel.cs.fyp.model.ProgrammeRequirements

class ExcelMarkingSheet(file: File) {
   private val workbook = new XSSFWorkbook(new FileInputStream(file))
	  
   private def getStringValueFromNamedCell(name: String):String ={
    getValueFromNamedCell(name) match {
      case Some(s: String) => s
      case Some(d: Double) => d.toString
      case Some(i: Int) => i.toString
      case None => ""
      case _ => throw new IllegalArgumentException("Contents of cell named \"" + name + "\" in file " + file.getName() + " cannot be returned as a String")
    }
  }
   
   private def getIntValueFromNamedCell(name: String): Int ={
     getValueFromNamedCell(name) match {
      case Some(i: Int) => i
      case Some(d: Double) => d.toInt
      case None => 0
      case _ => throw new IllegalArgumentException("Contents of cell named \"" + name + "\" in file " + file.getName() + " cannot be returned as an Integer")
    }
   }
   
   private def getDoubleValueFromNamedCell(name: String): Double ={
     getValueFromNamedCell(name) match {
      case Some(d: Double) => d
      case None => 0.0
      case _ => throw new IllegalArgumentException("Contents of cell named \"" + name + "\" in file " + file.getName() + " cannot be returned as a Double")
    }
   }
   
   private def getBooleanValueFromNamedCell(name: String): Boolean ={
     getValueFromNamedCell(name) match {
      case Some(b: Boolean) => b
      case None => throw new IllegalStateException("Cannot return Boolean from a blank cell in file " + file.getName())
      case _ => throw new IllegalArgumentException("cell named \" " + name + "\" cannot be returned as a Boolean in file " + file.getName())
    }
   }
  
  private def getValueFromNamedCell(name: String):Option[Any] ={
    val namedCell = workbook.getNameAt(workbook.getNameIndex(name))
    
    val reference = new AreaReference(namedCell.getRefersToFormula())
    
    if (!reference.isSingleCell()) {
      throw new IllegalArgumentException(name + " reference should refer to a single cell")
    }
    
    val cellRef = reference.getFirstCell
    
    val sheet = workbook.getSheet(cellRef.getSheetName);
    val row = sheet.getRow(cellRef.getRow);
    val cell = row.getCell(cellRef.getCol);
    
    
	cell.getCellType match {
	  case Cell.CELL_TYPE_NUMERIC => Some(cell.getNumericCellValue())
	  case Cell.CELL_TYPE_STRING => Some(cell.getStringCellValue())
	  case Cell.CELL_TYPE_BOOLEAN => Some(cell.getBooleanCellValue())
	  case Cell.CELL_TYPE_BLANK => None
	  case _ => throw new IllegalStateException("Cell Value Not Supported")
	}
    
  }
  
  def studentNumber: String= {
    "%7d".format(getIntValueFromNamedCell("Student_Number"))
  }
    
  def grade: String= {
    getStringValueFromNamedCell("Grade")
  }
  
  def programme: String= {
    getStringValueFromNamedCell("Programme")
  }
  
  def title: String= {
    getStringValueFromNamedCell("Title")
  }
  
  def marker: Marker= {
   new Marker(getStringValueFromNamedCell("Marker"))
  }
  
  def learningOutcomes: LearningOutcomes= {
    new LearningOutcomes(
        getBooleanValueFromNamedCell("LO_Problem_Definition"),
        getBooleanValueFromNamedCell("LO_Background"),
        getBooleanValueFromNamedCell("LO_Practical_Application"),
        getBooleanValueFromNamedCell("LO_Evaluation"),
        getBooleanValueFromNamedCell("LO_Management_And_Evaluation"),
        getBooleanValueFromNamedCell("LO_Communication")
    )
  }
  
  def programmeRequirements: ProgrammeRequirements ={
    new ProgrammeRequirements(
        getBooleanValueFromNamedCell("Programme_Requirements"),
        getStringValueFromNamedCell("Programme_Requirements_Text")
    )
  }
  
  def minimumStandardsText: String ={
    getStringValueFromNamedCell("Standards_Not_Met_Text")
  }
  
  def justification: String ={
    getStringValueFromNamedCell("Justification_Text")
  }
}

