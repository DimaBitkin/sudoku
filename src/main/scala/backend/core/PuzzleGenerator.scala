package backend.core


import backend.model._
import backend.core.Generator._
import backend.core.Solver._
import backend.core.SolverUtils._
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
      if (removed >= toRemove || remaining.isEmpty) current
      else {
        val (row, col) = remaining.head
        val backup = current.get(row, col)

        if (!Cell.isEmpty(backup)) {
          val updated = current.updated(row, col, Empty)
          
          if (hasUniqueSolution(updated))
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