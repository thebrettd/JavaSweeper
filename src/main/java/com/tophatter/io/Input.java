package com.tophatter.io;

import com.tophatter.Board;
import com.tophatter.Move;

import java.util.Scanner;

/**
 * Created by brett on 2/11/16.
 */
public class Input {

    public static int queryUserForInt(Scanner scanner, String prompt) {
        int input = -1;
        System.out.println(prompt);
        String gridInput = scanner.nextLine();
        while(input == -1) {
            try {
                input = Integer.parseInt(gridInput);
            } catch (NumberFormatException e) {
                System.out.println("Cannot parse integer from input: " + gridInput);
            }
        }
        return input;
    }

    public static Move getNextMove(Scanner scanner, Board board) {
        int xValue = -1;
        int yValue = -1;

        xValue = getX(scanner, board, xValue);

        yValue = getY(scanner, board, yValue);

        return new Move(xValue, yValue);
    }

    private static int getY(Scanner scanner, Board board, int yValue) {
        boolean validY = false;
        while(!validY){
            System.out.println(moveInputPrompt('y',board));
            String yInput = scanner.nextLine();
            try {
                yValue = Integer.parseInt(yInput);

                if (isValidMove(yValue,board)){
                    validY = true;
                }else{
                    System.out.println(invalidMoveWarning(yValue));
                }
            }catch (NumberFormatException e){
                System.out.println(moveInputPrompt('y',board));
            }
        }
        return yValue;
    }

    private static int getX(Scanner scanner, Board board, int xValue) {
        boolean validX = false;
        while(!validX){
            System.out.println(moveInputPrompt('x', board));
            String xInput = scanner.nextLine();
            try {
                xValue = Integer.parseInt(xInput);

                if (isValidMove(xValue, board)){
                    validX = true;
                }else{
                    System.out.println(invalidMoveWarning(xValue));
                }
            }catch (NumberFormatException e){
                System.out.println(moveInputPrompt('x',board));
            }
        }
        return xValue;
    }

    private static boolean isValidMove(int value, Board board) {
        return value > -1 && value < board.getGridSize();
    }


    private static String moveInputPrompt(char input, Board board) {
        return String.format("Enter %s value (Between 0 inclusive and %s exclusive)", input, board.getGridSize());
    }

    private static String invalidMoveWarning(int input) {
        return String.format("Invalid move entered! (%s)", input);
    }

}
