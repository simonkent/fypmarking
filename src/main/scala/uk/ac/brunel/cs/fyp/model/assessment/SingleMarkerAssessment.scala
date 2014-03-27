package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model.ProgrammeRequirements
import uk.ac.brunel.cs.fyp.model.Submission
import uk.ac.brunel.cs.fyp.model.Grade
import uk.ac.brunel.cs.fyp.model.LearningOutcomes
import uk.ac.brunel.cs.fyp.model.Marker

case class SingleMarkerAssessment (
  val submission: Submission,
  val marker: Marker,
  val programmeRequirements: ProgrammeRequirements,
  val learningOutcomes: LearningOutcomes,
  val minimumStandards: String,
  val justification: String,
  val grade: Option[Grade]
  ) extends Assessment 