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
