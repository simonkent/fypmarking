package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Grade
import uk.ac.brunel.cs.fyp.model.Submission

case class UnmoderatedDoubleMarkerAssessment(assessment: UnconfirmedDoubleMarkerAssessment) extends DoubleMarkerAssessment {
  //private final val assessment1 = assessment.assessment1
  //private final val assessment2 = assessment.assessment1
  
  def grade: Option[Grade] = {
    if (assessmentsWithinLimits) {
      val gp1 = checkedGrade(assessment1.grade).gradePoint;
      val gp2 = checkedGrade(assessment2.grade).gradePoint;
 
      Some(Grade((gp1 +gp2) / 2));
    } else {
      None
    }
  }
  
  def assessmentsWithinLimits: Boolean= {
    return checkedGrade(assessment1.grade).withinSameGradeBoundary(
        checkedGrade(assessment2.grade))
  }
  
  def assessment1 = assessment.assessment1 
  def assessment2 = assessment.assessment2 
  
  def isFinal = false
}