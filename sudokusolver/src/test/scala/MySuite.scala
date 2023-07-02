// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.shouldBe
import sudoku.Main

class MySuite extends AnyFunSuite {

  // The test case asserts that the solve method can solve a solvable puzzle correctly if there is solution.
  test("testing Main.solve method can solve the Solvable Puzzle") {
    val unSolvedPuzzle = List(
      List(5, 3, 0, 0, 7, 0, 0, 0, 0),
      List(6, 0, 0, 1, 9, 5, 0, 0, 0),
      List(0, 9, 8, 0, 0, 0, 0, 6, 0),
      List(8, 0, 0, 0, 6, 0, 0, 0, 3),
      List(4, 0, 0, 8, 0, 3, 0, 0, 1),
      List(7, 0, 0, 0, 2, 0, 0, 0, 6),
      List(0, 6, 0, 0, 0, 0, 2, 8, 0),
      List(0, 0, 0, 4, 1, 9, 0, 0, 5),
      List(0, 0, 0, 0, 8, 0, 0, 7, 9)
    )

    val expectedPuzzle = List(
      List(5, 3, 4, 6, 7, 8, 9, 1, 2),
      List(6, 7, 2, 1, 9, 5, 3, 4, 8),
      List(1, 9, 8, 3, 4, 2, 5, 6, 7),
      List(8, 5, 9, 7, 6, 1, 4, 2, 3),
      List(4, 2, 6, 8, 5, 3, 7, 9, 1),
      List(7, 1, 3, 9, 2, 4, 8, 5, 6),
      List(9, 6, 1, 5, 3, 7, 2, 8, 4),
      List(2, 8, 7, 4, 1, 9, 6, 3, 5),
      List(3, 4, 5, 2, 8, 6, 1, 7, 9)
    )

    val solvedPuzzle = Main.solve(unSolvedPuzzle).head

    solvedPuzzle shouldBe expectedPuzzle
  }

  //  1 to 3 : It only verifies if the puzzle is solvable and not malformed
  
  test("1: solvable puzzles") {
    val actual = Main.isSudokuMalformed(
      List(
        List(5, 3, 0, 0, 7, 0, 0, 0, 0),
        List(6, 0, 0, 1, 9, 5, 0, 0, 0),
        List(0, 9, 8, 0, 0, 0, 0, 6, 0),
        List(8, 0, 0, 0, 6, 0, 0, 0, 3),
        List(4, 0, 0, 8, 0, 3, 0, 0, 1),
        List(7, 0, 0, 0, 2, 0, 0, 0, 6),
        List(0, 6, 0, 0, 0, 0, 2, 8, 0),
        List(0, 0, 0, 4, 1, 9, 0, 0, 5),
        List(0, 0, 0, 0, 8, 0, 0, 7, 9)
      )
    )
    val expected = false
    actual shouldBe expected
  }

  test("2: Empty Grids") {
    val actual = Main.isSudokuMalformed(List.empty)
    val expected = true
    actual shouldBe expected
  }

  test("3: Partially Filled Grids - not 9x9") {
    val actual = Main.isSudokuMalformed(
      List(
        List(5, 3, 0, 0, 7, 0),
        List(6, 0, 0, 1, 9, 5, 0, 0),
        List(0, 9, 8, 0, 0, 0, 0, 6, 0),
        List(8, 0, 0, 0),
        List(4, 0, 0, 8, 0, 3, 0, 0, 1),
        List(7, 0, 0, 0, 2, 0, 0, 0, 6),
        List(0, 6, 0, 0, 0, 0, 2, 8, 0),
        List(0, 0, 0, 4, 1, 9, 0, 0, 5),
        List(0, 0, 0, 0, 8, 0)
      )
    )
    val expected = true
    actual shouldBe expected
  }

  //  4 to 8 (added) : It only verifies if the puzzle is solvable and not malformed
  
  test("4: Filled more than 9 Grids in Rows - not 9x9") {
    val actual = Main.isSudokuMalformed(
      List(
        List(5, 3, 0, 0, 7, 0, 0, 0, 0, 9), // 10 elements invalid
        List(6, 0, 0, 1, 9, 5, 0, 0, 0, 0), // 10 elements invalid
        List(0, 9, 8, 0, 0, 0, 0, 6, 0),
        List(8, 0, 0, 0, 6, 0, 0, 0, 3),
        List(4, 0, 0, 8, 0, 3, 0, 0, 1),
        List(7, 0, 0, 0, 2, 0, 0, 0, 6),
        List(0, 6, 0, 0, 0, 0, 2, 8, 0),
        List(0, 0, 0, 4, 1, 9, 0, 0, 5),
        List(0, 0, 0, 0, 8, 0, 0, 7, 9),
      )
    )
    val expected = true
    actual shouldBe expected
  }

