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
        Board board = new Board(5,1);
        Minesweeper game = new Minesweeper(board);
        Move move = new Move(0,0);
        List<Move> moves = game.computeAllAdjacentCells(move);
        assertTrue(moves.size() == 3);

        game.print();

        move = new Move(2,2);
        moves = game.computeAllAdjacentCells(move);
        assertTrue(moves.size() == 8);

        board = new Board(10,1);
        game = new Minesweeper(board);
        move = new Move(0,8);
        moves = game.computeAllAdjacentCells(move);
        assertTrue(moves.size() == 5);


    }
}