package backend.core

import backend.model._
import backend.core.Validator._

import scala.util.Random

object Generator {

  val MaxDepth = 81
  

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
