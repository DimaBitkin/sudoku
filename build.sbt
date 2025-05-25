val scala3Version = "3.7.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "course-work",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.typelevel" %% "cats-core" % "2.12.0"
    )
  )
import scala.scalanative.build._

nativeConfig ~= { c =>
  c.withSourceLevelDebuggingConfig(_.enableAll) // enable generation of debug information
  .withOptimize(false)  // disable Scala Native optimizer
  .withMode(Mode.debug) // compile using LLVM without optimizations
}