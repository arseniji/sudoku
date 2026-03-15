package com.github.arseniji.sudoku.enums;

public enum Difficulty {
    EASY(60),
    MEDIUM(40),
    HARD(20);
    private final int value;
    Difficulty(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
