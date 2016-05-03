name := "FYP Processes"

version := "0.1"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies += "org.scala-lang" % "scala-library" % "2.11.8"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11.0-M7"
 
libraryDependencies += "com.typesafe" % "config" % "1.2.1"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.6" % "test"

libraryDependencies +=
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"