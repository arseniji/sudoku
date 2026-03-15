package com.github.arseniji.sudoku.models;

import com.github.arseniji.sudoku.enums.Difficulty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import static java.util.Collections.shuffle;

public class SudokuGrid {
    private int[][] grid;
    private int[][] solution;
    private final List<Integer> cords;
    private final Random random;
    public SudokuGrid(Difficulty difficulty){
        this.grid = new int[9][9];
        this.cords = new ArrayList<>(IntStream.rangeClosed(0,80).boxed().toList());
        this.random = new Random();
        fill();
        generatePuzzle(difficulty.getValue());
    }
    private void fill(){
        generateDiagonalBlocks();
        generateBackTracking();
        this.solution = copy(grid);
    }

    private void generateDiagonalBlocks(){
        for (int k = 0; k < 3; k++){
            for (int i = (k * 3); i < k * 3 + 3; i++){
                for (int j = (k * 3); j < k * 3 + 3; j++){
                    int num = getRandom(i, j);
                    if (num == 0) throw new RuntimeException("Не удалось заполнить блок");
                    grid[i][j] = num;
                }
            }
        }
    }

    private boolean generateBackTracking(){
        for (int row = 0; row < 9; row ++){
            for (int col = 0; col < 9; col ++){
                if (grid[row][col]!=0) continue;
                List<Integer> randomNumbers = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9));
                shuffle(randomNumbers);
                for (int number : randomNumbers){
                    if (isValidNumber(number,row,col)) {
                        grid[row][col] = number;
                        if (generateBackTracking()) return true;
                        grid[row][col] = 0;
                    }
                }
                return false;
            }
        }
        return true;
    }

    private void generatePuzzle(int cluesToLeave) {
        int filled = 81;
        for (int attempt = 0; attempt < 10 && filled > cluesToLeave; attempt++) {
            shuffle(cords);
            for (Integer cord : cords) {
                if (filled <= cluesToLeave) break;
                if (grid[cord/9][cord%9] == 0) continue;
                int saved = grid[cord/9][cord%9];
                grid[cord/9][cord%9] = 0;
                if (!hasSolution()) grid[cord/9][cord%9] = saved;
                else filled--;
            }
        }
    }

    private void resolve(){
        int[][] grid = {
                {0,0,0,0,9,3,0,0,0},
                {0,0,8,2,0,0,3,0,0},
                {0,7,0,0,0,0,0,4,0},
                {0,6,0,0,0,0,0,0,3},
                {2,0,7,0,0,0,1,0,0},
                {5,0,0,0,0,8,0,0,9},
                {7,0,0,0,8,0,0,0,0},
                {0,0,0,0,6,0,8,0,4},
                {8,0,2,0,4,5,0,0,1}
        };
        this.grid = grid;
        generateBackTracking();
        show();
    }

    private boolean hasSolution(){
        return countSolutionsRec(0,0,2) == 1;
    }

    private int countSolutionsRec(int row, int col, int limit) {
        while (row < 9 && grid[row][col] != 0) {
            col++;
            if (col == 9) { col = 0; row++; }
        }
        if (row == 9) return 1;
        int solutionsFound = 0;
        for (int number = 1; number <= 9; number++) {
            if (isValidNumber(number, row, col)) {
                grid[row][col] = number;
                int nextCol = col + 1, nextRow = row;
                if (nextCol == 9) { nextCol = 0; nextRow++; }
                solutionsFound += countSolutionsRec(nextRow, nextCol, limit - solutionsFound);
                grid[row][col] = 0;

                if (solutionsFound >= limit) return solutionsFound;
            }
        }
        return solutionsFound;
    }

    private boolean inRow(int a,int row){
        for (int i = 0; i < 9; i++){
            if (grid[row][i] == a) return true;
        }
        return false;
    }
    private boolean inCol(int a,int col){
        for (int i = 0; i < 9; i++){
            if (grid[i][col] == a) return true;
        }
        return false;
    }

    private boolean inBlock(int a, int row, int col){
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (grid[i][j] == a) return true;
            }
        }
        return false;
    }

    private boolean isValidNumber(int a, int row, int col){
        return !inRow(a, row) && !inCol(a,col) && !inBlock(a,row,col);
    }

    private int getRandom(int row, int col) {
        List<Integer> validNumbers = new ArrayList<>();
        for (int num = 1; num <= 9; num++) {
            if (isValidNumber(num,row,col)) validNumbers.add(num);
        }
        if (validNumbers.isEmpty()) return 0;
        return validNumbers.get(random.nextInt(validNumbers.size()));
    }

    public void show(){
        for (int i =0;i < 9;i++){
            for (int j = 0; j < 9; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] getGrid(){
        return grid;
    }

    public int[][] getSolution(){
        return solution;
    }

    private int[][] copy(int[][] grid){
        int [][] copy = new int[9][9];
        for (int i = 0; i< 9; i++){
            copy[i] = grid[i].clone();
        }
        return copy;
    }

    public static void main(String[] args){
        SudokuGrid sg = new SudokuGrid(Difficulty.MEDIUM);
        sg.resolve();
    }
}
