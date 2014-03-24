package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade

class AgreedDoubleMarkerAssessment(unmoderatedAssessment: UnmoderatedDoubleMarkerAssessment, agreedGrade: Grade, justification: String) extends DoubleMarkerAssessment {
	if (!unmoderatedAssessment.assessmentsWithinLimits) {
	  throw new IllegalArgumentException("Cannot create an Agreed Double Marker Assessment unless two grades are within limits")
	}
	val gp1 = assessment1.grade match {
	  case Some(gp: Grade) => gp.gradePoint
	  case None => throw new IllegalArgumentException("Something has gone wrong!")
	}
	val gp2 = assessment2.grade match {
	  case Some(gp: Grade) => gp.gradePoint
	  case None => throw new IllegalArgumentException("Something has gone wrong!")
	}
	if (agreedGrade.gradePoint>Math.max(gp1, gp2) || 
		agreedGrade.gradePoint<Math.min(gp1, gp2)){
	  throw new IllegalArgumentException("Markers cannot agree a grade outside the original range");
	}
	
	def assessment1 = unmoderatedAssessment.assessment.assessment1 
    def assessment2 = unmoderatedAssessment.assessment.assessment2
	
	def grade: Option[Grade] = Some(agreedGrade);
	
	def isFinal = true
}