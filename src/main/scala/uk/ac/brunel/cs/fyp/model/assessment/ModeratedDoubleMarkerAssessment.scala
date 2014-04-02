package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade

class ModeratedDoubleMarkerAssessment(unmoderatedAssessment: UnmoderatedDoubleMarkerAssessment, moderation: Moderation) extends Assessment with Justification {
	def grade: Option[Grade] = moderation.grade
	def submission: Submission = unmoderatedAssessment.submission

  def justification = moderation.justification

  override def isFinal: Boolean = true
}