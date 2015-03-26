package uk.ac.brunel.cs.fyp

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import uk.ac.brunel.cs.fyp.model.Marker
import java.io.{FileInputStream, File}

class ExcelModerationSheet(file: File) extends AbstractDisagreementResolutionSheet(file, new XSSFWorkbook(new FileInputStream(file))) {

  def this(parser: ExcelSheetParser) {
    this(parser.file)
  }

}