package model;

public class SudokuBoard {
    private final int[][] grid;

    public SudokuBoard(int[][] grid) {
        this.grid = grid;
    }

    public int getGrid(int row, int col) {
        return grid[row][col];
    }
}
