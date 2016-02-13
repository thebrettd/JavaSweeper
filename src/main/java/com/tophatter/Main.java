package com.tophatter;

import java.util.Scanner;

import static com.tophatter.io.Input.getMenuChoice;

public class Main {

    public static void main(String[] args) {

        System.out.println("Press 1 to play a game, or 2 to run the simulator:");
        Scanner scanner = new Scanner(System.in);

        int choice = getMenuChoice(scanner);

        switch (choice) {
            case 1:
                Minesweeper.start(scanner);
                break;
            case 2:
                MineSweeperSolver.start();
                break;
        }
    }


}
