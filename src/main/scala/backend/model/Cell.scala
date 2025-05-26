package backend.model

/* Абстрактное представление ячейки Судоку.
 * Может быть либо пустой (Empty), либо заполненной числом (Filled).
 */
sealed trait Cell

//Пустая ячейка 
case object Empty extends Cell

/* Заполненная ячейка с числом от 1 до 9.
 * @param value Значение ячейки
 */
case class Filled(value: Int) extends Cell

//Объект-компаньон `Cell` с утилитными методами.

object Cell {

  /* Проверяет, является ли ячейка пустой.
   * @param cell Ячейка
   * @return true, если ячейка — Empty, иначе false
   */
  def isEmpty(cell: Cell): Boolean = cell match {
    case Empty => true
    case _     => false
  }

}
