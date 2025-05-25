file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/Main.scala
### dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition Board is defined in
  C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala
and also in
  C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala
One of these files should be removed from the classpath.

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/Main.scala
text:
```scala
import backend.ui.SudokuCLI
import backend.model._
import backend.core.Validator
import backend.core.Generator
import backend.core.PuzzleGenerator
import backend.core.Solver
import backend.ui.SudokuCLI


object Main extends App {
  new SudokuCLI().run()


}
```



#### Error stacktrace:

```

```
#### Short summary: 

dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition Board is defined in
  C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala
and also in
  C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala
One of these files should be removed from the classpath.