name := "FYP Processes"

version := "0.1"

scalaVersion := "2.10.3"

libraryDependencies <+= scalaVersion { "org.scala-lang" % "scala-swing" % _ }

scalacOptions ++= Seq("-deprecation", "-unchecked")
 
libraryDependencies += "com.typesafe" % "config" % "1.2.1"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.1.2" % "test"

libraryDependencies +=
  "org.scalamock" %% "scalamock-scalatest-support" % "3.1.RC1" % "test"

