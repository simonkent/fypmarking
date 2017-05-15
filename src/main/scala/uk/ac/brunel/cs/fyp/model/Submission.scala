package uk.ac.brunel.cs.fyp.model

trait Submission {

	def student: Student
	def programme: String
	def title: String
	
	override def equals(other: Any)= {
	  other match {
	    case s: Submission => {
	      this.student==s.student && this.programme==s.programme && this.title==s.title
	    }
	    case _ => false
	  }
	}
}