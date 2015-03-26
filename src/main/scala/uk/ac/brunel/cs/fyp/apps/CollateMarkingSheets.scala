package uk.ac.brunel.cs.fyp.apps

import uk.ac.brunel.cs.fyp.MarkingEngine

object CollateMarkingSheets extends App {

  val engine = new MarkingEngine
  
  engine.outputToXLSX
}