package uk.ac.brunel.cs.fyp

import scala.collection.mutable.Map
import uk.ac.brunel.cs.fyp.assessment.SingleMarkerAssessment
import uk.ac.brunel.cs.fyp.assessment.DoubleMarkerAssessment
import uk.ac.brunel.cs.fyp.assessment.UnconfirmedDoubleMarkerAssessment
import uk.ac.brunel.cs.fyp.assessment.Assessment

case class ConcreteStudent(val number: String) extends Student
case class ConcreteSubmission(val student: Student, val programme: String, val title: String) extends Submission

object StudentRegistry {
	var students = Map[String, Student]()
	var submissions = Map[(Student, String), Submission]()
	var assessments = Map[Submission, Assessment]()
	
	def addStudent(studentNo: String):Student ={
	  if(students.contains(studentNo)) {
	    students(studentNo)
	  } else {
	    val student = new ConcreteStudent(studentNo)
	    students += (studentNo -> student);
	    student
	  }
	}
	
	def getStudent(studentNo: String): Option[Student] ={
	  students(studentNo) match {
	    case stu: Student => Option(stu)
	    case _ => None
	  }
	}
	
	def addSubmission(student: Student, programme: String, title: String):Submission ={
	  new ConcreteSubmission(student, programme, title)
	}
	
	def getSubmission(student: Student, title: String): Option[Submission] ={
	  submissions((student, title)) match {
	    case sub: Submission => Option(sub)
	    case _ => None
	  }
	}
	
	def addAssessments(assessments: Seq[Assessment]) {
	  assessments.map(assessment => recordAssessment(assessment))
	  
	}
	
	def recordAssessment(assessment: Assessment) {
	  val submission = getSubmission(assessment.submission.student, assessment.submission.title) match {
	    case Some(s: Submission) => s
	    case None => throw new IllegalStateException("No submission exists for this assessment");
	  }
	  
	  assessment match {
	    case ass1: SingleMarkerAssessment => assessments += submission -> (assessments(submission) match {
	    	case ass2: SingleMarkerAssessment => new UnconfirmedDoubleMarkerAssessment(ass1, ass2)    
	    	case _ => throw new IllegalStateException("This should never happen!")
	  }	 )
	    case ass: DoubleMarkerAssessment => assessments += submission -> ass
	  }
	  	  
	}
}