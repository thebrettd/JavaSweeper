package com.tophatter.solver;

import com.tophatter.Minesweeper;

import java.util.Random;

/**
 * Created by brett on 2/11/16.
 */
public abstract class AbstractSolver {

    protected static final Random generator = new Random();

    protected Minesweeper game;

    public AbstractSolver(Minesweeper game){
        this.game = game;
    }

    public abstract void doSolve();

    public boolean solve(){
        while(game.getStatus() == Minesweeper.GameStatus.IN_PROGRESS){
            doSolve();
        }

        return game.getStatus().equals(Minesweeper.GameStatus.VICTORY);
    }



}
