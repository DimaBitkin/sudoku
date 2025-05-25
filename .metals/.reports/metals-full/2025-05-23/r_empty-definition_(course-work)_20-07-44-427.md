error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala:
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb

found definition using fallback; symbol BoardF
offset: 1323
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/backend/model/Board.scala
text:
```scala
package backend.model
import backend.model.Cell



final case class BoardF[F[_]](cells: Vector[Vector[F[Cell]]]) {
  def map[G[_]](func: F[Cell] => G[Cell]): BoardF[G] =
    BoardF(cells.map(_.map(func)))

  def size: Int = cells.length

  def get(row: Int, col: Int): F[Cell] =
    cells(row)(col)

  def updated(row: Int, col: Int, cell: F[Cell]): BoardF[F] = {
    val updatedRow = cells(row).updated(col, cell)
    BoardF(cells.updated(row, updatedRow))
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

object BoardF {
  type Identity[A] = A

  // Примитивная инстанциация с конкретным эффектом
def pure[F[_]](cells: Vector[Vector[Cell]])(implicit F: Applicative[F]): BoardF[F] =
  @@BoardF(cells.map(_.map(F.pure)))
  def empty[F[_]](size: Int)(implicit F: Applicative[F]): BoardF[F] =
    BoardF(Vector.fill(size, size)(F.pure(Empty)))
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 