package uk.ac.brunel.cs.fyp.assessment

import uk.ac.brunel.cs.fyp.Grade
import uk.ac.brunel.cs.fyp.Submission

class ModeratedDoubleMarkerAssessment(unmoderatedAssessment: UnmoderatedDoubleMarkerAssessment, moderation: Moderation) extends Assessment {
	def grade: Option[Grade] = ??? 
	def submission: Submission = ???
}