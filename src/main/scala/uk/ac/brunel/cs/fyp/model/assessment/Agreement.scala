package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Grade
import uk.ac.brunel.cs.fyp.model.Submission

case class Agreement(submission: Submission, grade: Option[Grade], justification: String) extends AssessmentAnnotation {

}