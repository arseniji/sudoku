package com.github.arseniji.sudoku.services;

import com.github.arseniji.sudoku.dto.PuzzleResponse;
import com.github.arseniji.sudoku.enums.Difficulty;
import com.github.arseniji.sudoku.models.Cell;
import com.github.arseniji.sudoku.models.SudokuGrid;
import com.github.arseniji.sudoku.models.SudokuPuzzle;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class SudokuService {
    private final Map<UUID, SudokuPuzzle> puzzles= new ConcurrentHashMap<>();

    public PuzzleResponse generate(Difficulty difficulty){
        SudokuGrid grid = new SudokuGrid(difficulty);
        Cell[][] cells = convertToCells(grid.getGrid());
        Cell[][] solution = convertToCells(grid.getSolution());
        SudokuPuzzle puzzle = new SudokuPuzzle(cells,solution,difficulty);
        puzzles.put(puzzle.getId(),puzzle);
        return toResponse(puzzle);
    }

    public PuzzleResponse getById(UUID id){
        SudokuPuzzle puzzle = puzzles.get(id);
        if (puzzle == null) throw new NoSuchElementException("Puzzle not found: " + id);
        return toResponse(puzzle);
    }

    public PuzzleResponse solve(UUID id){
        SudokuPuzzle puzzle = puzzles.get(id);
        if (puzzle == null) throw new NoSuchElementException("Puzzle not found: " + id);
        return toSolvedResponse(puzzle);
    }



    private Cell[][] convertToCells(int[][] grid){
        Cell[][] cells = new Cell[9][9];
        for (int row = 0; row< 9;row++){
            for(int col = 0; col< 9; col ++){
                cells[row][col] = new Cell(row,col,grid[row][col],grid[row][col] != 0);
            }
        }
        return cells;
    }

    private PuzzleResponse toResponse(SudokuPuzzle puzzle){
        return new PuzzleResponse(puzzle.getId(),puzzle.getDifficulty(),puzzle.getCells());
    }

    private PuzzleResponse toSolvedResponse(SudokuPuzzle puzzle){
        return new PuzzleResponse(puzzle.getId(),puzzle.getDifficulty(),puzzle.getSolution());
    }
}
