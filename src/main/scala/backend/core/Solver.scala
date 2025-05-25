package backend.core

import backend.model._
import backend.core.Validator._

object Solver {

  def solveAll(board: Board, limit: Int = Int.MaxValue): List[Board] =
    solveLazy(board).take(limit).toList

  private def solveLazy(board: Board): LazyList[Board] =
    findEmptyCell(board) match {
      case None if isComplete(board) => LazyList(board)
      case None                      => LazyList.empty
      case Some((row, col)) =>
        (1 to 9).to(LazyList)
          .filter(isValid(board, row, col, _))
          .map(n => board.updated(row, col, Filled(n)))
          .flatMap(solveLazy)
    }

  def isComplete(board: Board): Boolean =
    board.cells.flatten.forall {
      case Filled(_) => true
      case Empty     => false
    }

  private def findEmptyCell(board: Board): Option[(Int, Int)] =
    board.cells.zipWithIndex.flatMap {
      case (rowCells, row) =>
        rowCells.zipWithIndex.collect {
          case (Empty, col) => (row, col)
        }
    }.headOption
}
