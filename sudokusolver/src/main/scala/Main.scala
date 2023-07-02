package sudoku

import zio._

object Main extends ZIOAppDefault {
  
  //Implementation of a Sudoku solver using backtracking.
  // It defines a Board type, which is a 9x9 grid represented as a list of lists of integers.
  type Board = List[List[Int]]

  //The Sudoku case class encapsulates a Sudoku puzzle board.
  case class Sudoku(sudoku: Board)
  
  //The validate function checks if a given value can be placed at a specific position (x, y) in the Sudoku board without violating any Sudoku rules.
  // It checks the row, column, and the 3x3 box containing the position for the presence of the value.
  def validate(sudoku: Board, x: Int, y: Int, value: Int): Boolean = {
    val rowProperty: Boolean = !sudoku(y).contains(value)
    val columnProperty: Boolean = !sudoku.map(r => r(x)).contains(value)

    val box = ((y / 3 * 3) until (y / 3 * 3 + 3)).flatMap(_y =>
      ((x / 3 * 3) until (x / 3 * 3 + 3)).map(_x => sudoku(_y)(_x))
    )

    val boxProperty: Boolean = !box.contains(value)
    rowProperty && columnProperty && boxProperty
  }
  
  //The update function takes a Sudoku board and updates the value at a specific position (x, y) with the given value.
  def update(sudoku: Board, x: Int, y: Int, value: Int): Board =
    sudoku.take(x) ++ List(sudoku(x).take(y) ++ List(value) ++ sudoku(x).drop(y + 1)) ++ sudoku.drop(x + 1)

  //The solve function is the main solver function.
  // It uses backtracking to find a valid solution for the Sudoku puzzle.
  // It takes a Sudoku board as input, along with optional parameters x and y representing the current position being processed.
  // It recursively explores different possible values for each empty cell (cell with value 0) in the board, using the validate and update functions.
  // It backtracks whenever it reaches an invalid state. The function returns a list of all valid solutions found.
  def solve(sudoku: Board, x: Int = 0, y: Int = 0): List[Board] = (x, y) match {
    case _ if y >= 9 => List(sudoku)
    case _ if x >= 9 => solve(sudoku, 0, y + 1)
    case _ if sudoku(y)(x) > 0 => solve(sudoku, x + 1, y)
    case _ => (1 to 9).filter(validate(sudoku, x, y, _)).flatMap { value => solve(update(sudoku, y, x, value), x + 1, y) }.toList
  }
  
  def run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- Console.print("Enter the path to the JSON file containing the Sudoku problem:")
      path <- Console.readLine
      _ <-  Console.printLine(s"You entered: $path")
      // Add your Sudoku solver logic here, utilizing ZIO and interacting with the ZIO Console
    } yield ()
}
