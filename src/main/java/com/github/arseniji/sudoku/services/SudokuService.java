package com.github.arseniji.sudoku.services;

import com.github.arseniji.sudoku.dto.CellDto;
import com.github.arseniji.sudoku.dto.PuzzleResponse;
import com.github.arseniji.sudoku.dto.SolveRequest;
import com.github.arseniji.sudoku.dto.ValidationResponse;
import com.github.arseniji.sudoku.enums.Difficulty;
import com.github.arseniji.sudoku.models.Cell;
import com.github.arseniji.sudoku.models.SudokuGrid;
import com.github.arseniji.sudoku.models.SudokuPuzzle;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class SudokuService {
    private final Map<UUID, SudokuPuzzle> puzzles= new ConcurrentHashMap<>();

    public PuzzleResponse generate(Difficulty difficulty){
        SudokuGrid grid = new SudokuGrid(difficulty);
        Cell[][] cells = convertToCells(grid.getGrid());
        Cell[][] solution = convertSolution(grid.getGrid(),grid.getSolution());
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

    public ValidationResponse validate(UUID id, SolveRequest request){
        SudokuPuzzle puzzle = puzzles.get(id);
        if (puzzle == null) throw new NoSuchElementException("Puzzle not found: " + id);

        List<CellDto> wrongCells = new ArrayList<>();
        int[][] solution = toIntGrid(puzzle.getSolution());
        int[][] submitted = request.getCells();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (submitted[row][col] != solution[row][col]) {
                    wrongCells.add(new CellDto(row, col, submitted[row][col], false));
                }
            }
        }
        return new ValidationResponse(wrongCells.isEmpty(), wrongCells);
    }



    private Cell[][] convertToCells(int[][] puzzle) {
        Cell[][] cells = new Cell[9][9];
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                cells[row][col] = new Cell(row, col, puzzle[row][col], puzzle[row][col] != 0);
        return cells;
    }

    private int[][] toIntGrid(Cell[][] cells) {
        int[][] grid = new int[9][9];
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                grid[row][col] = cells[row][col].getValue();
        return grid;
    }

    private Cell[][] convertSolution(int[][] puzzle, int[][] solution) {
        Cell[][] cells = new Cell[9][9];
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                cells[row][col] = new Cell(row, col, solution[row][col], puzzle[row][col] != 0);
        return cells;
    }

    private CellDto[][] toCellDtos(Cell[][] cells) {
        CellDto[][] dtos = new CellDto[9][9];
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++)
                dtos[row][col] = new CellDto(cells[row][col].getRow(), cells[row][col].getCol(),
                        cells[row][col].getValue(), cells[row][col].isGen());
        return dtos;
    }
    private PuzzleResponse toResponse(SudokuPuzzle puzzle) {
        return new PuzzleResponse(puzzle.getId(), puzzle.getDifficulty(), toCellDtos(puzzle.getCells()));
    }
    private PuzzleResponse toSolvedResponse(SudokuPuzzle puzzle){
        return new PuzzleResponse(puzzle.getId(),puzzle.getDifficulty(),toCellDtos(puzzle.getSolution()));
    }
}
