package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Grade

class AgreedDoubleMarkerAssessment(unconfirmedAssessment: UnconfirmedDoubleMarkerAssessment, agreement: Agreement) extends DoubleMarkerAssessment {
	val agreedGrade = checkedGrade(agreement.grade)
  
	if (!assessmentsWithinLimits) {
	  throw new IllegalArgumentException(assessment1.submission.student + " Cannot create an Agreed Double Marker Assessment unless two grades are within limits")
	}

	if (!agreedGradeWithinBoundary) {
	  throw new IllegalArgumentException(assessment1.submission.student + " Cannot create an Agreed Double Marker Assessment unless proposed grades are within boundaries")
	}

  protected def agreedGradeWithinBoundary: Boolean = {
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

  override def requiresModeration: Boolean = !agreedGradeWithinBoundary

  override def requiresAgreement: Boolean = false

  override def eligibleForAgreement: Boolean = false

  override def status: String = "Agreed: " + agreement.justification
}