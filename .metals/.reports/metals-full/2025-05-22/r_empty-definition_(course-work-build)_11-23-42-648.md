error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/build.sbt:
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/build.sbt
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 343
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/build.sbt
text:
```scala
// Указываем версию Scala
scalaVersion := "2.13.12"

// Метаданные проекта
name := "course-work"
organization := "ch.epfl.scala"
version := "1.0"

// Версия JavaFX (20 — та же, что у ScalaFX)
val javafxVersion = "20.0.0"

// Зависимости
libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.@@0",
  "org.scalafx" %% "scalafx" % "20.0.0-R31",
  "org.openjfx" % "javafx-base" % javafxVersion classifier "win",
  "org.openjfx" % "javafx-controls" % javafxVersion classifier "win",
  "org.openjfx" % "javafx-graphics" % javafxVersion classifier "win"
)

// Для упаковки и запуска
enablePlugins(JavaAppPackaging)

```


#### Short summary: 

empty definition using pc, found symbol in pc: 