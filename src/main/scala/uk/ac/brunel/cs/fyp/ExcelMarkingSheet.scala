package uk.ac.brunel.cs.fyp

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import uk.ac.brunel.cs.fyp.model.LearningOutcomes
import uk.ac.brunel.cs.fyp.model.ProgrammeRequirements
import java.io.{FileInputStream, File}

class ExcelMarkingSheet(file: File) extends ExcelSheet(file, new XSSFWorkbook(new FileInputStream(file))) {
  def this(parser: ExcelSheetParser) {
    this(parser.file)
  }

  def learningOutcomes: LearningOutcomes = {
    new LearningOutcomes(
      getBooleanValueFromNamedCell("LO_Problem_Definition"),
      getBooleanValueFromNamedCell("LO_Background"),
      getBooleanValueFromNamedCell("LO_Practical_Application"),
      getBooleanValueFromNamedCell("LO_Evaluation"),
      getBooleanValueFromNamedCell("LO_Management_And_Evaluation"),
      getBooleanValueFromNamedCell("LO_Communication"),
      getBooleanValueFromNamedCell("LO_Ethics"))
  }
  
  def programmeRequirements: ProgrammeRequirements = {
    new ProgrammeRequirements(
      getBooleanValueFromNamedCell("Programme_Requirements"),
      getStringValueFromNamedCell("Programme_Requirements_Text"))
  }
  
  def minimumStandardsText: String = {
    getStringValueFromNamedCell("Standards_Not_Met_Text")
  }
}