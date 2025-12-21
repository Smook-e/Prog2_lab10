import model.SudokuBoard;
import model.SudokuVerifier;
import model.ValidationResult;
import solver.SudokuSolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.


        int[][] loadedGrid = null;
        try {
            loadedGrid = loadFromCsv("src\\files\\invalid.csv");

        } catch (IOException e) {
            System.err.println("Error loading CSV: " + e.getMessage());
            return;
        }


        SudokuBoard board = new SudokuBoard(loadedGrid);


        printBoard(board);

        SudokuVerifier sv =  new SudokuVerifier();
        ValidationResult vr =  sv.validate(board);
        System.out.println(vr);










    }


    private static int[][] loadFromCsv(String filePath) throws IOException {
        int[][] grid = new int[9][9];
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            for (int i = 0; i < 9; i++) {
                String line = br.readLine();
                if (line == null) {
                    throw new IOException("CSV has fewer than 9 rows");
                }
                String[] values = line.split(",");
                if (values.length != 9) {
                    throw new IOException("Row " + (i + 1) + " does not have 9 columns");
                }
                for (int j = 0; j < 9; j++) {
                    String val = values[j].trim();
                    grid[i][j] = val.isEmpty() ? 0 : Integer.parseInt(val);
                }
            }
        }
        return grid;
    }





    private static void printBoard(SudokuBoard board) {
        int[][] grid = board.getArray();
        System.out.println("Current Sudoku Board:");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int val = grid[i][j];
                System.out.print( val + " ");
                if ((j + 1) % 3 == 0 && j != 8) System.out.print("| ");
            }
            System.out.println();
            if ((i + 1) % 3 == 0 && i != 8) System.out.println("---------------------");
        }
    }
    }
