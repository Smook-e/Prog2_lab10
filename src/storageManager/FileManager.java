package storageManager;
import java.io.*;
import java.util.Arrays;


import model.SudokuBoard;

public class FileManager {
    public static void saveBoard(File file, SudokuBoard board) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    bw.write(String.valueOf(board.getGrid(i,j)));
                    if (j < 8) bw.write(",");
                }
                bw.newLine();
            }
        }
    }
    public static SudokuBoard loadBoard(File file) throws IOException {
        int[][] grid = new int[9][9];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (int i = 0; i < 9; i++) {
                String line = br.readLine();
                if (line == null) {
                    throw new IOException("file has less than 9 rows");
                }
                String[] values = line.split(",");
                if (values.length != 9) {
                    throw new IOException("Row " + (i + 1) + " does not contain 9 numbers");
                }
                for (int j = 0; j < 9; j++) {
                    String val = values[j].trim();
                    if (val.isEmpty() || val.equals("0")) {
                        grid[i][j] = 0;
                    } else {
                        int num = Integer.parseInt(val);
                        if (num < 1 || num > 9) {
                            throw new IOException("Invalid number in grid: " + num + " at row " + (i+1) + ", col " + (j+1));
                        }
                        grid[i][j] = num;
                    }
                }
            }
            // Check for extra lines
            if (br.readLine() != null) {
                System.err.println("Warning: File has more than 9 rows. Extra lines ignored.");
            }
        } catch (NumberFormatException e) {
            throw new IOException("Invalid number format in file: " + e.getMessage());
        }
        return new SudokuBoard(grid);
    }
    public static boolean exists(String folder) {
        return new File(folder + "/game.txt").exists();
    }
    public static void delete(String folder) {
        if(exists(folder))
        {          
            new File(folder + "/game.txt").delete();
        }
        else
        {
            System.out.println("error deleting game");
        }
    }
}
