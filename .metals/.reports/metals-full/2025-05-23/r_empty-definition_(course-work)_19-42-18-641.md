error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/Solver.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/Solver.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1107
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/Solver.scala
text:
```scala
package backend.core

import backend.model._
import backend.core.Validator._

object Solver {

  /** Найти все решения доски (до лимита) */
  def solveAll(board: Board, limit: Int = Int.MaxValue): List[Board] = {
    def helper(current: Board): LazyList[Board] = {
      if (isComplete(current)) LazyList(current)
      else findEmptyCell(current) match {
        case None => LazyList.empty
        case Some((row, col)) =>
          (1 to 9).to(LazyList)
            .filter(n => isValid(current, row, col, n))
            .flatMap { n =>
              val nextBoard = current.updated(row, col, Filled(n))
              helper(nextBoard)
            }
      }
    }

    helper(board).take(limit).toList
  }

  /** Проверка, заполнена ли доска полностью */
  def isComplete(board: Board): Boolean = {
    (0 until board.size).forall { row =>
      (0 until board.size).forall { col =>
        !Cell.isEmpty(board.get(row, col))
      }
    }
  }

  /** Найти первую пустую клетку */
  private def findEmptyCell(board: Board): Option[(Int, Int)] = {
    for {
      row <- 0 until board.size
      col <- 0@@ until board.size
      if Cell.isEmpty(board.get(row, col))
    } yield (row, col)
  }.headOption

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.