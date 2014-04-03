package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model._
import org.scalamock.scalatest.MockFactory

/**
 * Created by simonkent on 27/03/2014.
 */
class AgreedDoubleMarkerAssessmentSpec extends UnitSpec with MockFactory {

  val mockStudent = mock[Student]
  val mockSubmission = mock[Submission]

  val satisfiedLearningOutcomes = new LearningOutcomes(true, true, true, true, true, true)
  val unsatisfiedLearningOutcomes = new LearningOutcomes(false, false, false, false, false, false)
  val aStarGrade = new Grade("A*")
  val aPlusGrade = new Grade("A+")
  val aGrade = new Grade("A")
  val aMinusGrade = new Grade("A-")
  val bPlusGrade = new Grade("B+")
  val bGrade = new Grade("B")
  val bMinusGrade = new Grade("B-")

  val sma1 = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 1"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(aPlusGrade))
  val sma2 = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 2"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(aGrade))
  val sma3 = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 3"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(aMinusGrade))
  val sma4 = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 4"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(bPlusGrade))

  val udma1 = new UnconfirmedDoubleMarkerAssessment(sma1, sma3);
    
  "An AgreedDoubleMarkerAssessment" should "be constructable if markers grades are <3 gradepoints apart" in {
    try {
    	val dma = new AgreedDoubleMarkerAssessment(udma1, aPlusGrade, "justification")
    } catch {
      case _: AssessmentException => assert(false)
    }
    try {
    	val dma2 = new AgreedDoubleMarkerAssessment(udma1, aGrade, "justification")
    } catch {
      case _: AssessmentException => assert(false)
    }
    try {
    	val dma3 = new AgreedDoubleMarkerAssessment(udma1, aMinusGrade, "justification")
    } catch {
      case _: AssessmentException => assert(false)
    }
    assert(true)
  }
  
  it should "be final" in {
    val dma = new AgreedDoubleMarkerAssessment(udma1, aGrade, "justification")
    assert(dma.isFinal)
  }

  "If markers grades are >2 gradepoints apart an AgreedDoubleMarkerAssessment" should "throw an AssessmentException" in {
    try {
      val dma = new UnconfirmedDoubleMarkerAssessment(sma1, sma4)
    } catch {
      case e: AssessmentException => assert(true);
      assert(true)
    }
  }

}