  test("5: Filled more than 9 Grids in Columns - not 9x9") {
    val actual = Main.isSudokuMalformed(
      List(
        List(5, 3, 0, 0, 7, 0, 0, 0, 0),
        List(6, 0, 0, 1, 9, 5, 0, 0, 0),
        List(0, 9, 8, 0, 0, 0, 0, 6, 0),
        List(8, 0, 0, 0, 6, 0, 0, 0, 3),
        List(4, 0, 0, 8, 0, 3, 0, 0, 1),
        List(7, 0, 0, 0, 2, 0, 0, 0, 6),
        List(0, 6, 0, 0, 0, 0, 2, 8, 0),
        List(0, 0, 0, 4, 1, 9, 0, 0, 5),
        List(0, 0, 0, 0, 8, 0, 0, 7, 9),
        List(0, 0, 0, 0, 0, 0, 0, 0, 0) // 10.th row invalid
      )
    )
    val expected = true
    actual shouldBe expected
  }

  // In this puzzle, the subgrid in the top-left corner (represented by the 3x3 square containing the numbers 5,3,0 | 6,0,3) is invalid 
  // because it contains duplicate value 3.
  test("6: Invalid Sudoku puzzles - Invalid subGrids 3x3") {
    val actual = Main.isSudokuMalformed(
      List(
        List(5, 3, 0, 0, 7, 0, 0, 0, 0),
        List(6, 0, 3, 1, 9, 5, 0, 0, 0),
        List(0, 9, 8, 0, 0, 0, 0, 6, 0),
        List(8, 0, 0, 0, 6, 0, 0, 0, 3),
        List(4, 0, 0, 8, 0, 3, 0, 0, 1),
        List(7, 0, 0, 0, 2, 0, 0, 0, 6),
        List(0, 6, 0, 0, 0, 0, 2, 8, 0),
        List(0, 0, 0, 4, 1, 9, 0, 0, 5),
        List(0, 0, 0, 0, 8, 0, 0, 7, 9)
      )
    )
    val expected = true
    actual shouldBe expected
  }

  // In this puzzle, the first row (row index 0) contains duplicate value 3 .
  // This makes the row invalid.
  test("7: Invalid Sudoku puzzles - Invalid Rows") {
    val actual = Main.isSudokuMalformed(
      List(
        List(5, 3, 0, 3, 7, 0, 0, 0, 0), // 3 : duplicate value in row
        List(6, 0, 0, 1, 9, 5, 0, 0, 0),
        List(0, 9, 8, 0, 0, 0, 0, 6, 0),
        List(8, 0, 0, 0, 6, 0, 0, 0, 3),
        List(4, 0, 0, 8, 0, 3, 0, 0, 1),
        List(7, 0, 0, 0, 2, 0, 0, 0, 6),
        List(0, 6, 0, 0, 0, 0, 2, 8, 0),
        List(0, 0, 0, 4, 1, 9, 0, 0, 5),
        List(0, 0, 0, 0, 8, 0, 0, 7, 9)
      )
    )
    val expected = true
    actual shouldBe expected
  }

  // In this puzzle, the 2nd column (column index 1) contains duplicate value 3. 
  // This makes the column invalid.
  test("8: Invalid Sudoku puzzles - Invalid Columns") {
    val actual = Main.isSudokuMalformed(
      List( 
        List(5, 3, 0, 0, 7, 0, 0, 0, 0),
        List(6, 0, 0, 1, 9, 5, 0, 0, 0),
        List(0, 9, 8, 0, 0, 0, 0, 6, 0),
        List(8, 0, 0, 0, 6, 0, 0, 0, 3),
        List(4, 0, 0, 8, 0, 3, 0, 0, 1),
        List(7, 0, 0, 0, 2, 0, 0, 0, 6),
        List(0, 6, 0, 0, 0, 0, 2, 8, 0),
        List(0, 0, 0, 4, 1, 9, 0, 0, 5),
        List(0, 3, 0, 0, 8, 0, 0, 7, 9)
      )//.......3 : duplicate value in column
    )
    val expected = true
    actual shouldBe expected
  }
  
  
}
