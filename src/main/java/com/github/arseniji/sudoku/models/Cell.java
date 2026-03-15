package com.github.arseniji.sudoku.models;

public class Cell {
    private final int row;
    private final int col;
    private int value;
    private final boolean isGen;
    public Cell(int row,int col,int value,boolean isGen){
        this.row = row;
        this.col = col;
        this.value = value;
        this.isGen = isGen;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public boolean isGen() {
        return isGen;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
