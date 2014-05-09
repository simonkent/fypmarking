package uk.ac.brunel.cs.fyp.config

import com.typesafe.config.ConfigFactory

/**
 * Created by simonkent on 06/05/2014.
 */
object Config {



  val conf = ConfigFactory.load()
  
  lazy val markingsheets = conf.getString("files.markingsheets")
  lazy val outputfile = conf.getString("files.outputfile")
  
  lazy val agreementTemplate = conf.getString("files.agreementTemplate")
  lazy val agreementDirectory = conf.getString("files.agreementDirectory")

  lazy val eligibleForAgreementText = conf.getString("text.eligibleForAgreement")
  lazy val requiresAgreementText = conf.getString("text.requiresAgreement")
}
