# Sudoku Solver

This project is a Sudoku solver implemented in Scala 3 using the ZIO library. It provides a command-line interface to solve Sudoku puzzles and display the solutions.

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


## Table of Contents
- [Overview](#overview)
- [Instructions](#instructions)
    - [Libraries](#libraries)
    - [Data Structure](#data-structure)
    - [Algorithm](#algorithm)
- [Performance](#performance)

## Overview
Sudoku Solver is a command-line application that takes a Sudoku puzzle as input, solves it using backtracking, and displays the solutions. It supports solving both 9x9 Sudoku puzzles.

The application provides the following features:
- Input a Sudoku puzzle from a JSON file.
- Check if the specified file path is correct.
- Verify if the Sudoku puzzle is well-formed (valid structure and values).
- Solve the Sudoku puzzle if it has one or more solutions. 
- Display the first solution in a formatted manner. 
- If there is no solution, give an error message.


## Instructions
To run the Sudoku solver, follow these steps:

1. Clone the repository to your local machine.
2. Make sure you have [Scala](https://www.scala-lang.org/) and [sbt](https://www.scala-sbt.org/) installed.
3. Open a terminal and navigate to the project directory.
4. Compile the project using the following command: `sbt compile`
5. Run the application using the following command: `sbt run`
6. Follow the on-screen prompts to enter the path to the JSON file containing the Sudoku puzzle.
7. The application will solve the puzzle and display the first solution found.


### Libraries
The following libraries were used in the implementation of the Sudoku solver:
- **ZIO**: ZIO is a functional effect system for Scala. It provides a powerful way to manage effects and concurrency in a purely functional manner.
- **ZIO JSON**: ZIO JSON is a library for JSON encoding and decoding in ZIO applications. It is used to parse the Sudoku puzzle from a JSON file.
- **ZStream**: ZStream is a stream processing library in ZIO. It is used to read the contents of the Sudoku puzzle JSON file.

### Data Structure
The Sudoku puzzle is represented as a 9x9 grid, which is a list of lists of integers (`type Board = List[List[Int]]`). This data structure allows easy access and manipulation of the puzzle cells.

### Algorithm
The algorithm searches for different possible values for each empty cell (cell with the value 0) in the clipboard. Sudoku solver tries all possibilities to find a valid solution for the puzzle. Controls whether a value can be placed in a specific position without violating any Sudoku rules. If a value is valid, it is placed in the cell and the algorithm moves to the next cell. This process continues until all possible combinations are discovered.

## Performance
The performance of the Sudoku solver depends on the complexity of the puzzle. In the worst case, where the puzzle is empty (all cells are initially 0), the algorithm will need to explore all possible combinations to find a solution. This results in an exponential time complexity, making it infeasible to solve Sudoku puzzles of larger sizes efficiently.
