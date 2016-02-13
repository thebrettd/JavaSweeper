package com.tophatter.io;

import com.tophatter.Board;
import com.tophatter.Cell;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by brett on 2/12/16.
 */
public class PrettyPrint {

    public static void print(Board board){

            //How wide we want each cell, based on the maximum string length of the indices
            int desiredWidth = String.valueOf(board.getGridSize() - 1).length();

            printXIndices(board, desiredWidth);

            for (int y = board.getGridSize() - 1; y > -1; y--) {
                int yIndexWidth = String.valueOf(y).length();
                System.out.print(String.format("%s%s|", y, StringUtils.repeat(" ", desiredWidth - yIndexWidth)));
                for (int x = 0; x < board.getGridSize(); x++) {
                    Cell currCell = board.getCell(x,y);
                    System.out.print(String.format("%s%s|", currCell.print(), StringUtils.repeat(" ", desiredWidth - 1)));
                }
                System.out.println(String.format("%s", y));
            }

            printXIndices(board, desiredWidth);

    }

    private static void printXIndices(Board board, int desiredWidth) {

        //Padding for the space above Y indices
        System.out.print(String.format("%s|", StringUtils.repeat(" ", desiredWidth)));

        //Print the x indices and padding
        for (int x = 0; x < board.getGridSize(); x++) {
            int currentIndexWidth = String.valueOf(x).length();
            System.out.print(String.format("%s%s|", x, StringUtils.repeat(" ", desiredWidth - currentIndexWidth)));
        }
        System.out.println();
    }


}
