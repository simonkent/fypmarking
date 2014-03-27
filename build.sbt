name := "FYP Processes"

version := "0.1"

scalaVersion := "2.10.3"

libraryDependencies <+= scalaVersion { "org.scala-lang" % "scala-swing" % _ }
