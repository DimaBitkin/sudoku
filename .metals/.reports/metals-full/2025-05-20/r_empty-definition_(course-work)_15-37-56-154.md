error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/core/Generator.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/core/Generator.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 247
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/core/Generator.scala
text:
```scala
package core
import model._
import core.Validator._

object Generator {

  /** Основной метод: генерирует ленивый поток допустимых решений */
  def generate(board: Board): LazyList[Board] = {
    // findEmptyCell(board) match {
        
      case@@ None => LazyList(board) // доска полностью заполнена
      case Some((row, col)) =>
      scala.util.Random.shuffle(1 to 9).to(LazyList)
          .filter(isValid(board, row, col, _))
          .flatMap { num =>
            val newBoard = board.updated(row, col, Filled(num))
            generate(newBoard)
          }
    }
  }

  /** Находит первую пустую клетку (слева направо, сверху вниз) */
  private def findEmptyCell(board: Board): Option[(Int, Int)] = {
    for {
      row <- 0 until board.size
      col <- 0 until board.size
      if Cell.isEmpty(board.get(row, col))
    } return Some((row, col))
    None
  }

  private def findEmptyCellRandom(board: Board): Option[(Int, Int)] = {
  val emptyCells = for {
    row <- 0 until board.size
    col <- 0 until board.size
    if Cell.isEmpty(board.get(row, col))
  } yield (row, col)

  scala.util.Random.shuffle(emptyCells).headOption
}

  /** Сгенерировать первую подходящую доску */
  def generateOne(): Board =
    generate(Board.empty()).head
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.