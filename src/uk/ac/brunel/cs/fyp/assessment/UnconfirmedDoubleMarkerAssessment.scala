package uk.ac.brunel.cs.fyp.assessment

import uk.ac.brunel.cs.fyp.Grade
import uk.ac.brunel.cs.fyp.Submission

case class UnconfirmedDoubleMarkerAssessment(
		assessment1: SingleMarkerAssessment,
		assessment2: SingleMarkerAssessment) extends DoubleMarkerAssessment {
    
    def grade: Option[Grade] = None 
    
    def isFinal = false
}