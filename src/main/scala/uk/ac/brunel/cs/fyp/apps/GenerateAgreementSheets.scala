package uk.ac.brunel.cs.fyp.apps

import uk.ac.brunel.cs.fyp.MarkingEngine

object GenerateAgreementSheets extends App {

  val engine = new MarkingEngine
  
  engine.generateAgreementSheets 
  
  engine.outputToXLSX

  //reg.addAgreements(agreements)

  // Here we need confirm assessments that can be automatically agreed
  
}