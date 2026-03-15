package com.github.arseniji.sudoku.dto;

import java.util.UUID;

public record SolveRequest(UUID id, int[][] cells) {
}
