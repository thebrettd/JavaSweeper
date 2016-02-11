package com.tophatter.io;

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

}
