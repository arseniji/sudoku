package com.github.arseniji.sudoku.controllers;

import com.github.arseniji.sudoku.dto.PuzzleResponse;
import com.github.arseniji.sudoku.dto.SolveRequest;
import com.github.arseniji.sudoku.dto.ValidationResponse;
import com.github.arseniji.sudoku.enums.Difficulty;
import com.github.arseniji.sudoku.services.SudokuService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/puzzle")
public class SudokuController {
    private final SudokuService sudokuService;

    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }


    @GetMapping
    public PuzzleResponse generate(@RequestParam Difficulty difficulty){
        return sudokuService.generate(difficulty);
    }

    @GetMapping("/{id}")
    public PuzzleResponse getById(@PathVariable UUID id){
        return sudokuService.getById(id);
    }

    @PostMapping("/solve")
    public PuzzleResponse solve(@RequestBody SolveRequest request){
        return sudokuService.solve(request.id());
    }

    @PostMapping("/validate")
    public ValidationResponse validate(@RequestBody SolveRequest request){
        return sudokuService.validate(request.id(),request);
    }
}
