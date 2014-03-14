package uk.ac.brunel.cs.fyp

import uk.ac.brunel.cs.fyp.Student

trait Submission {
	def student: Student
	def programme: String
	def title: String
}