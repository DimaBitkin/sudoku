error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/Validator.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/Validator.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 454
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/core/Validator.scala
text:
```scala
package backend.core

import backend.model._

object Validator {

  /** Проверяет, можно ли поставить значение в данную клетку */
  def isValid(board: Board, row: Int, col: Int, value: Int): Boolean =
    isRowValid(board, row, value) &&
    isColValid(board, col, value) &&
    isBlockValid(board, row, col, value)

  /** Проверка по строке */
  private def isRowValid(board: Board, row: Int, value: Int): Boolean =
    !board.cells(row).exists {
      @@case Filled(v) => v == value
      case Empty     => false
    }

  /** Проверка по столбцу */
  private def isColValid(board: Board, col: Int, value: Int): Boolean =
    !board.cells.exists { row =>
      row(col) match {
        case Filled(v) => v == value
        case Empty     => false
      }
    }

  /** Проверка по блоку 3x3 */
  private def isBlockValid(board: Board, row: Int, col: Int, value: Int): Boolean = {
    val blockSize = 3
    val startRow = (row / blockSize) * blockSize
    val startCol = (col / blockSize) * blockSize

    val blockCells = for {
      r <- startRow until (startRow + blockSize)
      c <- startCol until (startCol + blockSize)
    } yield board.get(r, c)

    !blockCells.exists {
      case Filled(v) => v == value
      case Empty     => false
    }
  }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.