error id: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/ui/SudokuCLI.scala:`<none>`.
file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/ui/SudokuCLI.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 2329
uri: file:///C:/Users/dima-/OneDrive/Desktop/course-work/course-work/src/main/scala/ui/SudokuCLI.scala
text:
```scala
package ui

import scala.io.StdIn.readLine
import core.GameService
import model._

class SudokuCLI {

  private val gameService = new GameService()
  private var running = true

  def run(): Unit = {
    while (running) {
      printMenu()
      val choice = readLine().trim
      handleChoice(choice)
    }
  }

  private def printMenu(): Unit = {
    println(
      """
=== Sudoku Generator ===
1. Выбрать уровень сложности (Easy, Medium, Hard)
2. Сгенерировать новую головоломку
3. Показать текущую головоломку
4. Показать решение текущей головоломки
5. Ввести решение для проверки
6. Выход
7. Ввести значение в ячейку
Выберите пункт меню:
""".stripMargin)
  }

  private def handleChoice(choice: String): Unit = {
    choice match {
      case "1" => chooseDifficulty()
      case "2" => generatePuzzle()
      case "3" => showPuzzle()
      case "4" => showSolution()
      case "5" => inputSolution()
      case "6" => exit()
      case "7" => inputSingleCell()
      case _   => println("Некорректный выбор, попробуйте ещё раз.")
    }
  }

  private def chooseDifficulty(): Unit = {
    println("Введите уровень сложности (easy, medium, hard):")
    val input = readLine().trim.toLowerCase
    gameService.setDifficulty(input)
    println(s"Уровень сложности установлен на: $input")
  }

  private def generatePuzzle(): Unit = {
    println("Генерируем судоку...")
    gameService.generateNewPuzzle() match {
      case Some(board) =>
        println("Головоломка сгенерирована:")
        board.prettyPrint()
      case None =>
        println("Не удалось сгенерировать головоломку.")
    }
  }

  private def showPuzzle(): Unit = {
    gameService.getCurrentPuzzle match {
      case Some(board) =>
        println("Текущая головоломка:")
        board.prettyPrint()
      case None =>
        println("Головоломка не сгенерирована.")
    }
  }

  private def showSolution(): Unit = {
    gameService.getCurrentSolution match {
      case Some(solution) =>
        println("Решение головоломки:")
        solution.prettyPrint()
      case None =>
        println("Решение недоступно.")
    }
  }

  private def inputSolution(): Unit = {
    gameService.getCurrentPuzzle match {
      case Some(_) =>
        println("Введите ваше @@решение построчно (9 строк по 9 цифр, 0 для пустых):")
        val userRows = (1 to 9).map(_ => readLine().trim)
        if (userRows.exists(_.length != 9)) {
          println("Некорректный ввод. Каждая строка должна содержать ровно 9 символов.")
        } else {
          val userBoard = Board(userRows.map { rowStr =>
            rowStr.map {
              case '0' => Empty
              case ch if ch.isDigit => Filled(ch.asDigit)
              case _ => Empty
            }.toVector
          }.toVector)

          if (gameService.checkUserSolution(userBoard)) {
            println("Ваше решение корректно!")
          } else {
            println("Решение некорректно или содержит ошибки.")
          }
        }
      case None =>
        println("Сначала сгенерируйте головоломку.")
    }
  }

  private def exit(): Unit = {
    println("Выход.")
    running = false
  }
  
  private def inputSingleCell(): Unit = {
  currentPuzzle match {
    case Some(board) =>
      println("Введите координаты и значение (в формате: row col value), от 1 до 9:")
      val input = readLine().trim.split("\\s+")
      if (input.length != 3) {
        println("Неверный формат. Пример: 3 4 7")
        return
      }
      val Array(rStr, cStr, vStr) = input
      val (row, col, value) = (rStr.toInt - 1, cStr.toInt - 1, vStr.toInt)

      if (row >= 0 && row < 9 && col >= 0 && col < 9 && value >= 1 && value <= 9) {
        val updated = board.updated(row, col, Filled(value))
        currentPuzzle = Some(updated)
        println("Значение обновлено:")
        updated.prettyPrint()
      } else {
        println("Некорректные координаты или значение.")
      }

    case None =>
      println("Сначала сгенерируйте головоломку.")
  }
}

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.