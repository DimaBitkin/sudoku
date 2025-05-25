package backend.model

sealed trait Cell
case object Empty extends Cell
case class Filled(value: Int) extends Cell

object Cell {
   def isEmpty(cell: Cell): Boolean = cell match {
    case Empty => true
    case _ => false
  }
}

