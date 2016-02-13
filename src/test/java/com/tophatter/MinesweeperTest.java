package com.tophatter;

import com.tophatter.io.PrettyPrint;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by brett on 2/11/16.
 */
public class MinesweeperTest {

    @Test
    public void testAllAdjacentCells() throws Exception {
        Minesweeper game = new Minesweeper(5, 1);
        Cell cell = new Cell(0, 0);
        List<Cell> cells = game.computeAllAdjacentCells(cell.getX(), cell.getY());
        assertTrue(cells.size() == 3);

        PrettyPrint.print(game.getBoard());

        cell = new Cell(2, 2);
        cells = game.computeAllAdjacentCells(cell.getX(), cell.getY());
        assertTrue(cells.size() == 8);

        game = new Minesweeper(10, 1);
        cell = new Cell(0, 8);
        cells = game.computeAllAdjacentCells(cell.getX(), cell.getY());
        assertTrue(cells.size() == 5);
    }
}