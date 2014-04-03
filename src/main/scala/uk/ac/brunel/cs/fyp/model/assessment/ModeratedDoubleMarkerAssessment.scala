package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade

class ModeratedDoubleMarkerAssessment(unconfirmedAssessment: UnconfirmedDoubleMarkerAssessment, moderation: Moderation) extends DoubleMarkerAssessment with Justification {
	def grade: Option[Grade] = moderation.grade
	override def submission: Submission = unconfirmedAssessment.submission

  def justification = moderation.justification

  override def isFinal: Boolean = true

  override def assessment2: SingleMarkerAssessment = unconfirmedAssessment.assessment1

  override def assessment1: SingleMarkerAssessment = unconfirmedAssessment.assessment2
}