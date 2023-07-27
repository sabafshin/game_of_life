val scala3Version = "3.3.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "com.madgag" % "animated-gif-lib" % "1.4"
  )
