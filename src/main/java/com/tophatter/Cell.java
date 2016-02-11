package com.tophatter;


/**
 * Created by brett on 2/10/16.
 */
public class Cell {

    public enum CellStatus {
        HIDDEN,REVEALED
    }

    private String value;

    private CellStatus status = CellStatus.HIDDEN;

    public Cell(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //todo: precompute display status, dont run through this switch every time
    public String print(){
        String display;
        switch (status){
            case HIDDEN:
                display = "◼";
                break;
            case REVEALED:

                display = value;
                break;
            default:
                throw new RuntimeException("Unknown cell status");
        }
        return display;
    }


    public CellStatus getStatus() {
        return status;
    }

    public void revealed() {
        this.status = CellStatus.REVEALED;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
