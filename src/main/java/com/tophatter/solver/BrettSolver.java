package com.tophatter.solver;

import com.tophatter.Cell;
import com.tophatter.Minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by brett on 2/11/16.
 */
public class BrettSolver extends AbstractSolver {

    private final static Logger LOGGER = Logger.getLogger(BrettSolver.class.getName());

    //Array marking all the known bombs. Skip these during 4.
    private boolean isBomb[][];

    //Array marking squares where all bombs are known. Skip these during 2.
    private boolean isDefused[][];

    public BrettSolver(Minesweeper game) {
        super(game);

        isBomb = new boolean[game.getBoard().getGridSize()][game.getBoard().getGridSize()];
        isDefused = new boolean[game.getBoard().getGridSize()][game.getBoard().getGridSize()];
    }

    /***
     * 1.) Start by clicking on a random square (check to make sure it isnt a bomb or already
     *
     * 2.) Find easy patterns and mark the bombs.
     * Easy pattern - A numbered square who is touching exactly as many HIDDEN squares as its number.
     *
     * 3.) Click all safe squares around a defused square.
     *
     */
    @Override
    public void doSolve() {
        Cell randomCell = getRandomCell();

        if(notABombOrDefused(randomCell)){
            game.clickCell(randomCell);
        }

        if (game.getStatus().equals(Minesweeper.GameStatus.LOSS)){
            //LOGGER.log(Level.FINEST,"Solver randomly clicked a bomb :(");
        }else{
            findEasyPatterns();
            clearDefused();
        }

    }

    //Search through the board again for cells that now have all their bombs marked. Click all around it (but not on the bombs!)
    private void clearDefused() {
        for (int y=0;y<game.getBoard().getGridSize()-1;y++){
            for(int x=0;x<game.getBoard().getGridSize()-1;x++) {

                Cell currentCell = game.getBoard().getCell(x, y);

                if (currentCell.getStatus().equals(Cell.CellStatus.REVEALED)) {
                    int adjacentBombCount = getAdjacentBombCount(currentCell);
                    int seenBombs = 0;
                    List<Cell> adjacentCells = game.computeAllAdjacentCells(x, y);
                    for (Cell cell : adjacentCells) {
                        if (isBomb[cell.getX()][cell.getY()]) {
                            seenBombs++;
                        }
                    }
                    if (seenBombs == adjacentBombCount) {
                        for (Cell cell : adjacentCells) {
                            if (!isBomb[cell.getX()][cell.getY()]) {
                                game.clickCell(cell);
                            }
                        }
                        isDefused[x][y] = true;
                    }
                }
            }
        }
    }

    private int getAdjacentBombCount(Cell currentCell) {
        return Integer.parseInt(currentCell.getDisplayValue());
    }

    private boolean notABombOrDefused(Cell randomCell) {
        return !isBomb[randomCell.getX()][randomCell.getY()] && !isDefused[randomCell.getX()][randomCell.getY()];
    }

    /***
     * Look at all the revealed squares.
     * If they are touching exactly as many hidden squares as their adjacentBombCount, all the hidden squares are bombs.
     */
    private void findEasyPatterns() {
        for (int y=0;y<game.getBoard().getGridSize()-1;y++){
            for(int x=0;x<game.getBoard().getGridSize()-1;x++){

                Cell currentCell = game.getBoard().getCell(x, y);
                if (currentCell.getStatus().equals(Cell.CellStatus.REVEALED)){

                    int adjacentBombCount = getAdjacentBombCount(currentCell);
                    int hiddenAdjacentsCount = 0;

                    List<Cell> adjacentCells = game.computeAllAdjacentCells(x,y);
                    List<Cell> hiddenAdjacentsList = new ArrayList<Cell>();
                    for (Cell adjacentCell : adjacentCells){
                        if (game.getBoard().getCell(adjacentCell.getX(), adjacentCell.getY()).getStatus().equals(Cell.CellStatus.HIDDEN)){
                            hiddenAdjacentsCount++;
                            hiddenAdjacentsList.add(adjacentCell);
                        }
                    }

                    if (hiddenAdjacentsCount == adjacentBombCount){
                        //LOGGER.log(Level.FINEST,String.format("Defused all hidden squares touching (%s,%s)", x,y));
                        for(Cell hiddenBomb: hiddenAdjacentsList){
                            isBomb[hiddenBomb.getX()][hiddenBomb.getY()] = true;
                            isDefused[x][y] = true;
                        }
                    }
                }

            }
        }
    }


}
