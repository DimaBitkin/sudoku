package backend.model

/* Класс `Board` представляет игровое поле Судоку — двумерный вектор ячеек (Cell).
 * Поддерживает удобные методы получения, обновления и отображения доски.
 *
 * @param cells Двумерный вектор ячеек (размер NxN, обычно 9x9).
 */
case class Board(cells: Vector[Vector[Cell]]) {

  /** Размер доски (обычно 9) */
  def size: Int = cells.length

  /* Получить ячейку по координатам (row, col).
   *
   * @param row Номер строки (от 0 до size-1).
   * @param col Номер столбца (от 0 до size-1).
   * @return Значение ячейки (тип Cell).
   */
  def get(row: Int, col: Int): Cell = cells(row)(col)

  /* Безопасное получение ячейки: возвращает `Some(cell)` или `None`,
   * если координаты выходят за границы.
   */
  def getOption(row: Int, col: Int): Option[Cell] =
    cells.lift(row).flatMap(_.lift(col))

  /* Возвращает новую доску с заменой ячейки по координатам.
   *
   * @param row Строка
   * @param col Столбец
   * @param cell Новое значение ячейки
   * @return Обновлённая доска
   */
  def updated(row: Int, col: Int, cell: Cell): Board = {
    val updatedRow = cells(row).updated(col, cell)
    Board(cells.updated(row, updatedRow))
  }

  /*Возвращает новую доску с применением функции обновления к ячейке.
   *
   * @param row Строка
   * @param col Столбец
   * @param update Функция изменения текущего значения ячейки
   * @return Обновлённая доска
   */
  def updated(row: Int, col: Int)(update: Cell => Cell): Board = {
    val updatedRow = cells(row).updated(col, update(cells(row)(col)))
    Board(cells.updated(row, updatedRow))
  }

  /* Представление доски в виде строки (используется для отладки).
   * Пустые ячейки отображаются как `.`.
   */
  def asString: String =
    cells.map(_.map {
      case Empty         => "."
      case Filled(value) => value.toString
    }.mkString(" ")).mkString("\n")

  /* Проверяет, полностью ли заполнена доска.
   * (То есть нет ни одной пустой ячейки.)
   */
  def isComplete: Boolean =
    cells.flatten.forall(_.isInstanceOf[Filled])

  // Преобразует доску в красиво отформатированную строку с блоками 3x3.
   
  def prettyString: String = {
    cells.zipWithIndex.map { case (row, r) =>
      val line = row.zipWithIndex.map { case (cell, c) =>
        val value = cell match {
          case Empty     => "."
          case Filled(v) => v.toString
        }
        val sep = if (c > 0 && c % 3 == 0) "| " else ""
        s"$sep$value"
      }.mkString(" ")

      val separator = if (r > 0 && r % 3 == 0) Some("-" * 21) else None

      separator match {
        case Some(s) => s"\n$line"
        case None    => line
      }
    }.mkString("\n") + "\n"
  }

  /** Выводит `prettyString` в консоль. */
  def prettyPrint(): Unit = println(prettyString)
}

//Объект-компаньон Board: содержит фабричный метод `empty`.
 
object Board {
  /* Создаёт пустую доску указанного размера (по умолчанию 9x9).
   *
   * @param size Размер доски (по умолчанию 9)
   * @return Новая пустая доска
   */
  def empty(size: Int = 9): Board =
    Board(Vector.fill(size, size)(Empty))
}
