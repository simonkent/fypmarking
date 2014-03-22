package uk.ac.brunel.cs.fyp

import scala.collection.mutable.Map
import uk.ac.brunel.cs.fyp.assessment.SingleMarkerAssessment
import uk.ac.brunel.cs.fyp.assessment.DoubleMarkerAssessment
import uk.ac.brunel.cs.fyp.assessment.UnconfirmedDoubleMarkerAssessment
import uk.ac.brunel.cs.fyp.assessment.Assessment
import uk.ac.brunel.cs.fyp.assessment.UnconfirmedDoubleMarkerAssessment

case class ConcreteStudent(val number: String) extends Student
case class ConcreteSubmission(val student: Student, val programme: String, val title: String) extends Submission

object StudentRegistry {
	var students = Map[String, Student]()
	var submissions = Map[(Student, String), Submission]()
	var assessments = scala.collection.immutable.Map[Submission, Assessment]()
	
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
	  if (submissions.contains((student, title))) {
	    Some(submissions((student, title)))
	  } else {
	     None
	  }
	}
	
	def addAssessments(assessments: Seq[SingleMarkerAssessment]) {
	  assessments.map(assessment => recordAssessment(assessment))
	}
	
	def recordAssessment(assessment: SingleMarkerAssessment) {
	  val submission = getSubmission(assessment.submission.student, assessment.submission.title) match {
	    case Some(s: Submission) => s
	    case None => addStudent(assessment.submission.student.number); addSubmission(assessment.submission.student, assessment.submission.programme, assessment.submission.title)
	  }
	  
	  if (assessments.contains(submission)) {
	    assessments = assessments.map(ass => 
	    	if (ass._1!=submission) {
	    	  ass
	    	} else {
	    	  ass._2 match {
	    	  	case sma: SingleMarkerAssessment => submission->new UnconfirmedDoubleMarkerAssessment(sma, assessment)
	    	  	case _ => throw new IllegalStateException("Two assessments already exist for student " + submission.student)
	    	  }
	    	}
	    )
	  } else {
	    assessments = assessments + (submission -> assessment)
	  }
	}
}  
