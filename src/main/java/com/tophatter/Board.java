package com.tophatter;

import java.util.Random;

/**
 * Created by brett on 2/10/16.
 */
public class Board {

    private int gridSize;
    private int numMines = 0;

    private Cell[][] myBoard;
    private int numRevealed = 0;
    private int totalCells;

    public Board(int gridSize, int minesToPlant) {
        this.gridSize = gridSize;
        this.totalCells = gridSize * gridSize;

        initializeEmptyBoard();
        plantMines(gridSize, minesToPlant);


    }

    private void plantMines(int gridSize, int minesToPlant) {
        Random generator = new Random();
        while(numMines < minesToPlant){

            boolean plantingMine = true;
            while (plantingMine) {
                int randomX = generator.nextInt(gridSize);
                int randomY = generator.nextInt(gridSize);
                if(!(myBoard[randomX][randomY].getValue().equals("M"))){
                    myBoard[randomX][randomY] = new Cell("M");
                    numMines++;
                    plantingMine = false;
                }
            }
        }
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

    public Cell revealCell(int x, int y){
        Cell cell = myBoard[x][y];
        if (cell.getStatus() != CellStatus.REVEALED){
            numRevealed++;
        }
        cell.revealed();

        return cell;
    }

    public int getNumRevealed() {
        return numRevealed;
    }

    public boolean swept() {
        return numRevealed + numMines == totalCells;
    }

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
}
