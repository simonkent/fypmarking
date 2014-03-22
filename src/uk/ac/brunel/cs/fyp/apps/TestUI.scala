package uk.ac.brunel.cs.fyp.apps

import uk.ac.brunel.cs.fyp.assessment.SingleMarkerAssessment
import uk.ac.brunel.cs.fyp.StudentRegistry

object TestUI extends App {
import uk.ac.brunel.cs.fyp.MarkingSheetReader
import uk.ac.brunel.cs.fyp.Grade
	// Initialise the Student Registry
	val reg = StudentRegistry
  
	val markingSheetReader = new MarkingSheetReader("/Users/simonkent/Desktop/Marking Sheets")
		
	val markSheets = markingSheetReader.processMarkingSheets

	val assessments = markSheets.map(ms => new SingleMarkerAssessment(
							reg.addSubmission(reg.addStudent(ms.studentNumber), ms.programme, ms.title),
							ms.marker,
							ms.programmeRequirements,
							ms.learningOutcomes,
							ms.minimumStandardsText,
							ms.justification,
							Some(new Grade(ms.grade))
							)
	)
	
	reg.addAssessments(assessments);
	
	println {
	
	assessments.foldLeft("")((acc, a) => acc + a.submission + a.marker.toString + a.grade + "\n")
	
	}
	// Apply 
}