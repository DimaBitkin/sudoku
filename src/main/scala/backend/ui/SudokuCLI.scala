package backend.ui

import scala.io.StdIn.readLine
import backend.core.GameService
import backend.model._
import scala.util.Try

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
        |=== Sudoku Generator ===
        |1. Выбрать уровень сложности (Easy, Medium, Hard)
        |2. Сгенерировать новую головоломку
        |3. Показать текущую головоломку
        |4. Показать решение текущей головоломки
        |5. Ввести значение в ячейку
        |6. Выход
        |Выберите пункт меню:
        |""".stripMargin)
  }

  private def handleChoice(choice: String): Unit = {
    choice match {
      case "1" => chooseDifficulty()
      case "2" => generatePuzzle()
      case "3" => showPuzzle()
      case "4" => showSolution()
      case "5" => inputSingleCell()
      case "6" => exit()
      case _   => println("Некорректный выбор, попробуйте ещё раз.")
    }
  }

  private def chooseDifficulty(): Unit = {
    val validLevels = Set("easy", "medium", "hard")

    Iterator
      .continually {
        println("Введите уровень сложности (easy, medium, hard):")
        readLine().trim.toLowerCase
      }
      .dropWhile(!validLevels.contains(_))
      .take(1)
      .foreach { level =>
        gameService.setDifficulty(level)
        println(s"Уровень сложности установлен на: $level")
      }
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

  
  private def exit(): Unit = {
    println("Выход.")
    running = false
  }


  private def inputSingleCell(): Unit = {
    gameService.getCurrentPuzzle match {
      case None =>
        println("Сначала нужно сгенерировать головоломку.")

      case Some(_) =>
        println("Режим ввода по одной ячейке.")
        println("Введите: row col value (1-9), или value = 0 для удаления.")
        println("Введите 'exit', чтобы выйти из режима.")

        Iterator
          .continually {
            print("-> ")
            readLine().trim
          }
          .takeWhile(input => !input.equalsIgnoreCase("exit"))
          .foreach { input =>
            val parts = input.split("\\s+")
            if (parts.length != 3) {
              println("Неверный формат. Пример: 3 4 7")
            } else {
              parseInput(parts) match {
                case Some((row, col, value)) =>
                  if (!inBounds(row, col, value)) {
                    println("Координаты или значение вне допустимого диапазона.")
                  } else if (!gameService.isCellEditable(row, col)) {
                    println("Эта клетка недоступна для изменения.")
                  } else {
                    handleCellInput(row, col, value)
                  }

                case None =>
                  println("Ошибка ввода. Убедитесь, что ввели три числа или 'exit'.")
              }
            }
          }
    }
  }

  private def parseInput(parts: Array[String]): Option[(Int, Int, Int)] =
    Try {
      val Array(rStr, cStr, vStr) = parts
      (rStr.toInt - 1, cStr.toInt - 1, vStr.toInt)
    }.toOption

  private def inBounds(row: Int, col: Int, value: Int): Boolean =
    row >= 0 && row < 9 && col >= 0 && col < 9 && value >= 0 && value <= 9

  private def handleCellInput(row: Int, col: Int, value: Int): Unit = {
    if (value == 0) {
      if (gameService.updateUserCell(row, col, 0)) println("Значение удалено.")
    } else {
      gameService.getCurrentSolution match {
        case Some(solution) =>
          solution.get(row, col) match {
            case Filled(expectedValue) if value == expectedValue =>
              if (gameService.updateUserCell(row, col, value)) println("Значение записано.")
            case Filled(_) =>
              println("Неверное значение!")
            case Empty =>
              println("Ошибка: в решении эта клетка пуста.")
          }
        case None =>
          println("Решение отсутствует.")
      }
    }

    println("Текущая доска:")
    gameService.getCurrentPuzzle.foreach(_.prettyPrint())
  }
}

