package com.tophatter;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by brett on 2/10/16.
 */
public class Board {

    private int gridSize;

    private Cell[][] myBoard;

    public Board(int gridSize) {
        this.gridSize = gridSize;

        initializeEmptyBoard();
    }

    private void initializeEmptyBoard() {
        myBoard = new Cell[gridSize][gridSize];
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                myBoard[y][x] = new Cell(".");
            }
        }
    }

    public int getGridSize() {
        return gridSize;
    }




    public Cell getCell(int x, int y) {
        return myBoard[x][y];
    }

    public void putCell(int x, int y, Cell m) {
        myBoard[x][y] = m;
    }

    //Make sure these computed cell coordinates are actually on the board
    public boolean isValidCell(Cell cell) {
        return cell.getX() > -1 && cell.getX() < gridSize && cell.getY() > -1 && cell.getY() < gridSize;
    }

    //Make sure this computed value is within the range of the board
    public boolean isValidCell(int value) {
        return value > -1 && value < gridSize;
    }
}
