package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission

trait DoubleMarkerAssessment extends Assessment {
  def assessment1: SingleMarkerAssessment
  def assessment2: SingleMarkerAssessment
  if (assessment1.marker==assessment2.marker) {
    throw new AssessmentException("Two assessments for single submission must have different markers" + assessment1.marker + "!=" + assessment2.marker)
  }
  if (assessment1.submission != assessment2.submission) {
    throw new IllegalArgumentException("Two assessments must be for the same submission" + assessment1.submission + "!=" + assessment2.submission)
  }	
  def submission: Submission = {assessment1.submission}
  
  def isFinal: Boolean
}