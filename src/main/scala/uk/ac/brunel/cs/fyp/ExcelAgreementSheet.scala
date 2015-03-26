package uk.ac.brunel.cs.fyp

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import uk.ac.brunel.cs.fyp.model.Marker
import java.io.{FileOutputStream, FileInputStream, File}

class ExcelAgreementSheet(file: File) extends AbstractDisagreementResolutionSheet(file, new XSSFWorkbook(new FileInputStream(file))) {

  def this(parser: ExcelSheetParser) {
    this(parser.file)
  }

  def agreed: Boolean = {
    getBooleanValueFromNamedCell("Agreed")
  }
}