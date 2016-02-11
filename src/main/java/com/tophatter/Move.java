package com.tophatter;

import java.util.Scanner;

/**
 * Created by brett on 2/10/16.
 */
public class Move {

    private int x;
    private int y;

    public Move(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static Move getNextMove(Scanner scanner, Board board) {
        int xValue = -1;
        int yValue = -1;

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

        return new Move(xValue, yValue);
    }

    private static boolean isValidMove(int value, Board board) {
        return value > -1 && value < board.getGridSize();
    }

    public static boolean isValidMove(Move move, Board board) {
        return move.getX() > -1 && move.getX() < board.getGridSize() && move.getY() > -1 && move.getY() < board.getGridSize();
    }

    private static String moveInputPrompt(char input, Board board) {
        return String.format("Enter %s value (Between 0 inclusive and %s exclusive)", input, board.getGridSize());
    }

    private static String invalidMoveWarning(int input) {
        return String.format("Invalid move entered! (%s)", input);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
