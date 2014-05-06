package uk.ac.brunel.cs.fyp.model

case class Grade(gradePoint: Int) {
	def this(grade: String)= {
	  this(Grade.gradeToGradePoint(grade))
	}
  
	def grade: String= {
	  Grade.gradePointToGrade(gradePoint)
	}
	
	def withinSameGradeBoundary(other: Grade):Boolean ={
	  this.grade.charAt(0)==other.grade.charAt(0)
	}

	// TODO can I achieve this with Ordering[Grade]
  def >(other: Grade):Boolean ={
    return this.gradePoint>other.gradePoint
  }
  
    
  def <(other: Grade):Boolean ={
    return this.gradePoint<other.gradePoint
  }
  
  def <=(other: Grade):Boolean ={
    return this.gradePoint<=other.gradePoint
  }
    
  def >=(other: Grade):Boolean ={
    return this.gradePoint>=other.gradePoint
  }


  def -(other: Grade):Int ={
    this.gradePoint-other.gradePoint
  }

  def diff(other: Grade):Int ={
    if (this > other) {
      this - other
    } else {
      other - this
    }
  }
  
}

object Grade {
  
  val gradePointToGrade: Map[Int,String] = Map(1->"F",2->"E-",3->"E",4->"E+",5->"D-",6->"D",7->"D+",8->"C-",9->"C",10->"C+",11->"B-",12->"B",13->"B+",14->"A-",15->"A",16->"A+",17->"A*")
  val gradeToGradePoint: Map[String,Int] = gradePointToGrade.foldLeft(Map[String,Int]())((acc, m) => acc + (m._2->m._1))
  def max(x: Grade, y: Grade): Grade = if (x > y) x else y
  def min(x: Grade, y: Grade): Grade = if (x > y) y else x
}


