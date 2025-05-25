package backend.ui

import scala.io.StdIn.readLine
import backend.core.GameService
import backend.model._

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
    var valid = false
    var input = ""

    while (!valid) {
      println("Введите уровень сложности (easy, medium, hard):")
      input = readLine().trim.toLowerCase
      input match {
        case "easy" | "medium" | "hard" =>
          valid = true
        case _ =>
          println("Некорректный уровень сложности. Пожалуйста, введите: easy, medium или hard.")
      }
    }

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

  
  private def exit(): Unit = {
    println("Выход.")
    running = false
  }


  private def inputSingleCell(): Unit = {
    gameService.getCurrentPuzzle match {
      case Some(_) =>
        println("Режим ввода по одной ячейке.")
        println("Введите: row col value (1-9), или value = 0 для удаления.")
        println("Введите 'exit', чтобы выйти из режима.")

        var continue = true
        while (continue) {
          print("-> ")
          val input = readLine().trim
          if (input.equalsIgnoreCase("exit")) {
            continue = false
          } else {
            val parts = input.split("\\s+")
            if (parts.length != 3) {
              println("Неверный формат. Пример: 3 4 7")
            } else {
              try {
                val Array(rStr, cStr, vStr) = parts
                val (row, col, value) = (rStr.toInt - 1, cStr.toInt - 1, vStr.toInt)

                if (row >= 0 && row < 9 && col >= 0 && col < 9 && value >= 0 && value <= 9) {
                  if (!gameService.isCellEditable(row, col)) {
                    println("Эта клетка недоступна для изменения.")
                  } else {
                    if (value == 0) {
                      // Удаление значения
                      val updated = gameService.updateUserCell(row, col, 0)
                      if (updated) println("Значение удалено.")
                    } else {
                      // Проверка перед записью
                      gameService.getCurrentSolution match {
                        case Some(solution) =>
                          solution.get(row, col) match {
                            case Filled(expectedValue) =>
                              if (value != expectedValue) {
                                println("Неверное значение!")
                                //  Не записываем неправильное значение
                              } else {
                                //  Верно — записываем
                                val updated = gameService.updateUserCell(row, col, value)
                                if (updated) println("Значение записано.")
                              }
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
                } else {
                  println("Координаты или значение вне допустимого диапазона.")
                }
              } catch {
                case _: NumberFormatException =>
                  println("Ошибка ввода. Убедитесь, что ввели три числа или 'exit'.")
              }
            }
          }
        }

      case None =>
        println("Сначала нужно сгенерировать головоломку.")
    }
  }




}
