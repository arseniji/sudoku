package com.github.arseniji.sudoku.dto;

import com.github.arseniji.sudoku.models.Cell;

import java.util.List;

public record ValidationResponse(boolean correct, List<CellDto> wrongs) {

}
