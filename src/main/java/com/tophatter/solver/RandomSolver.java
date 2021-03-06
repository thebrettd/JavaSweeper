package com.tophatter.solver;

import com.tophatter.Cell;
import com.tophatter.Minesweeper;

/**
 * Created by brett on 2/11/16.
 */
public class RandomSolver extends AbstractSolver {

    public RandomSolver(Minesweeper game) {
        super(game);
    }

    @Override
    public void doSolve() {
        Cell randomCell = getRandomCell();
        game.clickCell(randomCell);
    }

}
