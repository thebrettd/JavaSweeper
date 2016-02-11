package com.tophatter;


import org.apache.commons.lang3.time.StopWatch;

import java.util.Random;

/**
 * Created by brett on 2/10/16.
 */
public class MineSweeperSolver {



    public static void start() {

        int winCount = 0;

        StopWatch s = new StopWatch();
        s.start();
        for (int i=0;i<1000;i++){
            Minesweeper game = new Minesweeper(10,10);
            if (randomMoveSolver(game)){
                winCount++;
            }
        }
        s.stop();

        System.out.println(String.format("Solving 1000 games took %s milliseconds", s.getTime()));
        System.out.println(String.format("Solved %s games", winCount));

    }

    private static boolean randomMoveSolver(Minesweeper game) {

        Random generator = new Random();

        while(game.getStatus() == Minesweeper.GameStatus.IN_PROGRESS){
            Move m = new Move(generator.nextInt(game.getBoard().getGridSize()),generator.nextInt(game.getBoard().getGridSize()));
            game.applyMove(m);
        }

        return game.getStatus().equals(Minesweeper.GameStatus.VICTORY);
    }


}
