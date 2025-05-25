error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/model/Board.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/model/Board.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 521
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/model/Board.scala
text:
```scala
package model

case class Board(cells: Vector[Vector[Cell]]) {
  def size: Int = cells.length

  def get(row: Int, col: Int): Cell =
    cells(row)(col)

  def updated(row: Int, col: Int, cell: Cell): Board = {
    val updatedRow = cells(row).updated(col, cell)
    Board(cells.updated(row, updatedRow))
  }

  def print: String =
    cells.map(_.map {
      case Empty        => "."
      case Filled(value) => value.toString
    }.mkString(" ")).mkString("\n")
}
    def isComplete: Boolean = rows.flatten.forall {
    @@case Filled(_) => true
    case Empty     => false
    }


object Board {
  def empty(size: Int = 9): Board =
    Board(Vector.fill(size, size)(Empty))
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.