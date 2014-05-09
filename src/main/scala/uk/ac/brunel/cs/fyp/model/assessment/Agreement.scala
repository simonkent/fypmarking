package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Grade
import uk.ac.brunel.cs.fyp.model.Submission

case class Agreement(grade: Option[Grade], submission: Submission, justification: String) extends AssessmentAnnotation {

}