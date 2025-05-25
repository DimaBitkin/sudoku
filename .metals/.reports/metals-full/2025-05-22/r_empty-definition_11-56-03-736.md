error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/build.sbt:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/build.sbt
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 596
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/build.sbt
text:
```scala
scalaVersion := "2.13.8"

// Добавляем зависимость от библиотеки ScalaFX 
libraryDependencies += "org.scalafx" %% "scalafx" % "16.0.0-R25"

// Определяем версию операционной системы для бинарников JavaFX 
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Добавляем зависимость от библиотек JavaFX, с учетом операционной системы
lazy val javaFXModules = Seq("base", "controls", "fx@@ml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map(m =>
  "org.openjfx" % s"javafx-$m" % "16" classifier osName
)
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.