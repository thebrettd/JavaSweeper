package com.tophatter.solver;

import com.tophatter.Board;
import com.tophatter.Cell;
import com.tophatter.Minesweeper;

import java.util.Random;

/**
 * Created by brett on 2/11/16.
 */
public abstract class AbstractSolver {

    protected static final Random generator = new Random();

    protected Minesweeper game;
    protected Board board;

    public AbstractSolver(Minesweeper game) {
        this.game = game;

        board = game.getBoard();
    }

    public abstract void doSolve();

    public boolean solve() {
        while (game.getStatus() == Minesweeper.GameStatus.IN_PROGRESS) {
            doSolve();
        }

        return game.getStatus().equals(Minesweeper.GameStatus.VICTORY);
    }


    protected Cell getRandomCell() {
        return new Cell(generator.nextInt(board.getGridSize()), generator.nextInt(game.getBoard().getGridSize()));
    }
}
