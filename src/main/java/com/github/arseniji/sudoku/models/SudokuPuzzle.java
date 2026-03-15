package com.github.arseniji.sudoku.models;

import com.github.arseniji.sudoku.enums.Difficulty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SudokuPuzzle {
    private final UUID id;
    private final Cell[][] cells;
    private final Difficulty difficulty;
    private final int[][] solution;
    private final LocalDateTime createdAt;

    public SudokuPuzzle(Cell[][] cells, int[][] solution, Difficulty difficulty){
        this.id = UUID.randomUUID();
        this.cells = cells;
        this.solution = solution;
        this.difficulty = difficulty;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int[][] getSolution() {
        return solution;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
