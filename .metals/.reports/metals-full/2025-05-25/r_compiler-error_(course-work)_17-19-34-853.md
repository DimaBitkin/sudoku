file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/GameService.scala
### dotty.tools.dotc.core.TypeError$$anon$1: Toplevel definition Board is defined in
  C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala
and also in
  C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala
One of these files should be removed from the classpath.

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/GameService.scala
text:
```scala
package backend.core

import backend.model._
import backend.core.{PuzzleGenerator, Solver}

class GameService {

  private var currentPuzzle: Option[Board] = None
  private var currentSolution: Option[Board] = None
  private var difficulty: String = "medium"
  private var initialPuzzle: Option[Board] = None


  def setDifficulty(level: String): Unit = {
    difficulty = level.toLowerCase match {
      case "easy" | "medium" | "hard" => level.toLowerCase
      case _ => "medium"
    }
  }

  /** Генерация новой головоломки */
  def generateNewPuzzle(): Option[Board] = {
    val puzzleOpt = PuzzleGenerator.generatePuzzle(difficulty)
    currentPuzzle = puzzleOpt
    initialPuzzle = puzzleOpt  // сохранить исходную
    currentSolution = puzzleOpt.flatMap(board => Solver.solveAll(board, limit = 1).headOption)
    puzzleOpt
  }

  /** Получить текущую головоломку */
  def getCurrentPuzzle: Option[Board] = currentPuzzle

  /** Получить решение текущей головоломки */
  def getCurrentSolution: Option[Board] = currentSolution

  def setCurrentPuzzle(board: Board): Unit = {
  currentPuzzle = Some(board)
  }


  /** Проверить пользовательское решение */
  def checkUserSolution(userBoard: Board): Boolean = {
    if (!isValidBoard(userBoard)) return false
    // Проверяем, что пользовательское решение совпадает с решением
    currentSolution match {
      case Some(solution) => boardsEqual(solution, userBoard)
      case None => false
    }
  }

  /** Валидация доски на корректность по правилам судоку */
  private def isValidBoard(board: Board): Boolean = {
    val size = board.size

    def noDuplicates(values: Seq[Int]): Boolean = {
      val nonZero = values.filter(_ != 0)
      nonZero.distinct.size == nonZero.size
    }

    val rowsValid = (0 until size).forall { r =>
      noDuplicates((0 until size).map(c => board.get(r, c) match {
        case Filled(v) => v
        case Empty => 0
      }))
    }

    val colsValid = (0 until size).forall { c =>
      noDuplicates((0 until size).map(r => board.get(r, c) match {
        case Filled(v) => v
        case Empty => 0
      }))
    }

    val blocksValid = (0 until size by 3).forall { br =>
      (0 until size by 3).forall { bc =>
        noDuplicates(for {
          r <- br until br + 3
          c <- bc until bc + 3
        } yield board.get(r, c) match {
          case Filled(v) => v
          case Empty => 0
        })
      }
    }

    rowsValid && colsValid && blocksValid
  }
  

  /** Сравнение двух досок */
  private def boardsEqual(b1: Board, b2: Board): Boolean = {
    (0 until b1.size).forall { r =>
      (0 until b1.size).forall { c =>
        b1.get(r, c) == b2.get(r, c)
      }
    }
  }
  //Метод для проверки "можно ли редактировать клетку":
  def isCellEditable(row: Int, col: Int): Boolean = {
    initialPuzzle match {
      case Some(board) =>
        board.get(row, col) match {
          case Filled(_) => false
          case Empty     => true
        }
      case None => true // если по какой-то причине нет исходной — разрешить
    }
  }

  def updateUserCell(row: Int, col: Int, value: Int): Boolean = {
    if (!isCellEditable(row, col) || currentPuzzle.isEmpty) return false

    val newCell = if (value == 0) Empty else Filled(value)
    currentPuzzle = currentPuzzle.map(_.updated(row, col, newCell))
    true
  }


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