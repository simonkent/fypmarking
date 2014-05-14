package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Grade

case class UnconfirmedDoubleMarkerAssessment(
		assessment1: SingleMarkerAssessment,
		assessment2: SingleMarkerAssessment) extends DoubleMarkerAssessment {

  def grade: Option[Grade] = None
    
  def isFinal = false

  override def requiresModeration: Boolean = !assessmentsWithinLimits

  override def requiresAgreement: Boolean = assessmentsWithinLimits && !assessmentsWithinSameGradeBoundary

  override def eligibleForAgreement: Boolean = assessmentsWithinSameGradeBoundary && g1!=g2
}