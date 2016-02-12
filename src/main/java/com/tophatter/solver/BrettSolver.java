package com.tophatter.solver;

import com.tophatter.Board;
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
    private final int gridSize;
    private final Board board;

    //Array marking all the known mines. Skip these during 4.
    private boolean isMine[][];

    //Array marking squares where all mines are known. Skip these during 2.
    private boolean isDefused[][];

    public BrettSolver(Minesweeper game) {
        super(game);

        board = game.getBoard();
        gridSize = board.getGridSize();

        isMine = new boolean[gridSize][gridSize];
        isDefused = new boolean[gridSize][gridSize];
    }

    /***
     * 1.) Start by clicking on a random square (check to make sure it isnt a mine or already defused)
     * <p/>
     * 2.) Find surround cells - A REVEALED cell that is touching exactly as many HIDDEN cells as mines cells.
     *     This implies all the HIDDEN cells are actually mines.
     *
     * <p/>
     * 3.) Click all safe squares around a defused square.
     */
    @Override
    public void doSolve() {
        Cell randomCell = getRandomCell();

        if (notMineOrDefused(randomCell)) {
            game.clickCell(randomCell);
        }

        if (game.getStatus().equals(Minesweeper.GameStatus.LOSS)) {
            //LOGGER.log(Level.FINEST,"Solver randomly clicked a mine :(");
        } else {
            findSurroundedCells();
            clearDefusedCells();
        }

    }

    //Search through the board again for cells that now have all their mines marked. Click all around it (but not on the bombs!)
    private void clearDefusedCells() {
        for (int y = 0; y < gridSize - 1; y++) {
            for (int x = 0; x < gridSize - 1; x++) {

                Cell currentCell = board.getCell(x, y);

                if (currentCell.getStatus().equals(Cell.CellStatus.REVEALED)) {
                    int adjacentMineCount = getAdjacentMineCount(currentCell);
                    int minesSeen = 0;
                    List<Cell> adjacentCells = game.computeAllAdjacentCells(x, y);
                    for (Cell cell : adjacentCells) {
                        if (isMine[cell.getX()][cell.getY()]) {
                            minesSeen++;
                        }
                    }
                    if (minesSeen == adjacentMineCount) {
                        for (Cell cell : adjacentCells) {
                            if (!isMine[cell.getX()][cell.getY()]) {
                                game.clickCell(cell);
                            }
                        }
                        isDefused[x][y] = true;
                    }
                }
            }
        }
    }

    private int getAdjacentMineCount(Cell currentCell) {
        return Integer.parseInt(currentCell.getDisplayValue());
    }

    private boolean notMineOrDefused(Cell randomCell) {
        return !isMine[randomCell.getX()][randomCell.getY()] && !isDefused[randomCell.getX()][randomCell.getY()];
    }

    /***
     * Look at all the revealed squares.
     * If they are touching exactly as many hidden squares as their adjacentBombCount, all the hidden squares are bombs.
     */
    private void findSurroundedCells() {
        for (int y = 0; y < gridSize - 1; y++) {
            for (int x = 0; x < gridSize - 1; x++) {

                Cell currentCell = board.getCell(x, y);
                if (currentCell.getStatus().equals(Cell.CellStatus.REVEALED)) {

                    int adjacentMineCount = getAdjacentMineCount(currentCell);
                    int hiddenAdjacentsCount = 0;

                    List<Cell> adjacentCells = game.computeAllAdjacentCells(x, y);
                    List<Cell> hiddenAdjacentsList = new ArrayList<Cell>();
                    for (Cell adjacentCell : adjacentCells) {
                        if (board.getCell(adjacentCell.getX(), adjacentCell.getY()).getStatus().equals(Cell.CellStatus.HIDDEN)) {
                            hiddenAdjacentsCount++;
                            hiddenAdjacentsList.add(adjacentCell);
                        }
                    }

                    if (hiddenAdjacentsCount == adjacentMineCount) {
                        //LOGGER.log(Level.FINEST,String.format("Defused all hidden squares touching (%s,%s)", x,y));
                        for (Cell hiddenMine : hiddenAdjacentsList) {
                            isMine[hiddenMine.getX()][hiddenMine.getY()] = true;
                            isDefused[x][y] = true;
                        }
                    }
                }

            }
        }
    }


}
