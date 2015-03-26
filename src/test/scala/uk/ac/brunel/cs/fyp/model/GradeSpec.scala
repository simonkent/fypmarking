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
	
	it should "also be created with an Int between 1..17 which corresponds to grades F..A*" in {
	  assert(validGradePoints.map(gp => new Grade(gp)).map(g => g.grade) == validGrades) 
    }

  "Subtraction of a grade from itself" should "equal 0" in {
    val g = new Grade("A*")
    assert(g-g==0)
  }

  "Subtraction of grades" should "work" in {
    assert(new Grade("A*") - new Grade("C") == 8)
  }

  "The difference between a grade and itself" should "be zero" in {
    val a = new Grade("A")
    val b = new Grade("B")
    assert((a diff b)==(b diff a))
  }

  it should "be calculated correctly" in {
    assert((new Grade("A") diff new Grade("C"))==6)
  }
}