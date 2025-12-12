//package storageManager;
//
//import java.io.IOException;
//import model.DifficultyLevel;
//import model.SudokuBoard;
//
//
//public class GameStorageManager {
//
//    public void saveGame(DifficultyLevel level, SudokuBoard board) {
//        try {
//            FileManager.saveBoard(level.getfolderName(), board);
//        } catch (IOException ex) {
//            System.out.println("error saving game");
//        }
//
//    }
//
//    public SudokuBoard loadGame(DifficultyLevel level) {
//        try {
//            return FileManager.loadBoard(level.getfolderName());
//        } catch (IOException ex) {
//            System.out.println("error loading game");
//            return null;
//        }
//    }
//
//    public void deleteGame(DifficultyLevel level) {
//        FileManager.delete(level.getfolderName());
//    }
//
//    public void saveCurrentGame(SudokuBoard board) {
//        try {
//            FileManager.saveBoard("current", board);
//        } catch (IOException ex) {
//            System.out.println("error saving game");
//        }
//    }
//
//    public SudokuBoard loadCurrentGame() {
//        try {
//            return FileManager.loadBoard("current");
//        } catch (IOException ex) {
//            System.out.println("error loading game");
//            return null;
//        }
//    }
//
//    public boolean hasCurrentGame() {
//        return FileManager.exists("current");
//    }
//}
