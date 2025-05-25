error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/Generator.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/Generator.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -backend/model/backend.
	 -backend/core/Validator.backend.
	 -backend.
	 -scala/Predef.backend.
offset: 52
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/Generator.scala
text:
```scala
package backend.core

import backend.model._
import @@backend.core.Validator._

import scala.util.Random

object Generator {

  val MaxDepth = 150

  def generateHeuristic(board: Board, depthLeft: Int = MaxDepth): LazyList[Board] = {
    if (depthLeft == 0) LazyList.empty
    else findMostConstrainedCell(board) match {
      case None => if (board.isComplete) LazyList(board) else LazyList.empty
      case Some((row, col, validValues)) =>
        Random.shuffle(validValues).to(LazyList)
          .flatMap { num =>
            val newBoard = board.updated(row, col, Filled(num))
            generateHeuristic(newBoard, depthLeft - 1)
          }
    }
  }

  def generateOneHeuristic(): Option[Board] =
    generateHeuristic(Board.empty(), MaxDepth).headOption

  /** Поиск самой ограниченной клетки и её допустимых значений */
  private def findMostConstrainedCell(board: Board): Option[(Int, Int, Seq[Int])] = {
    val allEmpty = for {
      row <- 0 until board.size
      col <- 0 until board.size
      if Cell.isEmpty(board.get(row, col))
    } yield {
      val valid = (1 to 9).filter(n => isValid(board, row, col, n))
      (row, col, valid)
    }

    // Отфильтровать невозможные и выбрать минимальную по длине допустимых значений
    allEmpty.filter(_._3.nonEmpty).sortBy(_._3.size).headOption
  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.