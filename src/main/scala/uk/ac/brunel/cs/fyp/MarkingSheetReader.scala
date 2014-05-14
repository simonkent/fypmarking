package uk.ac.brunel.cs.fyp

import java.io.FilenameFilter
import java.io.File


class MarkingSheetReader(val directory: File) {
  def this(dirString: String) {
    this(new File(dirString))
  }
  
  if (!directory.exists()) {
    throw new IllegalArgumentException("Path does not exist");
  }
  
  if (!directory.isDirectory()) {
    throw new IllegalArgumentException("Path is not a directory");
  }
  
  
  def processMarkingSheets():List[ExcelSheet] ={
    val filter = new FilenameFilter() {
        override def accept(dir: File, fileName: String): Boolean={
            fileName.endsWith(".xlsx") && !fileName.startsWith("~")
        }
    }
  
   directory.listFiles(filter).map(file => { println("Reading file " + file) ; 
   											 ExcelSheet.parse(file) })./*
   							   filter(_ match {
   							     case e: ExcelMarkingSheet => true
   							     case _ => false
   							     }).*/
   							   toList
   
  }
  
}