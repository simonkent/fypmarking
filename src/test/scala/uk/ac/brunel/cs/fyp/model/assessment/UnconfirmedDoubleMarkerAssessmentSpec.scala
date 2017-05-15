package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model._
import org.scalamock.scalatest.MockFactory

/**
 * Created by simonkent on 27/03/2014.
 */
class UnconfirmedDoubleMarkerAssessmentSpec extends UnitSpec with MockFactory {

  val mockStudent = mock[Student]
  val mockSubmission = mock[Submission]

  val satisfiedLearningOutcomes = new LearningOutcomes(true, true, true, true, true, true, true)
  val unsatisfiedLearningOutcomes = new LearningOutcomes(false, false, false, false, false, false, false)
  val aStarGrade = new Grade("A*")

  val sma1 = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 1"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(aStarGrade))
  val sma2 = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 2"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(aStarGrade))
    
  "An UnconfirmedDoubleMarkerAssessment" should "not be final" in {
    val dma = new UnconfirmedDoubleMarkerAssessment(sma1, sma2)
    assert(dma.isFinal == false)
  }
  
  "Two markers" should "not be used to construct an UnconfirmedDoubleMarkerAssessment" in {
    try {
      val dma = new UnconfirmedDoubleMarkerAssessment(sma1, sma1)
      assert(false)
    } catch {
      case e: AssessmentException => assert(true)
    }
    
  }
}
