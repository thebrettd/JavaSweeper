package com.tophatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static com.tophatter.io.Input.queryUserForInt;

/**
 * Created by brett on 2/10/16.
 */
public class Minesweeper {

    public enum GameStatus {
        IN_PROGRESS,LOSS,VICTORY
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
        computeAdjacentBombs();
    }

    private void plantMines() {
        int minesPlanted = 0;

        Random generator = new Random();
        while(minesPlanted < numMines){

            boolean plantingMine = true;
            while (plantingMine) {
                int randomX = generator.nextInt(board.getGridSize());
                int randomY = generator.nextInt(board.getGridSize());
                if(!(board.getCell(randomX,randomY).getValue().equals("M"))){
                    board.putCell(randomX,randomY, new Cell("M"));
                    minesPlanted++;
                    plantingMine = false;
                }
            }
        }
    }

    private void computeAdjacentBombs() {
        for(int y=0;y<gridSize;y++){
            for (int x=0;x<gridSize;x++){
                Cell cell = board.getCell(x,y);
                if (!cell.getValue().equals("M")){
                    cell.setValue("" + countAdjacentBombs(x,y));
                }
            }
        }
    }

    public static void start(Scanner scanner) {
        int gridSize = queryUserForInt(scanner, "Enter size of the grid: ");
        int numBombs = queryUserForInt(scanner, "Enter number of bombs: ");

        Minesweeper game = new Minesweeper(gridSize, numBombs);
        game.run(scanner);
    }

    private void run(Scanner scanner) {
        print();

        while(status.equals(GameStatus.IN_PROGRESS)){
            Move move = Move.getNextMove(scanner, getBoard());
            applyMove(move);

            switch(getStatus()){
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

    public Cell applyMove(Move move) {
        //Reveal the cell
        Cell clickedCell = revealCell(move.getX(), move.getY());

        //Update game status
        if (clickedCell.getValue().equals("M")){
            this.status = GameStatus.LOSS;
        }else if (swept()){
            this.status = GameStatus.VICTORY;
        }else{
            System.out.println("Phew, not a bomb!");

            if (clickedCell.getValue().equals("0")){
                System.out.println("Clicked on a square with no adjacent bombs, automatically clicking all adjacent squares.");
                clickAllAdjacentCells(move);

                //Check for win conditions after autosweep
                if (swept()){
                    this.status = GameStatus.VICTORY;
                }
            }
        }
        return clickedCell;
    }

    private void clickAllAdjacentCells(Move move) {
        for (Move autoMove: computeAllAdjacentCells(move)){
            Cell autoCell = board.getCell(autoMove.getX(), autoMove.getY());

            if (autoCell.getStatus().equals(Cell.CellStatus.HIDDEN)){
                revealCell(autoMove.getX(), autoMove.getY()); //We know its a 0 or we wouldnt be here
                if (autoCell.getValue().equals("0")){
                    clickAllAdjacentCells(autoMove);
                }
            }
        }
    }

    public Cell revealCell(int x, int y){
        Cell cell = board.getCell(x,y);
        if (cell.getStatus() != Cell.CellStatus.REVEALED){
            numRevealed++;
        }
        cell.setStatus(Cell.CellStatus.REVEALED);

        return cell;
    }

    public List<Move> computeAllAdjacentCells(Move move) {
        List<Move> adjacentCells = new ArrayList<Move>();

        Move autoMove = new Move(move.getX()-1, move.getY()+1);
        validateMove(adjacentCells, autoMove);

        autoMove = new Move(move.getX()-1, move.getY());
        validateMove(adjacentCells, autoMove);
        autoMove = new Move(move.getX()-1, move.getY()-1);
        validateMove(adjacentCells, autoMove);

        autoMove = new Move(move.getX(), move.getY()+1);
        validateMove(adjacentCells, autoMove);
        autoMove = new Move(move.getX(), move.getY()-1);
        validateMove(adjacentCells, autoMove);

        autoMove = new Move(move.getX()+1, move.getY()+1);
        validateMove(adjacentCells, autoMove);
        autoMove = new Move(move.getX()+1, move.getY());
        validateMove(adjacentCells, autoMove);
        autoMove = new Move(move.getX()+1, move.getY()-1);
        validateMove(adjacentCells, autoMove);

        return adjacentCells;
    }

    private void validateMove(List<Move> adjacentCells, Move autoMove) {
        if (Move.isValidMove(autoMove, board)){
            adjacentCells.add(autoMove);
        }
    }

    private int countAdjacentBombs(int x, int y) {
        int adjacentBombCount = 0;

        if(isBomb(x-1,y)){
            adjacentBombCount++;
        }
        if(isBomb(x-1,y-1)){
            adjacentBombCount++;
        }
        if(isBomb(x-1,y+1)){
            adjacentBombCount++;
        }

        if(isBomb(x,y-1)){
            adjacentBombCount++;
        }
        if(isBomb(x,y+1)){
            adjacentBombCount++;
        }


        if(isBomb(x+1,y)){
            adjacentBombCount++;
        }
        if(isBomb(x+1,y+1)){
            adjacentBombCount++;
        }

        if(isBomb(x+1,y-1)){
            adjacentBombCount++;
        }

        return adjacentBombCount;
    }

    private boolean isBomb(int x, int y) {
        //Check for array index out of bounds..
        if ((x == -1) || (y == -1) || (x > board.getGridSize()-1) || (y > board.getGridSize()-1) ){
            return false;
        }

        Cell cell = board.getCell(x,y);
        return cell.getValue().equals("M");
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
