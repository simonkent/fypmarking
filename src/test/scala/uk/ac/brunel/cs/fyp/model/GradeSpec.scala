package uk.ac.brunel.cs.fyp.model

class GradeSpec extends UnitSpec {
  
	val validGrades = List("A*","A+","A","A-",
					    "B+","B","B-",
					    "C+","C","C-",
					    "D+","D","D-",
					    "E+","E","E-",
					    "F")
  val validGradePoints = List(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17).reverse
    
    
    
	"A grade" should "be created with a String between F and A* which correspond to gradepoints 1..17" in {
	  assert(validGrades.map(g => new Grade(g)).map(g => g.gradePoint) == validGradePoints) 
    }
	
	"A grade" should "also be created with an Int between 1..17 which corresponds to grades F..A*" in {
	  assert(validGradePoints.map(gp => new Grade(gp)).map(g => g.grade) == validGrades) 
    }
}