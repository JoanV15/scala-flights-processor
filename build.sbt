import sbt.Keys.libraryDependencies
import sbtassembly.AssemblyPlugin.defaultShellScript

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.15"
ThisBuild / assemblyPrependShellScript := Some(defaultShellScript)

val mainClassName = "org.ntic.flights.FlightsLoaderApp"

lazy val root = (project in file("."))
  .settings(
    name := "us-flights-loader",
    Compile / run / mainClass := Some(mainClassName),
    Compile / packageBin / mainClass := Some(mainClassName),
    assembly / mainClass := Some(mainClassName),
    assembly / assemblyJarName := "flights_loader.jar",

    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.3",

      // Dependencias de Test
      "org.scalatest" %% "scalatest" % "3.2.17" % Test,
      "org.scala-lang" %% "toolkit-test" % "0.1.7" % Test
    )
  )