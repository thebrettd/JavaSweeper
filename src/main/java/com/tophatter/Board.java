package com.tophatter;

import java.util.Random;

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
        for (int y=0;y<gridSize;y++){
            for (int x=0;x<gridSize;x++){
                myBoard[y][x] = new Cell(".");
            }
        }
    }

    public int getGridSize() {
        return gridSize;
    }

    //Todo: padding for gridSize > 10
    public void print() {

        printX();

        for (int y=gridSize-1;y>-1;y--){
            System.out.print("" + y + "|");
            for (int x=0;x<gridSize;x++){
                Cell currCell = myBoard[x][y];
                System.out.print(currCell.print() + "|");
            }
            System.out.print("" + y);
            System.out.println();
        }

        printX();
    }

    private void printX() {
        System.out.print(" -");
        for (int i=0;i<gridSize;i++){
            System.out.print("" + i + "-");
        }
        System.out.println();
    }

    public Cell getCell(int x, int y) {
        return myBoard[x][y];
    }

    public void putCell(int x, int y, Cell m) {
        myBoard[x][y] = m;
    }

    public boolean isValidMove(Move move) {
        return move.getX() > -1 && move.getX() < gridSize && move.getY() > -1 && move.getY() < gridSize;
    }
}
