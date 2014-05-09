package uk.ac.brunel.cs.fyp.apps

import uk.ac.brunel.cs.fyp.MarkingEngine

object ApplyAgreementSheets extends App {

  val engine = new MarkingEngine
  
  engine.autoAgree
  
  engine.outputToXLSX
  
  //reg.addAgreements(agreements)

  // Here we need confirm assessments that can be automatically agreed
  
}