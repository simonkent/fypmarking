package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade

trait Assessment{
  def submission: Submission
  def grade: Option[Grade]

  protected def checkedGrade(go: Option[Grade]):Grade ={
    grade match {
      case Some(g: Grade) => g;
      case None => throw new IllegalStateException("Should not call checkedGrade when grade is unconfirmed")
    }
  }
}