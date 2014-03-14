package uk.ac.brunel.cs.fyp

import java.io.File
import java.io.FilenameFilter
import scala.Array.canBuildFrom

class MarkingSheetReader(dirString: String) {  
  val directory = new File(dirString)
  
  if (!directory.exists()) {
    throw new IllegalArgumentException("Path does not exist");
  }
  
  if (!directory.isDirectory()) {
    throw new IllegalArgumentException("Path is not a directory");
  }
  
  
  def processMarkingSheets():List[ExcelMarkingSheet] ={
    // get xlsx
    // TODO pull out the excel specific stuff in case marks are submitted in another form 
    val filter = new FilenameFilter() {
        override def accept(dir: File, fileName: String): Boolean={
            fileName.endsWith(".xlsx") && !fileName.startsWith("~")
        }
    }
  
   directory.listFiles(filter).map(file => { println("Reading file " + file) ; 
   											 new ExcelMarkingSheet(file) }).toList
   
  }
  
}