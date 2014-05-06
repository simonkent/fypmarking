package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade

class AgreedDoubleMarkerAssessment(unconfirmedAssessment: UnconfirmedDoubleMarkerAssessment, agreement: Agreement) extends DoubleMarkerAssessment {
	val g1 = checkedGrade(assessment1.grade)
	val g2 = checkedGrade(assessment2.grade)
	val agreedGrade = checkedGrade(agreement.grade)
  
	if (!assessmentsWithinLimits) {
	  throw new IllegalArgumentException("Cannot create an Agreed Double Marker Assessment unless two grades are within limits")
	}

	if (!agreedGradeWithinBoundary) {
	  throw new IllegalArgumentException("Cannot create an Agreed Double Marker Assessment unless proposed grades are within boundaries")
	}
	
	private def assessmentsWithinLimits: Boolean= { 
		  (g1 withinSameGradeBoundary g2) &&
		  (g1 diff g2)<=2 
    }
	
	private def agreedGradeWithinBoundary: Boolean= {
		  agreedGrade <= Grade.max(g1, g2) &&
		  agreedGrade >= Grade.min(g1, g2)
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

	def assessment1 = unconfirmedAssessment.assessment1
    def assessment2 = unconfirmedAssessment.assessment2
	
	def grade: Option[Grade] = Some(agreedGrade);
	
	def isFinal = true
}