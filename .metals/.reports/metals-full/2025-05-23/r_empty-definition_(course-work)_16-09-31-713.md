error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb

found definition using fallback; symbol Cell
offset: 89
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala
text:
```scala
package backend.model
import backend.model.Cell


case class Board(cells: Vector[Vector[C@@ell]]) {
  def size: Int = cells.length

  def get(row: Int, col: Int): Cell =
    cells(row)(col)

  def updated(row: Int, col: Int, cell: Cell): Board = {
    val updatedRow = cells(row).updated(col, cell)
    Board(cells.updated(row, updatedRow))
  }

  def asString: String =
    cells.map(_.map {
      case Empty        => "."
      case Filled(value) => value.toString
    }.mkString(" ")).mkString("\n")

  def isComplete: Boolean = cells.flatten.forall {
    case Filled(_) => true
    case Empty     => false
    }
   
   def prettyPrint(): Unit = {
    for (row <- 0 until size) {
      if (row % 3 == 0 && row != 0) println("-" * 21)
      for (col <- 0 until size) {
        if (col % 3 == 0 && col != 0) print("| ")
        val value = cells(row)(col) match {
          case Empty      => "."
          case Filled(v)  => v.toString
        }
        print(s"$value ")
      }
      println()
    }
    println()
  }
}

object Board {
  def empty(size: Int = 9): Board =
    Board(Vector.fill(size, size)(Empty))
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.