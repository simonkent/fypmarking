package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model._
import org.scalamock.scalatest.MockFactory

/**
 * Created by simonkent on 27/03/2014.
 */
class SingleMarkerAssessmentSpec extends UnitSpec with MockFactory {
  val mockStudent = mock[Student]
  val mockSubmission = mock[Submission]

  val satisfiedLearningOutcomes = new LearningOutcomes(true, true, true, true, true, true)
  val unsatisfiedLearningOutcomes = new LearningOutcomes(false, false, false, false, false, false)
  val aStarGrade = new Grade("A*")

  

  "An assessment" should "not be constructed with a missing grade" in {
    //(mockStudent.number _).expects().returning("1234567")
    //(mockSubmission.student _).expects().returning(mockStudent)
    try {
      val assessment = new SingleMarkerAssessment(mockSubmission, new Marker("Test"), new ProgrammeRequirements(true, "Test"),
        satisfiedLearningOutcomes, "Minimum Standards", "Justification", None)
      assert(false)
    } catch {
      case e: AssessmentException => assert(true)
    }
  }

  "A valid assessment" should "be constructed with an A* grade" in {
    val assessment = new SingleMarkerAssessment(mockSubmission, new Marker("Test"),new ProgrammeRequirements(true, "Test"),
      satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(aStarGrade))
    assert(assessment.grade match {
      case Some(g: Grade) => g==aStarGrade
      case _ => false
    })
  }

}
