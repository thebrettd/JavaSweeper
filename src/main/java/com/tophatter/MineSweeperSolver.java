package com.tophatter;


import com.tophatter.solver.RandomSolver;
import org.apache.commons.lang3.time.StopWatch;


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
            if (new RandomSolver(game).solve()){
                winCount++;
            }
        }
        s.stop();

        System.out.println(String.format("Solving 1000 games took %s milliseconds", s.getTime()));
        System.out.println(String.format("Solved %s games", winCount));
    }


}
