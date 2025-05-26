package backend.core

import backend.model._
import backend.core.{PuzzleGenerator, Solver}

class GameService {

  // Текущая доска (в процессе игры)
  private var currentPuzzle: Option[Board] = None

  // Решение текущей головоломки
  private var currentSolution: Option[Board] = None

  // Уровень сложности (по умолчанию medium)
  private var difficulty: String = "medium"

  // Исходная (начальная) головоломка — нужна для определения, какие клетки можно изменять
  private var initialPuzzle: Option[Board] = None


  /* Установить уровень сложности для следующей головоломки.
    * 
    * @param level Строка, содержащая один из вариантов: "easy", "medium", "hard".
    *              Если введено что-то другое — используется "medium" по умолчанию.
    */
  def setDifficulty(level: String): Unit = {
    difficulty = level.toLowerCase match {
      case "easy" | "medium" | "hard" => level.toLowerCase
      case _                          => "medium"
    }
  }

  /* Сгенерировать новую головоломку согласно текущему уровню сложности.
    *
    * Сохраняет:
    * - текущую головоломку (`currentPuzzle`)
    * - начальную версию (`initialPuzzle`)
    * - решение (`currentSolution`), если оно существует.
    * 
    * @return `Some(board)` если генерация успешна, иначе `None`
    */
  def generateNewPuzzle(): Option[Board] = {
    
    val puzzleOpt = PuzzleGenerator.generatePuzzle(difficulty)
    currentPuzzle = puzzleOpt
    initialPuzzle = puzzleOpt  // сохранить исходную
    currentSolution = puzzleOpt.flatMap(board => Solver.solveAll(board, limit = 1).headOption)
    puzzleOpt
  }

  /** Получить текущую головоломку */
  def getCurrentPuzzle: Option[Board] = currentPuzzle

  /** Получить решение текущей головоломки */
  def getCurrentSolution: Option[Board] = currentSolution


  /* Установить доску вручную (например, для тестов).
    *
    * @param board Доска, которая становится текущей.
    */
  def setCurrentPuzzle(board: Board): Unit = {
  currentPuzzle = Some(board)
  }


  /* Проверить правильность пользовательского решения.
    *
    * @param userBoard Доска, введённая пользователем.
    * @return true, если:
    *         - доска корректна по правилам судоку (без дубликатов)
    *         - совпадает с предварительно вычисленным решением
    */
  def checkUserSolution(userBoard: Board): Boolean = {
    if (!isValidBoard(userBoard)) return false
    // Проверяем, что пользовательское решение совпадает с решением
    currentSolution match {
      case Some(solution) => boardsEqual(solution, userBoard)
      case None => false
    }
  }
  /* Проверка доски на соответствие правилам судоку:
    * - в каждой строке, столбце и 3x3-блоке нет повторяющихся чисел (кроме нулей).
    *
    * @param board Доска для валидации.
    * @return true, если доска допустима.
    */
  private def isValidBoard(board: Board): Boolean = {
    val size = board.size

    def noDuplicates(values: Seq[Int]): Boolean = {
      val nonZero = values.filter(_ != 0)
      nonZero.distinct.size == nonZero.size
    }

    val rowsValid = (0 until size).forall { r =>
      noDuplicates((0 until size).map(c => board.get(r, c) match {
        case Filled(v) => v
        case Empty => 0
      }))
    }

    val colsValid = (0 until size).forall { c =>
      noDuplicates((0 until size).map(r => board.get(r, c) match {
        case Filled(v) => v
        case Empty => 0
      }))
    }

    val blocksValid = (0 until size by 3).forall { br =>
      (0 until size by 3).forall { bc =>
        noDuplicates(for {
          r <- br until br + 3
          c <- bc until bc + 3
        } yield board.get(r, c) match {
          case Filled(v) => v
          case Empty => 0
        })
      }
    }

    rowsValid && colsValid && blocksValid
  }
  

  /** Сравнение двух досок */
  private def boardsEqual(b1: Board, b2: Board): Boolean = {
    (0 until b1.size).forall { r =>
      (0 until b1.size).forall { c =>
        b1.get(r, c) == b2.get(r, c)
      }
    }
  }
  //Метод для проверки "можно ли редактировать клетку":
  def isCellEditable(row: Int, col: Int): Boolean = {
    initialPuzzle match {
      case Some(board) =>
        board.get(row, col) match {
          case Filled(_) => false
          case Empty     => true
        }
      case None => true // если по какой-то причине нет исходной — разрешить
    }
  }

  /* Обновить значение клетки, введённое пользователем.
    *
    * @param row Номер строки (0–8).
    * @param col Номер столбца (0–8).
    * @param value Значение от 1 до 9, или 0 для удаления.
    * @return true, если обновление успешно.
    */
  def updateUserCell(row: Int, col: Int, value: Int): Boolean = {
    if (!isCellEditable(row, col) || currentPuzzle.isEmpty) return false

    val newCell = if (value == 0) Empty else Filled(value)
    currentPuzzle = currentPuzzle.map(_.updated(row, col, newCell))
    true
  }
}
