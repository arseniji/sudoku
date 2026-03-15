package com.github.arseniji.sudoku.enums;

public enum Difficulty {
    Easy(60),
    Medium(40),
    Hard(20);
    private final int value;
    Difficulty(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
