package uk.ac.brunel.cs.fyp

import scala.util.Random

object TestMarkingSheetGenerator extends Application {
	val directory = "/Users/simonkent/Desktop/Marking Sheets"
	
	val r = new Random()
	
	val numberOfStudents = 100;
	
	val students: Map[Int, String] = (1 to numberOfStudents).zip((1 to numberOfStudents).map(n => "%07d".format(r.nextInt(9999999)))).toMap
	    
	val studentsChosen = scala.collection.mutable.Map[String, Int]() ++ students.map(i => (i._2, 0))
	  
	// iterate m times
	// choose student (max twice)
	
	val i = 0
	while (i<10) {
	  val stu = students(r.nextInt(numberOfStudents-1)+1);
	  studentsChosen(stu) match {
	    case 0 => println("1st marker"); 
	    case 1 => println("2nd marker") 
	    case _ => println("Reject")
	  }
	}
	
	def firstMarker {
	  
	}
}