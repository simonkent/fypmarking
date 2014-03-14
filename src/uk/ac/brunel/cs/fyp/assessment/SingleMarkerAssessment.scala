package uk.ac.brunel.cs.fyp.assessment

import uk.ac.brunel.cs.fyp.Submission
import uk.ac.brunel.cs.fyp.Marker
import uk.ac.brunel.cs.fyp.ProgrammeRequirements
import uk.ac.brunel.cs.fyp.LearningOutcomes
import uk.ac.brunel.cs.fyp.Grade

case class SingleMarkerAssessment (
  val submission: Submission,
  val marker: Marker,
  val programmeRequirements: ProgrammeRequirements,
  val learningOutcomes: LearningOutcomes,
  val minimumStandards: String,
  val justification: String,
  val grade: Option[Grade]
  ) extends Assessment 