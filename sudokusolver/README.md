## sbt project compiled with Scala 3

### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).

#  Sudoku Solver in Scala

## Topic

 Sudoku solver in Scala 3 using ZIO

## Description

Sudoku is a popular logic-based puzzle game played on a 9x9 grid. The grid is divided into 9 rows, 9 columns, and 9 3x3 sub-grids. The objective is to fill the grid with digits from 1 to 9 such that each row, each column, and each sub-grid contains all the digits from 1 to 9 without any repetition. A partially filled Sudoku grid is given as input, and the solver should determine a solution if one exists.

### Unresolved Sudoku Grid

| <!-- -->| <!-- --> | <!-- --> | <!-- --> | <!-- --> | <!-- --> |<!-- -->| <!-- --> | <!-- --> |
|---|---|---|---|---|---|---|---|---|
| 5 | 3 | 0 | 0 | 7 | 0 | 0 | 0 | 0 |
| 6 | 0 | 0 | 1 | 9 | 5 | 0 | 0 | 0 |
| 0 | 9 | 8 | 0 | 0 | 0 | 0 | 6 | 0 |
| 8 | 0 | 0 | 0 | 6 | 0 | 0 | 0 | 3 |
| 4 | 0 | 0 | 8 | 0 | 3 | 0 | 0 | 1 |
| 7 | 0 | 0 | 0 | 2 | 0 | 0 | 0 | 6 |
| 0 | 6 | 0 | 0 | 0 | 0 | 2 | 8 | 0 |
| 0 | 0 | 0 | 4 | 1 | 9 | 0 | 0 | 5 |
| 0 | 0 | 0 | 0 | 8 | 0 | 0 | 7 | 9 |

### Solution
| <!-- -->| <!-- --> | <!-- --> | <!-- --> | <!-- --> | <!-- --> |<!-- -->| <!-- --> | <!-- --> |
|---|---|---|---|---|---|---|---|---|
| 5 | 3 | 4 | 6 | 7 | 8 | 9 | 1 | 2 |
| 6 | 7 | 2 | 1 | 9 | 5 | 3 | 4 | 8 |
| 1 | 9 | 8 | 3 | 4 | 2 | 5 | 6 | 7 |
| 8 | 5 | 9 | 7 | 6 | 1 | 4 | 2 | 3 |
| 4 | 2 | 6 | 8 | 5 | 3 | 7 | 9 | 1 |
| 7 | 1 | 3 | 9 | 2 | 4 | 8 | 5 | 6 |
| 9 | 6 | 1 | 5 | 3 | 7 | 2 | 8 | 4 |
| 2 | 8 | 7 | 4 | 1 | 9 | 6 | 3 | 5 |
| 3 | 4 | 5 | 2 | 8 | 6 | 1 | 7 | 9 |

