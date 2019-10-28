name := "scala_project_template" // Please change

version := "0.1"

scalaVersion := "2.12.10"

organization := "com.jimmy.learning"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "com.typesafe" % "config" % "1.3.4",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
) ++ appDependencies

val appDependencies = Seq (
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "1.1.2",
  "org.postgresql"      % "postgresql"            % "42.2.5",
  "com.typesafe.slick"  %% "slick-hikaricp"       % "3.3.0",
  "com.h2database" % "h2" % "1.4.200"
)