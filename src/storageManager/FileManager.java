package storageManager;
import java.io.*;
import javaapplication19.SudokuBoard;
public class FileManager {
    public static void saveBoard(String folder, SudokuBoard board) throws IOException {
        new File(folder).mkdirs();    
        BufferedWriter writer = new BufferedWriter(new FileWriter(folder + "/game.txt"));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                writer.write(board.getGrid()[i][j]+ " ");//getGrid for return int[][]
            }
            writer.newLine();
        }
        writer.close();
    }
    public static int[][] loadBoard(File file) throws IOException {
        int[][] grid = new int[9][9];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (int i = 0; i < 9; i++) {
                String line = br.readLine();

                String[] values = line.split(",");

                for (int j = 0; j < 9; j++) {
                    String val = values[j].trim();
                    if (val.isEmpty() || val.equals("0")) {
                        grid[i][j] = 0;
                    } else {
                        int num = Integer.parseInt(val);

                        grid[i][j] = num;
                    }
                }
            }
            // Check for extra lines

        } catch (NumberFormatException e) {
            throw new IOException("Invalid number format in file: " + e.getMessage());
        }
        return grid;
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
