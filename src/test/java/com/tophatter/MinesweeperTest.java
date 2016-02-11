package com.tophatter;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by brett on 2/11/16.
 */
public class MinesweeperTest {

    @Test
    public void testAllAdjacentCells() throws Exception {
        Minesweeper game = new Minesweeper(5,1);
        Move move = new Move(0,0);
        List<Move> moves = game.computeAllAdjacentCells(move);
        assertTrue(moves.size() == 3);

        game.print();

        move = new Move(2,2);
        moves = game.computeAllAdjacentCells(move);
        assertTrue(moves.size() == 8);

        game = new Minesweeper(10,1);
        move = new Move(0,8);
        moves = game.computeAllAdjacentCells(move);
        assertTrue(moves.size() == 5);


    }
}