package com.tophatter;


/**
 * Created by brett on 2/10/16.
 */
public class Cell {

    public enum CellStatus {
        HIDDEN,REVEALED
    }

    private String displayValue;

    private CellStatus status = CellStatus.HIDDEN;

    public Cell(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    //todo: precompute display status, dont run through this switch every time
    public String print(){
        return status == CellStatus.HIDDEN ? "◼" : displayValue;
    }

    public CellStatus getStatus() {
        return status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
}
