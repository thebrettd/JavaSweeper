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

    public void print() {
        //How wide we want each cell, based on the maximum string length of the indices
        int desiredWidth = String.valueOf(gridSize - 1).length();

        printXIndices(desiredWidth);

        for (int y = gridSize - 1; y > -1; y--) {
            int yIndexWidth = String.valueOf(y).length();
            System.out.print(String.format("%s%s|", y, StringUtils.repeat(" ", desiredWidth - yIndexWidth)));
            for (int x = 0; x < gridSize; x++) {
                Cell currCell = myBoard[x][y];
                System.out.print(String.format("%s%s|", currCell.print(), StringUtils.repeat(" ", desiredWidth - 1)));
            }
            System.out.println(String.format("%s", y));
        }

        printXIndices(desiredWidth);
    }

    private void printXIndices(int desiredWidth) {

        //Padding for the space above Y indices
        System.out.print(String.format("%s|", StringUtils.repeat(" ", desiredWidth)));

        //Print the x indices and padding
        for (int x = 0; x < gridSize; x++) {
            int currentIndexWidth = String.valueOf(x).length();
            System.out.print(String.format("%s%s|", x, StringUtils.repeat(" ", desiredWidth - currentIndexWidth)));
        }
        System.out.println();
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
