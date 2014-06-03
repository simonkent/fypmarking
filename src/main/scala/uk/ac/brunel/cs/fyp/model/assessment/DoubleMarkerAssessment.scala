package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission

trait DoubleMarkerAssessment extends Assessment {

  def assessment1: SingleMarkerAssessment
  def assessment2: SingleMarkerAssessment
  if (assessment1.marker==assessment2.marker) {
    throw new AssessmentException("Two assessments for single submission (" + submission.student.number + ") must have different markers" + assessment1.marker + "!=" + assessment2.marker)
  }
  if (assessment1.submission != assessment2.submission) {
    throw new IllegalArgumentException("Two assessments must be for the same submission" + assessment1.submission + "!=" + assessment2.submission)
  }

  def submission: Submission = {assessment1.submission}

  protected val g1 = checkedGrade(assessment1.grade)
  protected val g2 = checkedGrade(assessment2.grade)

  protected def assessmentsWithinLimits: Boolean= {
    gradeDifference<=2
  }

  protected def assessmentsWithinSameGradeBoundary: Boolean= {
    (g1 withinSameGradeBoundary g2)
  }

  def gradeDifference:Int = {
    (g1 diff g2)
  }

  def isDoubleMarked: Boolean = true
  def isFinal: Boolean

  def status: String

}