name := "FYP Processes"

version := "0.2"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies += "org.scala-lang" % "scala-library" % "2.11.8"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test"

// https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml
libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.8"

libraryDependencies +=
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"
