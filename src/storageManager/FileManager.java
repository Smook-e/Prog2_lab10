package storageManager;
import java.io.*;
import model.SudokuBoard;
public class FileManager {
    public static void saveBoard(String folder, SudokuBoard board) throws IOException {
        new File(folder).mkdirs();
        BufferedWriter writer = new BufferedWriter(new FileWriter(folder + "/game.txt"));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                writer.write(board.getArray()[i][j]+ " ");//getGrid for return int[][]
            }
            writer.newLine();
        }
        writer.close();
    }
    public static SudokuBoard loadBoard(String folder) throws IOException {
        int[][] board = new int[9][9];
        BufferedReader reader = new BufferedReader(new FileReader(folder + "/game.txt"));
        for (int i = 0; i < 9; i++) {
            String[] values = reader.readLine().trim().split("\\s+");
            for (int j = 0; j < 9; j++) {
                board[i][j] = Integer.parseInt(values[j]);
            }
        }
        reader.close();
        return new SudokuBoard(board);//constructor for SudokuBoard
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