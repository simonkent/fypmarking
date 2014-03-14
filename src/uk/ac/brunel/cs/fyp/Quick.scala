package uk.ac.brunel.cs.fyp

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream

object Quick extends App {
  
   //val workbook = new XSSFWorkbook(new FileInputStream("/Users/simonkent/Dropbox/Brunel/FYP/Task 2 Assessment/Marking Scheme.xlsx"))
   val workbook = new XSSFWorkbook(new FileInputStream("/Users/simonkent/Desktop/Marking Sheets/12345.xlsx"))
  
   val nameCount = workbook.getNumberOfNames
   
   println (nameCount + " named ranges")
      
   println ( (0 to nameCount-1).foldLeft("")((acc, i) => acc + workbook.getNameAt(i).getNameName() + "\n") )
}