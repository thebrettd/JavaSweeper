package com.tophatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by brett on 2/10/16.
 */
public class Minesweeper {

    private Board board;
    private GameStatus status = GameStatus.IN_PROGRESS;

    public Minesweeper(Board board) {
        this.board = board;
    }

    public static void start(Scanner scanner) {
        Minesweeper game = collectInputParameters(scanner);
        game.print();

        run(scanner, game);

    }

    private static void run(Scanner scanner, Minesweeper game) {
        while(game.getStatus().equals(GameStatus.IN_PROGRESS)){
            Move move = Move.getNextMove(scanner, game.getBoard());
            game.applyMove(move);

            switch(game.getStatus()){
                case LOSS:
                    System.out.println("Sorry, you lost!");
                    break;
                case VICTORY:
                    System.out.println("Congrats, you win!");
                    break;
            }
            game.print();
        }
    }

    public void print() {
        getBoard().print();
    }

    private void applyMove(Move move) {
        //Reveal the cell
        Cell clickedCell = board.revealCell(move.getX(), move.getY());

        //Update game status
        if (clickedCell.getValue().equals("M")){
            this.status = GameStatus.LOSS;
        }else if (board.swept()){
            this.status = GameStatus.VICTORY;
        }else{
            System.out.println("Phew, not a bomb!");

            int adjacentBombs = updateCellValue(move, clickedCell);

            if (adjacentBombs == 0){
                System.out.println("Clicked on a square with no adjacent bombs, automatically clicking all adjacent squares.");
                clickAllAdjacentCells(move);

                //Check for win conditions after autosweep
                if (board.swept()){
                    this.status = GameStatus.VICTORY;
                }
            }

        }
    }

    private int updateCellValue(Move move, Cell result) {
        int adjacentBombs = countAdjacentBombs(move.getX(), move.getY());
        result.setValue(Integer.toString(adjacentBombs));
        return adjacentBombs;
    }

    private void clickAllAdjacentCells(Move move) {
        for (Move autoMove: computeAllAdjacentCells(move)){
            Cell autoCell = board.getCell(autoMove.getX(), autoMove.getY());
            autoCell.setValue("0");

            if (autoCell.getStatus().equals(CellStatus.HIDDEN)){
                board.revealCell(autoMove.getX(), autoMove.getY()); //We know its a 0 or we wouldnt be here

                int adjacentBombs = countAdjacentBombs(autoMove.getX(), autoMove.getY());


                if (adjacentBombs == 0){
                    clickAllAdjacentCells(autoMove);
                }

            }

        }
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

    private static Minesweeper collectInputParameters(Scanner scanner) {

        int gridSize = -1;
        System.out.println("Enter size of the grid: ");
        String gridInput = scanner.nextLine();
        while(gridSize == -1) {
            try {
                gridSize = Integer.parseInt(gridInput);
            } catch (NumberFormatException e) {
                System.out.println("Cannot parse integer from input: " + gridInput);
            }
        }

        int numBombs = -1;
        System.out.println("Enter number of bombs: ");
        String bombInput = scanner.nextLine();
        while (numBombs == -1){
            try {
                numBombs = Integer.parseInt(bombInput);
            } catch (NumberFormatException e) {
                System.out.println("Cannot parse integer from input: " + bombInput);
            }
        }

        return new Minesweeper(new Board(gridSize, numBombs));
    }


    public Board getBoard() {
        return board;
    }

    public GameStatus getStatus() {
        return status;
    }

}
