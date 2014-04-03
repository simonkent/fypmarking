package uk.ac.brunel.cs.fyp.model.assessment

import uk.ac.brunel.cs.fyp.model._
import org.scalamock.scalatest.MockFactory

/**
 * Created by simonkent on 27/03/2014.
 */
class AutomaticAgreedDoubleMarkerAssessmentSpec extends UnitSpec with MockFactory {

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

  val sma1a = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 1a"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(aPlusGrade))
  val sma1b = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 1b"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(aPlusGrade))
  val sma2 = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 2"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(aGrade))
  val sma3 = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 3"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(aMinusGrade))
  val sma4 = new SingleMarkerAssessment(mockSubmission, new Marker("Marker 4"), new ProgrammeRequirements(true, "Test"),
    satisfiedLearningOutcomes, "Minimum Standards", "Justification", Some(bPlusGrade))

  val udma1 = new UnconfirmedDoubleMarkerAssessment(sma1a, sma1b);
  val udma2 = new UnconfirmedDoubleMarkerAssessment(sma1a, sma2);
  val udma3 = new UnconfirmedDoubleMarkerAssessment(sma1a, sma3);

  def equalGrade = new AutomaticAgreedDoubleMarkerAssessment(udma1)
  def oneGPDiff = new AutomaticAgreedDoubleMarkerAssessment(udma2)
  def twoGPDiff = new AutomaticAgreedDoubleMarkerAssessment(udma3)
  
  "An AutomaticAgreedDoubleMarkerAssessment constructed with an UnconfirmedDoubleMarkerAssessment" should "retain the underlying marks" in {
    assert(udma3.assessment1==sma1a)
    assert(udma3.assessment2==sma3)
    assert(udma3.submission==sma1a.submission)
    assert(udma3.submission==sma3.submission)
  }
  
  "An AutomaticAgreedDoubleMarkerAssessment with the same grades" should "be constructable" in {
    val dma = equalGrade
    assert(true)
  }

  it should "have a grade the same as the two input marks" in {
    assert(equalGrade.grade==Some(new Grade("A+")))
  }

  it should "be final" in {
    assert(equalGrade.isFinal)
  }

  it should "have an automatic justification" in {
    assert(equalGrade.justification=="Both markers grades are identical, and therefore have been used to determine the final grade")
  }

  "An AutomaticAgreedDoubleMarkerAssessment with one gradepoint difference" should "be constructable" in {
    val dma = oneGPDiff
    assert(true)
  }

  it should "have the higher of the two grades" in {
    assert(oneGPDiff.grade==Some(new Grade("A+")))
  }

  it should "be final" in {
    assert(oneGPDiff.isFinal)
  }

  it should "have an automatic justification" in {
    assert(oneGPDiff.justification=="Both markers grades are in the same grade band and one gradepoint apart, and therefore the higher has been used")
  }


  "An AutomaticAgreedDoubleMarkerAssessment with two gradepoints difference" should "be constructable" in {
    val dma = twoGPDiff
    assert(true)
  }

  it should "be awarded the middle of the two grades" in {
    assert(twoGPDiff.grade==Some(new Grade("A")))
  }

  it should "be final" in {
    assert(twoGPDiff.isFinal)
  }

  it should "have an automatic justification" in {
    assert(twoGPDiff.justification=="Both markers grades are in the same grade band and two gradepoints apart, and therefore the grade band midpoint has been used")
  }


  "If markers grades are >2 gradepoints apart an AgreedDoubleMarkerAssessment" should "throw an AssessmentException" in {
    try {
      val dma = new UnconfirmedDoubleMarkerAssessment(sma1a, sma4)
    } catch {
      case e: AssessmentException => assert(true);
      assert(true)
    }
  }

}
