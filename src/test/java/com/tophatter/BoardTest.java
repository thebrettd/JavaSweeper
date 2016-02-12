package com.tophatter;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by brett on 2/10/16.
 */
public class BoardTest {

    @Test
    public void testGetGridSize() throws Exception {
        Board board = new Board(5);
        assertTrue(board.getGridSize() == 5);
    }

    @Test
    public void testSwept() throws Exception {

        Minesweeper game = new Minesweeper(1, 0);
        assertFalse(game.swept());
        game.revealCell(0, 0);
        assertTrue(game.swept());

        game = new Minesweeper(2, 0);
        assertFalse(game.swept());
        game.revealCell(0, 0);
        game.revealCell(0, 1);
        game.revealCell(1, 0);
        game.revealCell(1, 1);
        assertTrue(game.swept());
    }

    @Test
    public void testPrint() throws Exception {
        Board board = new Board(5);
        board.print();
    }

    @Test
    public void testGenerator() throws Exception {
        Random generator = new Random();
        //Should see 0-4 in here...
        for (int i = 0; i < 50; i++) {
            System.out.println(generator.nextInt(5));
        }
    }
}