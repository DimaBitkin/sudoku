error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/model/Cell.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/model/Cell.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 124
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/model/Cell.scala
text:
```scala
package model

sealed trait Cell
case object Empty extends Cell
case class Filled(value: Int) extends Cell

object Cell {
  @@def isEmpty(cell: Cell): Boolean = cell == Empty
}


```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.