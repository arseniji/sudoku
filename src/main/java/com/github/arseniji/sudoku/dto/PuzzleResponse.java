package com.github.arseniji.sudoku.dto;

import com.github.arseniji.sudoku.enums.Difficulty;
import com.github.arseniji.sudoku.models.Cell;

import java.util.UUID;

public record PuzzleResponse(UUID id, Difficulty difficulty, Cell[][] cells) {
}
