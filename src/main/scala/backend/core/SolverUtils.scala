package backend.core
import backend.model._

object SolverUtils {

  def hasUniqueSolution(board: Board): Boolean = {
    val solutions = Solver.solveAll(board, limit = 2)
    solutions.size == 1
  }

}
