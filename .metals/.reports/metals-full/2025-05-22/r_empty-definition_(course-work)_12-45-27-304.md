error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/GameService.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/GameService.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 749
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/GameService.scala
text:
```scala
package backend.core

import model._
import core.{PuzzleGenerator, Solver}

class GameService {

  private var currentPuzzle: Option[Board] = None
  private var currentSolution: Option[Board] = None
  private var difficulty: String = "medium"

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
    currentSolution = puzzleOpt.flatMap(board => Solver.solveAll(board, limit = 1).headOption)
    puzzleOpt
  }

  /** Получить текущую головоломк@@у */
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
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.