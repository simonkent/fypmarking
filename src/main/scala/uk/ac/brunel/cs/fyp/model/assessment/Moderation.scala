package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade

case class Moderation(grade: Option[Grade], submission: Submission, justification: String) extends Assessment with Justification {
  def isFinal = true
}