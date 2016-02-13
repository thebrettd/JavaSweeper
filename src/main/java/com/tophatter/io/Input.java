package com.tophatter.io;

import com.tophatter.Board;
import com.tophatter.Cell;

import java.util.Scanner;

/**
 * Created by brett on 2/11/16.
 */
public class Input {

    public static int getMenuChoice(Scanner scanner) {
        int choice = -1;
        while (!(choice == 1 || choice == 2)) {
            String choiceInput = scanner.nextLine();
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Please enter 1 or 2");
            }
            if (!(choice == 1 || choice == 2)) {
                System.out.println(String.format("Invalid entry detected (%s), please enter 1 or 2", choice));
            }
        }
        return choice;
    }

    public static int queryUserForInt(Scanner scanner, String prompt) {
        int input = -1;
        while (input == -1) {
            System.out.println(prompt);
            String gridInput = scanner.nextLine();
            try {
                input = Integer.parseInt(gridInput);
            } catch (NumberFormatException e) {
                System.out.println("Cannot parse integer from input: " + gridInput);
            }
        }
        return input;
    }

    public static Cell getCellToClick(Scanner scanner, Board board) {

        int xValue = getValidIntInput(scanner, board, 'x');
        int yValue = getValidIntInput(scanner, board, 'y');

        return new Cell(xValue, yValue);
    }

    private static int getValidIntInput(Scanner scanner, Board board, char valueName) {
        int value = -1;
        boolean validValue = false;
        while (!validValue) {
            System.out.println(moveInputPrompt(valueName, board));
            String yInput = scanner.nextLine();
            try {
                value = Integer.parseInt(yInput);

                if (board.isValidCell(value)) {
                    validValue = true;
                } else {
                    System.out.println(invalidMoveWarning(value));
                }
            } catch (NumberFormatException e) {
                System.out.println(moveInputPrompt(valueName, board));
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
