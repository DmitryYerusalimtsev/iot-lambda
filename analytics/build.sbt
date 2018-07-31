name := "analytics"

lazy val commonSettings = Seq(
  organization := "com.cartracker",
  version := "1.0",
  scalaVersion := "2.11.5",

  libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.18.0"
)

val spark = Seq(
  "org.apache.spark" %% "spark-core" % "2.3.0",
  "org.apache.spark" %% "spark-sql" % "2.3.0",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.3.0",
  "org.apache.kafka" % "kafka-clients" % "0.11.0.1"
)

lazy val common = (project in file("common"))
  .settings(
    commonSettings,
    libraryDependencies ++= spark
  )

lazy val domain = (project in file("domain"))
  .settings(commonSettings)

lazy val speed = (project in file("speed"))
  .settings(
    commonSettings,

    libraryDependencies ++= spark
  ).dependsOn(domain, common)

lazy val batch = (project in file("batch"))
  .settings(
    commonSettings,

    libraryDependencies ++= spark
  ).dependsOn(domain, common)