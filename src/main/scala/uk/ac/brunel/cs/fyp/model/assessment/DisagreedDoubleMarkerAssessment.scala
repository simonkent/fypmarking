package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Grade

class DisagreedDoubleMarkerAssessment(unconfirmedAssessment: UnconfirmedDoubleMarkerAssessment, agreement: Agreement) extends DoubleMarkerAssessment {
  println("Creating a Disagreement")
	agreement.grade match {
    case Some(grade) => throw new IllegalArgumentException("Cannot create a DisagreedDoubleMarkerAssessment with a satisfactory agreement. Markers must be in conflict.")
    case None => /* this is ok */ ;
  }

	if (gradeDifference==0) {
	  throw new IllegalArgumentException("Cannot create a DisagreedDoubleMarkerAssessment when grades match")
	}

	def assessment1 = unconfirmedAssessment.assessment1
  def assessment2 = unconfirmedAssessment.assessment2
	
	def grade: Option[Grade] = None
	
	def isFinal = false

  override def requiresModeration: Boolean = true

  override def requiresAgreement: Boolean = false

  override def eligibleForAgreement: Boolean = false

  override def status: String = "Disagreement: " + agreement.justification
}