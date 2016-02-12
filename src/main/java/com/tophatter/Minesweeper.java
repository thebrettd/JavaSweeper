package com.tophatter;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.tophatter.io.Input.queryUserForInt;
import static com.tophatter.io.Input.getCellToClick;

/**
 * Created by brett on 2/10/16.
 */
public class Minesweeper {

    private final static Logger LOGGER = Logger.getLogger(Minesweeper.class.getName());

    public enum GameStatus {
        IN_PROGRESS, LOSS, VICTORY
    }

    private Board board;
    private GameStatus status = GameStatus.IN_PROGRESS;

    private int numMines;
    private int gridSize;
    private int numRevealed;

    public Minesweeper(int gridSize, int numMines) {
        this.numMines = numMines;
        this.gridSize = gridSize;

        numRevealed = 0;
        board = new Board(gridSize);

        plantMines();
        computeAdjacentMiness();
    }

    private void plantMines() {
        int minesPlanted = 0;

        Random generator = new Random();
        while (minesPlanted < numMines) {

            boolean plantingMine = true;
            while (plantingMine) {
                int randomX = generator.nextInt(board.getGridSize());
                int randomY = generator.nextInt(board.getGridSize());
                if (!(board.getCell(randomX, randomY).getDisplayValue().equals("M"))) {
                    board.putCell(randomX, randomY, new Cell("M"));
                    minesPlanted++;
                    plantingMine = false;
                }
            }
        }
    }

    private void computeAdjacentMiness() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Cell cell = board.getCell(x, y);
                if (!cell.getDisplayValue().equals("M")) {
                    cell.setDisplayValue(String.format("%s", countAdjacentMines(x, y)));
                }
            }
        }
    }

    public static void start(Scanner scanner) {
        int gridSize = queryUserForInt(scanner, "Enter size of the grid: ");
        int numMines = queryUserForInt(scanner, "Enter number of mines: ");

        Minesweeper game = new Minesweeper(gridSize, numMines);
        game.run(scanner);
    }

    private void run(Scanner scanner) {
        print();

        while (status.equals(GameStatus.IN_PROGRESS)) {
            Cell cellToClick = getCellToClick(scanner, getBoard());
            clickCell(cellToClick);

            switch (getStatus()) {
                case LOSS:
                    System.out.println("Sorry, you lost!");
                    break;
                case VICTORY:
                    System.out.println("Congrats, you win!");
                    break;
            }
            print();
        }
    }

    public void print() {
        getBoard().print();
    }

    public Cell clickCell(Cell cellToClick) {
        //Reveal the cell
        Cell clickedCell = revealCell(cellToClick.getX(), cellToClick.getY());

        //Update game status
        if (clickedCell.getDisplayValue().equals("M")) {
            this.status = GameStatus.LOSS;
        } else if (swept()) {
            this.status = GameStatus.VICTORY;
        } else {
            //LOGGER.log(Level.FINEST,"Phew, not a mine!");

            if (clickedCell.getDisplayValue().equals("0")) {
                //LOGGER.log(Level.FINEST,"Clicked on a square with no adjacent mine, automatically clicking all adjacent squares.");
                clickAllAdjacentCells(cellToClick);

                //Check for win conditions after autosweep
                if (swept()) {
                    this.status = GameStatus.VICTORY;
                }
            }
        }
        return clickedCell;
    }

    private void clickAllAdjacentCells(Cell clickedCell) {
        for (Cell autoClickedCell : computeAllAdjacentCells(clickedCell.getX(), clickedCell.getY())) {
            Cell autoCell = board.getCell(autoClickedCell.getX(), autoClickedCell.getY());

            if (autoCell.getStatus().equals(Cell.CellStatus.HIDDEN)) {
                revealCell(autoClickedCell.getX(), autoClickedCell.getY()); //We know its a 0 or we wouldnt be here
                if (autoCell.getDisplayValue().equals("0")) {
                    clickAllAdjacentCells(autoClickedCell);
                }
            }
        }
    }

    public Cell revealCell(int x, int y) {
        Cell cell = board.getCell(x, y);
        if (cell.getStatus() != Cell.CellStatus.REVEALED) {
            numRevealed++;
        }
        cell.setStatus(Cell.CellStatus.REVEALED);

        return cell;
    }

    public List<Cell> computeAllAdjacentCells(int x, int y) {
        List<Cell> adjacentCells = new ArrayList<Cell>();

        //Left column
        Cell possibleAdjacentCell = new Cell(x - 1, y + 1);
        validateCell(adjacentCells, possibleAdjacentCell);
        possibleAdjacentCell = new Cell(x - 1, y);
        validateCell(adjacentCells, possibleAdjacentCell);
        possibleAdjacentCell = new Cell(x - 1, y - 1);
        validateCell(adjacentCells, possibleAdjacentCell);

        //Same column
        possibleAdjacentCell = new Cell(x, y + 1);
        validateCell(adjacentCells, possibleAdjacentCell);
        possibleAdjacentCell = new Cell(x, y - 1);
        validateCell(adjacentCells, possibleAdjacentCell);

        //Right column
        possibleAdjacentCell = new Cell(x + 1, y + 1);
        validateCell(adjacentCells, possibleAdjacentCell);
        possibleAdjacentCell = new Cell(x + 1, y);
        validateCell(adjacentCells, possibleAdjacentCell);
        possibleAdjacentCell = new Cell(x + 1, y - 1);
        validateCell(adjacentCells, possibleAdjacentCell);

        return adjacentCells;
    }

    private void validateCell(List<Cell> adjacentCells, Cell autoMove) {
        if (board.isValidCell(autoMove)) {
            adjacentCells.add(autoMove);
        }
    }

    private int countAdjacentMines(int x, int y) {
        int adjacentMineCount = 0;
        List<Cell> adjacentCells = computeAllAdjacentCells(x, y);

        for (Cell adjectCell : adjacentCells) {
            if (isMine(adjectCell.getX(), adjectCell.getY())) {
                adjacentMineCount++;
            }
        }

        return adjacentMineCount;
    }

    private boolean isMine(int x, int y) {
        Cell cell = board.getCell(x, y);
        return cell.getDisplayValue().equals("M");
    }

    public boolean swept() {
        return numRevealed + numMines == gridSize * gridSize;
    }

    public Board getBoard() {
        return board;
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }


}
