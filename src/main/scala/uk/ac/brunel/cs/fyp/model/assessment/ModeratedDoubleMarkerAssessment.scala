package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade

// TODO: Need to introduce a Requires moderation trait, so that this only accepts an assessment with this trait, so that we don't rely on a call to .requiresModeration during construction
class ModeratedDoubleMarkerAssessment(dma: DoubleMarkerAssessment, moderation: Moderation) extends DoubleMarkerAssessment with Justification {
  if (!dma.requiresModeration) throw new IllegalArgumentException("Should not construct ModeratedDoubleMarkerAssessment from an assessment that doesn't require moderation for submission: " + dma.submission)
	def grade: Option[Grade] = moderation.grade
	override def submission: Submission = dma.submission

  def justification = moderation.justification

  override def assessment2: SingleMarkerAssessment = dma.assessment1

  override def assessment1: SingleMarkerAssessment = dma.assessment2

  override def isFinal: Boolean = true

  override def requiresModeration: Boolean = false

  override def requiresAgreement: Boolean = false

  override def eligibleForAgreement: Boolean = false
}