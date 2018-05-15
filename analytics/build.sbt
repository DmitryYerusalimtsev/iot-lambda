name := "analytics"

lazy val commonSettings = Seq(
  organization := "com.cartracker",
  version := "1.0",
  scalaVersion := "2.11.5",

  libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.18.0"
)

val spark = Seq(
  "org.apache.spark" %% "spark-core" % "2.3.0",
  "org.apache.spark" %% "spark-sql" % "2.3.0"
)

lazy val domain = (project in file("domain"))
  .settings(commonSettings)

lazy val batch = (project in file("batch"))
  .settings(
    commonSettings,

    libraryDependencies ++= spark
  ).dependsOn(domain)