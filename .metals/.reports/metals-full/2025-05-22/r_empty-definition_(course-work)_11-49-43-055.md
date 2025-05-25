error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/ui/SudokuApp.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/ui/SudokuApp.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -model/scalafx.
	 -scalafx.
	 -scala/Predef.scalafx.
offset: 24
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/ui/SudokuApp.scala
text:
```scala
package ui

import scala@@fx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.{GridPane, VBox, HBox}
import scalafx.scene.control.{Button, ComboBox, Label, TextField, Alert, AlertType}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.text.Font
import core.GameService
import model._

class SudokuApp extends JFXApp3 {

  private val gameService = new GameService()
  private val cells = Array.ofDim[TextField](9, 9)

  override def start(): Unit = {
    val grid = new GridPane {
      hgap = 5
      vgap = 5
      padding = Insets(10)
      alignment = Pos.Center
    }

    for (r <- 0 until 9; c <- 0 until 9) {
      val cell = new TextField {
        prefWidth = 40
        alignment = Pos.Center
        font = Font.font(16)
      }
      cells(r)(c) = cell
      grid.add(cell, c, r)
    }

    val difficultyBox = new ComboBox(Seq("easy", "medium", "hard")) {
      value = "medium"
    }

    val generateBtn = new Button("Генерировать") {
      onAction = _ => {
        gameService.setDifficulty(difficultyBox.value())
        gameService.generateNewPuzzle().foreach { board =>
          for (r <- 0 until 9; c <- 0 until 9) {
            board.get(r, c) match {
              case Filled(v) =>
                cells(r)(c).text = v.toString
                cells(r)(c).editable = false
              case Empty =>
                cells(r)(c).text = ""
                cells(r)(c).editable = true
            }
          }
        }
      }
    }

    val checkBtn = new Button("Проверить решение") {
      onAction = _ => {
        val userBoard = Board((0 until 9).map { r =>
          (0 until 9).map { c =>
            val text = cells(r)(c).text.value.trim
            if (text.isEmpty || text == "0") Empty
            else Filled(text.toInt)
          }.toVector
        }.toVector)

        val valid = gameService.checkUserSolution(userBoard)
        val alert = new Alert(AlertType.Information) {
          title = "Проверка"
          contentText = if (valid) "Правильно!" else "Неверно."
        }
        alert.showAndWait()
      }
    }

    val solveBtn = new Button("Показать решение") {
      onAction = _ => {
        gameService.getCurrentSolution.foreach { solution =>
          for (r <- 0 until 9; c <- 0 until 9) {
            solution.get(r, c) match {
              case Filled(v) => cells(r)(c).text = v.toString
              case Empty     => cells(r)(c).text = ""
            }
          }
        }
      }
    }

    stage = new JFXApp3.PrimaryStage {
      title = "Sudoku GUI"
      scene = new Scene {
        root = new VBox(10) {
          padding = Insets(20)
          children = Seq(
            new HBox(10, new Label("Сложность:"), difficultyBox, generateBtn, checkBtn, solveBtn),
            grid
          )
        }
      }
    }
  }
}


```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.