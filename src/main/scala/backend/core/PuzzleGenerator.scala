package backend.core

import backend.model._
import backend.core.Generator._        // генерация полной заполненной доски
import backend.core.Solver._           // решатель для проверки уникальности
import backend.core.SolverUtils._      // вспомогательные утилиты (если есть)
import scala.util.Random

/* Объект `PuzzleGenerator` отвечает за генерацию судоку-головоломок
 * определённой сложности с гарантией уникального решения.
 */
object PuzzleGenerator {

  /* Генерация новой головоломки по заданной сложности.
   *
   * @param difficulty Уровень сложности: "easy", "medium", "hard".
   * @return Option[Board] — готовая головоломка, если удалось сгенерировать.
   */
  def generatePuzzle(difficulty: String): Option[Board] = {

    // Генерируем полное решение
    Generator.generateFullBoard().map { fullBoard =>
      
      // Определяем количество ячеек, которое нужно удалить,
      // в зависимости от уровня сложности
      val cellsToRemove = difficulty match {
        case "easy"   => 35
        case "medium" => 45
        case "hard"   => 55
        case _        => 45 // по умолчанию medium
      }

      // Удаляем клетки с проверкой на уникальность
      removeCells(fullBoard, cellsToRemove)
    }
  }

  /* Удаляет клетки с доски, при этом гарантируя, что после каждого удаления
   * головоломка остаётся с **единственным решением**.
   * @param board Полностью заполненная доска (корректное решение).
   * @param toRemove Количество клеток, которое нужно удалить.
   * @return Головоломка с удалёнными клетками.
   */
  private def removeCells(board: Board, toRemove: Int): Board = {
    // Создаём случайный список координат клеток
    val positions = Random.shuffle(for {
      row <- 0 until board.size
      col <- 0 until board.size
    } yield (row, col))

    /* Рекурсивная вспомогательная функция, которая поочерёдно пытается удалить
     * клетки из `remaining`, проверяя, остаётся ли решение уникальным.
     * @param current Текущая доска.
     * @param remaining Список координат для удаления.
     * @param removed Счётчик успешно удалённых клеток.
     * @return Доска с удалёнными клетками.
     */
    def helper(current: Board, remaining: Seq[(Int, Int)], removed: Int): Board = {
      // Условие завершения: достаточно удалили или закончились клетки
      if (removed >= toRemove || remaining.isEmpty) current
      else {
        val (row, col) = remaining.head
        val original = current.get(row, col)

        if (!Cell.isEmpty(original)) {
          val updated = current.updated(row, col, Empty)

          // Проверка: если после удаления остаётся ровно одно решение — сохраняем удаление
          if (Solver.solveAll(updated, 2).size == 1)
            helper(updated, remaining.tail, removed + 1)
          else
            helper(current, remaining.tail, removed) // отменяем удаление
        } else {
          helper(current, remaining.tail, removed) // уже пустая — пропустить
        }
      }
    }

    helper(board, positions, 0)
  }

}
