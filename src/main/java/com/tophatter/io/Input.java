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

        int xValue = getIntInput(scanner, board, 'x');
        int yValue = getIntInput(scanner, board, 'y');

        return new Move(xValue, yValue);
    }

    private static int getIntInput(Scanner scanner, Board board, char valueName) {
        int value = -1;
        boolean validValue = false;
        while(!validValue){
            System.out.println(moveInputPrompt(valueName,board));
            String yInput = scanner.nextLine();
            try {
                value = Integer.parseInt(yInput);

                if (board.isValidMove(value)){
                    validValue = true;
                }else{
                    System.out.println(invalidMoveWarning(value));
                }
            }catch (NumberFormatException e){
                System.out.println(moveInputPrompt(valueName,board));
            }
        }
        return value;
    }

    private static String moveInputPrompt(char input, Board board) {
        return String.format("Enter %s value (Between 0 inclusive and %s exclusive)", input, board.getGridSize());
    }

    private static String invalidMoveWarning(int input) {
        return String.format("Invalid move entered! (%s)", input);
    }

}
