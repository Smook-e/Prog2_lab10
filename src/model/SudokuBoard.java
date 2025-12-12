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
}

