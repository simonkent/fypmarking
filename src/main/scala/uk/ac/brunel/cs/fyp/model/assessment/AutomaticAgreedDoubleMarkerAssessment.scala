package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Grade

/**
 * Created by simonkent on 02/04/2014.
 */
class AutomaticAgreedDoubleMarkerAssessment(unconfirmedAssessment: UnconfirmedDoubleMarkerAssessment) extends DoubleMarkerAssessment with Justification {
  val grade1 = assessment1.grade match {
    case Some (g: Grade) => g;
    case _ => throw new AssessmentException ("Invalid grade in assessment 1")
  }

  val grade2 = assessment2.grade match {
    case Some(g: Grade) => g;
    case _ => throw new AssessmentException("Invalid grade in assessment 2")
  }
  // add some checks here to ensure it is in range
  override def isFinal: Boolean = true

  override def grade: Option[Grade] ={
    // if a = b then a
    // if |a - b| = 1 then max(a, b)
    // if |a - b| = 2 then min(a, b) + 1 (i.e. the midpoint)
    assert(((grade1 diff grade2) > 2) == false, "An AutomaticAgreedDoubleMarkerAssessment should never have grade difference > 2")

    if ((grade1 diff grade2) <=1) {
      val g = new Grade(Math.max(grade1.gradePoint, grade2.gradePoint))
      Some(g)
    } else {
      val g = new Grade(Math.min(grade1.gradePoint, grade2.gradePoint) + 1)
      Some(g)
    }
  }

  override def assessment2: SingleMarkerAssessment = unconfirmedAssessment.assessment2

  override def assessment1: SingleMarkerAssessment = unconfirmedAssessment.assessment1

  override def justification: String = {
    (grade1 diff grade2) match {
      case 0 => "Both markers grades are identical, and therefore have been used to determine the final grade"
      case 1 => "Both markers grades are in the same grade band and one gradepoint apart, and therefore the higher has been used"
      case 2 => "Both markers grades are in the same grade band and two gradepoints apart, and therefore the grade band midpoint has been used"
      case _ => throw new IllegalStateException("Should never be >2 gradepoints between markers")
    }
  }
}
