package com.tophatter;


import com.tophatter.solver.BrettSolver;
import com.tophatter.solver.RandomSolver;
import org.apache.commons.lang3.time.StopWatch;


/**
 * Created by brett on 2/10/16.
 */
public class MineSweeperSolver {

    public static final double simulations = 1000.0;

    public static void start() {

        int winCount = 0;

        StopWatch s = new StopWatch();
        s.start();
        for (int i=0;i<simulations;i++){
            Minesweeper game = new Minesweeper(10,10);
            if (new BrettSolver(game).solve()){
                winCount++;
            }
        }
        s.stop();

        System.out.println(String.format("Solving %s games took %s milliseconds", simulations, s.getTime()));
        System.out.println(String.format("Solved %s games", winCount));
        System.out.println(String.format("Win percentage: %s", (winCount/simulations) * 100));
    }


}
