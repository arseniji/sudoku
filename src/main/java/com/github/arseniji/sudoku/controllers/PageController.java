package com.github.arseniji.sudoku.controllers;

import com.github.arseniji.sudoku.dto.PuzzleResponse;
import com.github.arseniji.sudoku.enums.Difficulty;
import com.github.arseniji.sudoku.services.SudokuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class PageController {

    private final SudokuService sudokuService;

    public PageController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/game")
    public String game(@RequestParam Difficulty difficulty, Model model) {
        PuzzleResponse puzzle = sudokuService.generate(difficulty);
        model.addAttribute("puzzle", puzzle);
        model.addAttribute("puzzleId", puzzle.id().toString());
        return "game";
    }
}