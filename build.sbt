// Your sbt build file. Guides on how to write one can be found at
// http://www.scala-sbt.org/0.13/docs/index.html

import ReleaseTransformations._

val sparkVer = sys.props.getOrElse("spark.version", "2.3.2")
val sparkBranch = sparkVer.substring(0, 3)
val defaultScalaVer = sparkBranch match {
  case "2.3" => "2.11.8"
  case "2.4" => "2.11.8"
  case _ => throw new IllegalArgumentException(s"Unsupported Spark version: $sparkVer.")
}
val scalaVer = sys.props.getOrElse("scala.version", defaultScalaVer)
val scalaMajorVersion = scalaVer.substring(0, scalaVer.indexOf(".", scalaVer.indexOf(".") + 1))

sparkVersion := sparkVer

scalaVersion := scalaVer

name := "spark-sklearn"

spName := "databricks/spark-sklearn"

organization := "com.databricks"

version := (version in ThisBuild).value + s"-spark$sparkBranch"

isSnapshot := version.value.contains("-SNAPSHOT")

spAppendScalaVersion := true

// All Spark Packages need a license
licenses := Seq("Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0"))

// Add Spark components this package depends on, e.g, "mllib", ....
sparkComponents ++= Seq("mllib")

// uncomment and change the value below to change the directory where your zip artifact will be created
// spDistDirectory := target.value

// add any Spark Package dependencies using spDependencies.
// e.g. spDependencies += "databricks/spark-avro:0.1"

// We only use sbt-release to update version numbers for now.
releaseProcess := Seq[ReleaseStep](
  inquireVersions,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  setNextVersion,
  commitNextVersion
)
