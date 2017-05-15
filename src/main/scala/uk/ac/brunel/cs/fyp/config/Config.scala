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
  lazy val moderationTemplate = conf.getString("files.moderationTemplate")

  lazy val agreementOutDirectory = conf.getString("files.agreementOutDirectory")

  lazy val moderationOutDirectory = conf.getString("files.moderationOutDirectory")

  lazy val agreementInDirectory = conf.getString("files.agreementInDirectory")

  lazy val moderationInDirectory = conf.getString("files.moderationInDirectory")

  lazy val matchingThreshold = conf.getInt("matchingThreshold")

  lazy val eligibleForAgreementText = conf.getString("text.eligibleForAgreement")
  lazy val requiresAgreementText = conf.getString("text.requiresAgreement")
  lazy val requiresModerationBecauseOfGradeDifference = conf.getString("text.requiresModerationBecauseOfMarkerDifference")
  lazy val requiresModerationBecauseOfMarkerDisagreement = conf.getString("text.requiresModerationBecauseOfMarkerDisagreement")
}
