package uk.ac.brunel.cs.fyp

import scala.collection.mutable.Map
import uk.ac.brunel.cs.fyp.model.assessment._
import uk.ac.brunel.cs.fyp.model.{Grade, Student, Submission}
import uk.ac.brunel.cs.fyp.ConcreteStudent
import scala.Some
import uk.ac.brunel.cs.fyp.model.assessment.SingleMarkerAssessment
import uk.ac.brunel.cs.fyp.ConcreteSubmission
import uk.ac.brunel.cs.fyp.model.assessment.UnconfirmedDoubleMarkerAssessment
import uk.ac.brunel.cs.fyp.model.assessment.Agreement

case class ConcreteStudent(val number: String) extends Student {
  
}

case class ConcreteSubmission(val student: Student, val programme: String, val title: String) extends Submission {
  override def equals(other: Any)= {
    other match {
      case that: Submission => this.student==that.student && this.programme==that.programme && this.title.equalsIgnoreCase(that.title) 
    }
  }
}

object StudentRegistry {
	var students = Map[String, Student]()
	var submissions = Map[Student, Submission]()
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
	  val key = student
	  val value = new ConcreteSubmission(student, programme, title)
	  submissions += (key -> value)
	  value
	}
	
	def getSubmission(student: Student): Option[Submission] ={
	  if (submissions.contains(student)) {
	    Some(submissions(student))
	  } else {
	     None
	  }
	}
	
	def addAssessments(assessments: Seq[Assessment]) {
	  assessments.map(assessment => {
	    try {
	    	recordAssessment(assessment)
	    } catch {
			case e: IllegalStateException => println ("Illegal State:" + e.getMessage())
			case e: AssessmentException => println("Assessment Exception" + e.getMessage())
	    }
	  })
	}
	
	def assessment(submission: Submission):Option[Assessment] ={
	  if (assessments.contains(submission)) {
	    Some(assessments(submission))
	  } else {
	    None
	  }
	}
	
	private def recordAssessment(assessment: Assessment) {
	  val submission = getSubmission(assessment.submission.student) match {
	    case Some(s: Submission) => s
	    case None => addStudent(assessment.submission.student.number); addSubmission(assessment.submission.student, assessment.submission.programme, assessment.submission.title)
	  }
	  
	  if (submission.student==assessment.submission.student && submission!=assessment.submission) {
	      	  throw new IllegalStateException("Inconsistent Submissions Detected:\n\tExisting:" + submission + "\n\tNew:     " + assessment.submission)
	      }
	  
	  if (assessments.contains(submission)) {
	    assessments = assessments.map(existing =>
	      	
	    	if (existing._1!=submission) {
	    	  existing
	    	} else {
	    	  existing._2 match {
	    	  	case existingSma: SingleMarkerAssessment => {
              assessment match {
                case newSma: SingleMarkerAssessment => submission->new UnconfirmedDoubleMarkerAssessment(newSma, existingSma)
                case _ => throw new IllegalStateException("Incompatible Assessment being added for " + submission.student)
              }
            }
            // Replace an existing DMA with a new one
            case existingDma: DoubleMarkerAssessment => {
              assessment match {
                case newDma: DoubleMarkerAssessment => submission->newDma
              }
            }
	    	  	case _ => throw new IllegalStateException("Two assessments already exist for student " + submission.student)
	    	  }
	    	}
	    )
	  } else {
	    assessments += submission -> assessment
	  }
	}
	
	def addAgreements(agreements: Seq[Agreement])= {
	  agreements.map(agreement => recordAgreement(agreement))
	}
	
	private def recordAgreement(agreement: Agreement) {
		val submission = getSubmission(agreement.submission.student) match {
		  case Some(s: Submission) => s;
		  case None => throw new IllegalStateException("No submission for agreement: " + agreement);
		}
		val existingAssessment = assessment(submission) match {
		    case Some(udma: UnconfirmedDoubleMarkerAssessment) => udma
		    case _ => throw new IllegalStateException("Unconfirmed Double Marker Assessment does not exist for submission: " + submission);
		}
		
		val agreedAssessment =
      agreement.grade match {
        case Some(g: Grade) => new AgreedDoubleMarkerAssessment(existingAssessment, agreement)
        case None => new DisagreedDoubleMarkerAssessment(existingAssessment, agreement)
      }
		
		assessments += submission -> agreedAssessment
	}
}  
