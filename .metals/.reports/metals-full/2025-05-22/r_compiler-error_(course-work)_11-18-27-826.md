file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/ui/SudokuApp.scala
### dotty.tools.dotc.MissingCoreLibraryException: Could not find package scala from compiler core libraries.
Make sure the compiler core libraries are on the classpath.
   

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/ui/SudokuApp.scala
text:
```scala
package ui

import scalafx.application.JFXApp3
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



#### Error stacktrace:

```
dotty.tools.dotc.core.Denotations$.select$1(Denotations.scala:1321)
	dotty.tools.dotc.core.Denotations$.recurSimple$1(Denotations.scala:1349)
	dotty.tools.dotc.core.Denotations$.recur$1(Denotations.scala:1351)
	dotty.tools.dotc.core.Denotations$.staticRef(Denotations.scala:1355)
	dotty.tools.dotc.core.Symbols$.requiredPackage(Symbols.scala:943)
	dotty.tools.dotc.core.Definitions.ScalaPackageVal(Definitions.scala:215)
	dotty.tools.dotc.core.Definitions.ScalaPackageClass(Definitions.scala:218)
	dotty.tools.dotc.core.Definitions.AnyClass(Definitions.scala:281)
	dotty.tools.dotc.core.Definitions.syntheticScalaClasses(Definitions.scala:2161)
	dotty.tools.dotc.core.Definitions.syntheticCoreClasses(Definitions.scala:2176)
	dotty.tools.dotc.core.Definitions.init(Definitions.scala:2192)
	dotty.tools.dotc.core.Contexts$ContextBase.initialize(Contexts.scala:921)
	dotty.tools.dotc.core.Contexts$Context.initialize(Contexts.scala:544)
	dotty.tools.dotc.interactive.InteractiveDriver.<init>(InteractiveDriver.scala:41)
	dotty.tools.pc.MetalsDriver.<init>(MetalsDriver.scala:32)
	dotty.tools.pc.ScalaPresentationCompiler.newDriver(ScalaPresentationCompiler.scala:99)
```
#### Short summary: 

dotty.tools.dotc.MissingCoreLibraryException: Could not find package scala from compiler core libraries.
Make sure the compiler core libraries are on the classpath.
   