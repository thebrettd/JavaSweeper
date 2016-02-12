package com.tophatter.solver;

import com.tophatter.Cell;
import com.tophatter.Minesweeper;
import com.tophatter.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by brett on 2/11/16.
 */
public class BrettSolver extends AbstractSolver {

    private final static Logger LOGGER = Logger.getLogger(BrettSolver.class.getName());

    //Array marking all the known bombs. Skip these during 3.
    private boolean isBomb[][];

    //Array marking squares where all bombs are known. Skip these during 2.
    private boolean isDefused[][];

    public BrettSolver(Minesweeper game) {
        super(game);

        isBomb = new boolean[game.getBoard().getGridSize()][game.getBoard().getGridSize()];
        isDefused = new boolean[game.getBoard().getGridSize()][game.getBoard().getGridSize()];
    }

    /***
     * 1.) Start by clicking on a random square.
     * 2.) Find easy patterns and mark the bombs.
     *
     * Easy pattern - A numbered square who is touching exactly as many HIDDEN squares as its number.
     *
     * 3.) Click on a random non-bomb square.
     *
     */
    @Override
    public void doSolve() {
        Move randomMove = getRandomMove();

        if(notABombOrDefused(randomMove)){
            game.applyMove(randomMove);
        }

        if (game.getStatus().equals(Minesweeper.GameStatus.LOSS)){
            //LOGGER.log(Level.FINEST,"Solver randomly clicked a bomb :(");
        }else{
            findPatterns();
        }

    }

    private boolean notABombOrDefused(Move randomMove) {
        return !isBomb[randomMove.getX()][randomMove.getY()] && !isDefused[randomMove.getX()][randomMove.getY()];
    }

    /***
     * Look at all the revealed squares.
     * If they are touching exactly as many hidden squares as their adjacentBombCount, all the hidden squares are bombs.
     */
    private void findPatterns() {
        for (int y=0;y<game.getBoard().getGridSize()-1;y++){
            for(int x=0;x<game.getBoard().getGridSize()-1;x++){

                Cell currentCell = game.getBoard().getCell(x, y);
                if (currentCell.getStatus().equals(Cell.CellStatus.REVEALED)){

                    int adjacentBombCount = Integer.parseInt(currentCell.getDisplayValue());
                    int hiddenAdjacentsCount = 0;

                    List<Move> adjacentCells = game.computeAllAdjacentCells(x,y);
                    List<Move> hiddenAdjacentsList = new ArrayList<Move>();
                    for (Move adjacentCell : adjacentCells){
                        if (game.getBoard().getCell(adjacentCell.getX(), adjacentCell.getY()).getStatus().equals(Cell.CellStatus.HIDDEN)){
                            hiddenAdjacentsCount++;
                            hiddenAdjacentsList.add(adjacentCell);
                        }
                    }

                    if (hiddenAdjacentsCount == adjacentBombCount){
                        //LOGGER.log(Level.FINEST,String.format("Defused all hidden squares touching (%s,%s)", x,y));
                        for(Move hiddenBomb: hiddenAdjacentsList){
                            isBomb[hiddenBomb.getX()][hiddenBomb.getY()] = true;
                            isDefused[x][y] = true;
                        }
                    }
                }

            }
        }
    }


}
