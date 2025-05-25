error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/core/PuzzleGenerator.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/core/PuzzleGenerator.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -model/toRemove.
	 -model/toRemove#
	 -model/toRemove().
	 -core/Generator.toRemove.
	 -core/Generator.toRemove#
	 -core/Generator.toRemove().
	 -core/Solver.toRemove.
	 -core/Solver.toRemove#
	 -core/Solver.toRemove().
	 -toRemove.
	 -toRemove#
	 -toRemove().
	 -scala/Predef.toRemove.
	 -scala/Predef.toRemove#
	 -scala/Predef.toRemove().
offset: 779
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/core/PuzzleGenerator.scala
text:
```scala
package core


import model._
import core.Generator._
import core.Solver._

import scala.util.Random

object PuzzleGenerator {

  def generatePuzzle(difficulty: String): Option[Board] = {
    generateOneHeuristic().map { fullBoard =>
      val cellsToRemove = difficulty match {
        case "easy"   => 35
        case "medium" => 45
        case "hard"   => 55
        case _        => 45 // по умолчанию medium
      }

      removeCells(fullBoard, cellsToRemove)
    }
  }

  private def removeCells(board: Board, toRemove: Int): Board = {
    val positions = Random.shuffle(for {
      row <- 0 until board.size
      col <- 0 until board.size
    } yield (row, col))

    def helper(current: Board, remaining: Seq[(Int, Int)], removed: Int): Board = {
      if (removed >= @@toRemove || remaining.isEmpty) current
      else {
        val (row, col) = remaining.head
        val backup = current.get(row, col)

        if (!Cell.isEmpty(backup)) {
          val updated = current.updated(row, col, Empty)
          val solutions = solveAll(updated, limit = 2)

          if (solutions.length == 1)
            helper(updated, remaining.tail, removed + 1)
          else
            helper(current, remaining.tail, removed)
        } else {
          helper(current, remaining.tail, removed)
        }
      }
    }

    helper(board, positions, 0)
  }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.