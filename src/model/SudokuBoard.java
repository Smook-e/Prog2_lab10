package model;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard {
    private final int[][] grid;

    public SudokuBoard(int[][] grid) {
        this.grid = grid;
    }
    public int[][] getArray() {
        return grid;
    }
    public List<int[]> getEmptyCells() {
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == 0){
                    list.add(new int[]{i, j});
                }
            }
        }
        return list;
    }
    public void setDigit(int i, int j, int digit) {
        grid[i][j] = digit;
    }

    public int getGrid(int row, int col) {
        return grid[row][col];
    }
    public boolean isValid() {
        return false;
    }
    public void clearDigit(int row, int col) {
        grid[row][col] = 0;
    }
}

 class Entry{
    int  row;
    int col;
    int value;
    public Entry(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }
}