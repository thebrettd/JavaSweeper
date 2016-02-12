package com.tophatter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Press 1 to play a game, or 2 to run the simulator:");
        Scanner scanner = new Scanner(System.in);
        String choiceInput = scanner.nextLine();

        int choice = -1;
        while(choice == -1){
            try{
                choice = Integer.parseInt(choiceInput);
            }catch (NumberFormatException e){
                System.out.println("Please enter 1 or 2");
            }
        }

        switch(choice){
            case 1 : Minesweeper.start(scanner); break;
            case 2 : MineSweeperSolver.start(); break;
        }


    }
}
