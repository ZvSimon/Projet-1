package sudoku

import zio.*
import zio.stream.ZStream
import java.io.{File, IOException}
import zio.json.*

object Main extends ZIOAppDefault {

  // It allows to create instances of MalformedFile with a custom error message 
  // that describes the specific reason for the file being considered malformed.
  case class MalformedFile(message: String) extends IOException(message)
  
  //Implementation of a Sudoku solver using backtracking.
  // It defines a Board type, which is a 9x9 grid represented as a list of lists of integers.
  type Board = List[List[Int]]

  //The Sudoku case class encapsulates a Sudoku puzzle board.
  case class Sudoku(sudoku: Board)

  // These 2 methods generates a JSON decoder/encoder for the Sudoku class based on its structure
  // and the available type class instances for decoding/encoding its fields.
  implicit val decoder: JsonDecoder[Sudoku] = DeriveJsonDecoder.gen[Sudoku]
  implicit val encoder: JsonEncoder[Sudoku] = DeriveJsonEncoder.gen[Sudoku]

  val header: String = "+-------+-------+-------+"

  // The purpose of this function is to take a sudoku board, format it with appropriate separators and line breaks,
  // and print the beautified board to the console using Console.printLine.
  // The purpose of this function is to beautify and print the provided sudoku board to the console.
  def beautifySudoku(sudoku: Board): IO[IOException, Unit] = Console.printLine(
    "Solution\n".concat(
      sudoku
        .grouped(3)
        .map(_.map(_.grouped(3).map(_.mkString(" ", " ", " ")).mkString("|", "|", "|")).mkString("\n"))
        .mkString(s"$header\n", s"\n$header\n", s"\n$header")
    )
  )
  
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

    //This function takes a Sudoku board (sudoku) as input and checks if there are no duplicate non-zero elements in any row, column, or grid.
  def doesntHasSameElementManyTimes(sudoku: Board): Boolean = {
    sudoku.flatMap(row => row.filter(_ != 0).groupBy(identity).view.mapValues(_.size).find(_._2 > 1)).isEmpty
  }

  //This function takes a Sudoku board (sudoku) as input and checks if the Sudoku puzzle is malformed, meaning it is not a valid Sudoku puzzle.
  // It first checks if the size of the Sudoku board is 9x9 by comparing the size of sudoku with 9 and the count of sublists with length 9 with 9.
  // If the size is correct, it proceeds to perform further validations.
  // Otherwise, it returns true immediately, indicating a malformed puzzle.
  def isSudokuMalformed(sudoku: Board): Boolean = {
    val isTableSizeCorrect = sudoku.size == 9 && sudoku.map(_.length).count(l => l == 9) == 9
    if (isTableSizeCorrect)
      val areValuesValidated = !sudoku.flatten.exists(l => l > 9 || l < 0)
      val areRowsValidated = doesntHasSameElementManyTimes(sudoku)
      val areColumnsValidated = doesntHasSameElementManyTimes((0 to 8).map(column => sudoku.map(row => row(column))).toList)
      val areGridsValidated = doesntHasSameElementManyTimes(
        sudoku
          .grouped(3)
          .toList
          .map(_.transpose.flatten)
          .flatMap(l => (0 to 2).map(group => l.slice(group * 9, group * 9 + 9)))
      )
      !(areValuesValidated && areRowsValidated && areColumnsValidated && areGridsValidated)
    else true
  }
  
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
  
  // Reading the contents of the file specified by the path.
  // "ZStream.fromFile(new File(path)).runCollect" creates a ZStream from the file and collects all its contents into a chunk.
  // The collected file contents are then transformed into a String by converting each element of the chunk to a Char and joining them together using mkString.
  // The resulting String is then parsed as JSON and converted to a Sudoku object using the fromJson function.
  // If the conversion fails, a Left value is returned, indicating a malformed file. If successful, a Right value is returned with the Sudoku object.
  // If the file is malformed (resulting in a Left value), a MalformedFile error is raised using ZIO.fail.
  // If the file is valid (resulting in a Right value), the Sudoku table within the Sudoku object is checked for any malformation using the isSudokuMalformed function.
  //If the Sudoku table is malformed, a MalformedFile error is raised.
  //If the Sudoku table is valid, the solve function is called with the Sudoku table to attempt to find a solution.
  //If a solution is found (resulting in a non-empty list), the first solution is printed using Console.printLine.
  def run: ZIO[Any, Throwable, Unit] = {
    for {
      _ <- Console.print("Enter the path to the JSON file containing the Sudoku problem: ")
      path <- Console.readLine
      _ <- Console.printLine(s"You entered: $path")
      file <- ZStream.fromFile(new File(path))
        .runCollect
        .flatMap {
          _.toList.map(_.toChar).mkString.fromJson[Sudoku] match {
            case Left(value) => ZIO.fail(MalformedFile(s"file is malformed, can NOT decode json file to Scala Class! Please check your json file '$path'"))
            case Right(value) => isSudokuMalformed(value.sudoku) match {
              case true => ZIO.fail(MalformedFile(s"Sudoku table is malformed, check all values in '$path'"))
              case false => solve(value.sudoku) match {
                case head :: tail => beautifySudoku(head)
              }
            }
          }
        }
      _ <- Console.printLine("Job is ending...")
    } yield ()
  }
  
}
