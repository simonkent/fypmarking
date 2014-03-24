package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade

class ModeratedDoubleMarkerAssessment(unmoderatedAssessment: UnmoderatedDoubleMarkerAssessment, moderation: Moderation) extends Assessment {
	def grade: Option[Grade] = ??? 
	def submission: Submission = ???
}