# Minesweeper and Solver implemented in Java.

# Building and running the application

1. git clone https://github.com/thebrettd/JavaSweeper.git
2. cd JavaSweeper
3. mvn clean compile assembly:single
4. cd target
5. java -jar minesweeper-1.0-SNAPSHOT-jar-with-dependencies.jar

## Command Line Game

Pretty self-explanatory. Enter a grid size N (will generate an NxN square grid) and the desired number of mines.

You will be prompted for the x and y coordinates of the cell you want to click.

Game ends when you enter the coordinates of a mine, or all non-mine squares are revealed.

If you reveal a square with 0 adjacent mines, the game will automatically reveal all adjacent cells.

For each of play, x and y indices are printed along the perimeter of the grid.

## Solver

Code for two solvers are included - but the program is hardcoded to use BrettSolver.

In a real world scenario, I would use dependency injection to control which solver is used at runtime.

Per specification, the number of simulations is hardcoded to 1000 via the constant MineSweeperSolver.simulations

### Brett Solver
Three phase solver 

This solver achieves a solve rate of 62.7% over 100,000 games.
 
>Solving 100000.0 games took 126341 milliseconds
>Solved 63017 games
>Win percentage: 63.017

The solver loops through the following phases

#### Click a random square and pray.
To start the process, we must first click on a random square. Once a simulation is started, we skip over known mines or 
revealed cells.

#### Mine detection
Search through the map, and find any cells where the cell's mine count is equal to the number of hidden cells that it is
touching. We know those all of those hidden cells are mines.

#### Cell Defusal
Search through the map, and find any cells where we know the location of exactly as many mines as it is touching.
Click all of the non-mine cells surround that cell.

### Random Solver
Toy implementation which simply clicks around randomly.

This solver achieves an impressive .027% solve rate over 100,000 simulations.

>Solving 100000.0 games took 8753 milliseconds
>Solved 27 games
>Win percentage: 0.027

## Dependencies

* Java 1.5 or later
* Maven for dependency management. 
* JUnit is used for Unit Testing.
* Apache commons-lang3 is used for StopWatch and StringUtils classes.

