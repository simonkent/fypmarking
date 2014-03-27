package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Grade
import uk.ac.brunel.cs.fyp.model.Submission

case class UnconfirmedDoubleMarkerAssessment(
		assessment1: SingleMarkerAssessment,
		assessment2: SingleMarkerAssessment) extends DoubleMarkerAssessment {
    
    def grade: Option[Grade] = None 
    
    def isFinal = false
}