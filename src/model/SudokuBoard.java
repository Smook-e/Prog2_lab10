package model;

public class SudokuBoard {
    private final int[][] grid;

    public SudokuBoard(int[][] grid) {
        this.grid = grid;
    }

    public int get(int row, int col) {
        return grid[row][col];
    }
}
