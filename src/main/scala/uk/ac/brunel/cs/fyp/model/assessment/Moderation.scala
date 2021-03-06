package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade

case class Moderation(submission: Submission, grade: Option[Grade] , justification: String) extends AssessmentAnnotation with Justification {
  def isFinal = true
}