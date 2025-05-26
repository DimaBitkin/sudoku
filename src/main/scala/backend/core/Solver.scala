package backend.core

import backend.model._
import backend.core.Validator._

/* Объект `Solver` содержит алгоритмы для решения судоку.
 * Основной метод — `solveAll`, который ищет все возможные решения доски.
 */
object Solver {

  /* Решить головоломку и вернуть не более `limit` решений.
   *
   * @param board Исходная доска.
   * @param limit Максимальное количество решений, которое нужно найти.
   *              По умолчанию — Int.MaxValue (т.е. без ограничения).
   * @return Список решений, каждое из которых — завершённая доска.
   */
  def solveAll(board: Board, limit: Int = Int.MaxValue): List[Board] =
    solveLazy(board).take(limit).toList

  /* Ленивое (отложенное) решение судоку с помощью рекурсии и backtracking.
   * @param board Исходная доска.
   * @return LazyList решений, которые можно итерировать по мере необходимости.
   */
  private def solveLazy(board: Board): LazyList[Board] =
    findEmptyCell(board) match {
      // Если нет пустых ячеек и доска завершена корректно — вернуть её как решение
      case None if isComplete(board) => LazyList(board)
      // Если нет пустых ячеек, но доска некорректна — решений нет
      case None                      => LazyList.empty
      // Найдена пустая клетка — подставляем в неё все допустимые значения
      case Some((row, col)) =>
        (1 to 9).to(LazyList)
          .filter(isValid(board, row, col, _))                // только допустимые значения
          .map(n => board.updated(row, col, Filled(n)))       // создаём новую доску с этим числом
          .flatMap(solveLazy)                                 // рекурсивно решаем дальше
    }

  /* Проверить, полностью ли заполнена доска (все клетки заполнены).
   * @param board Доска для проверки.
   * @return true, если доска заполнена числами (без Empty).
   */
  def isComplete(board: Board): Boolean =
    board.cells.flatten.forall {
      case Filled(_) => true
      case Empty     => false
    }

  /* Найти первую пустую клетку (в порядке сверху вниз, слева направо).
   * @param board Доска.
   * @return Option[(row, col)] — координаты первой пустой клетки или None, если таких нет.
   */
  private def findEmptyCell(board: Board): Option[(Int, Int)] =
    board.cells.zipWithIndex.flatMap {
      case (rowCells, row) =>
        rowCells.zipWithIndex.collect {
          case (Empty, col) => (row, col)
        }
    }.headOption
}
