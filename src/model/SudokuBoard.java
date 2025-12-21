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
    ///////////////
    public int[][] getArrayCopy() {
    int[][] copy = new int[grid.length][grid[0].length];
    for (int i = 0; i < grid.length; i++) {
        System.arraycopy(grid[i], 0, copy[i], 0, grid[i].length);
    }
    return copy;
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
    public SudokuBoard newBoard(){
       /* SudokuBoard board = new SudokuBoard(grid);
        return board;*/
       int[][]copy =new int[9][9];
       for(int i=0;i<9;i++)
       {
           System.arraycopy(grid[i],0,copy[i],0,9);
       }
       return new SudokuBoard(copy);
    }
    public int getGrid(int row, int col) {
        return grid[row][col];
    }
    //////////////////////////////////////
    public boolean isValid() {
        // Check rows
    for (int i = 0; i < 9; i++) {
        boolean[] seen = new boolean[10];
        for (int j = 0; j < 9; j++) {
            int val = grid[i][j];
            if (val != 0) {
                if (seen[val]) return false;
                seen[val] = true;
            }
        }
    }

    // Check columns
    for (int j = 0; j < 9; j++) {
        boolean[] seen = new boolean[10];
        for (int i = 0; i < 9; i++) {
            int val = grid[i][j];
            if (val != 0) {
                if (seen[val]) return false;
                seen[val] = true;
            }
        }
    }

    // Check 3x3 boxes
    for (int blockRow = 0; blockRow < 3; blockRow++) {
        for (int blockCol = 0; blockCol < 3; blockCol++) {
            boolean[] seen = new boolean[10];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int val = grid[blockRow * 3 + i][blockCol * 3 + j];
                    if (val != 0) {
                        if (seen[val]) return false;
                        seen[val] = true;
                    }
                }
            }
        }
    }

    return true;
}
    
    public void clearDigit(int row, int col) {
        grid[row][col] = 0;
    }
}