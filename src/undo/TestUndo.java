package undo;

import model.SudokuBoard;
import storageManager.FileManager;

public class TestUndo {
    public static void main(String[] args) throws Exception {

        SudokuBoard board = new SudokuBoard(new int[9][9]);
        String folder = "incomplete";

        // Move 1
        makeMove(board, folder, 0, 0, 5);

        // Move 2
        makeMove(board, folder, 0, 1, 3);
 makeMove(board, folder, 0, 2, 4);
        // Undo last move
        undo(board, folder);
    }

    static void makeMove(SudokuBoard board, String folder,
                         int r, int c, int val) throws Exception {

        int prev = board.getGrid(r, c);
        board.setDigit(r, c, val);
        UndoLogManager.logMove(
            folder,
            new UndoLogEntry(r, c, val, prev)
        );
        FileManager.saveBoard(folder, board);
    }

    static void undo(SudokuBoard board, String folder) throws Exception {
        UndoLogEntry e = UndoLogManager.popLast(folder);
        if (e != null) {
            board.setDigit(e.row, e.col, e.prev);
            FileManager.saveBoard(folder, board);
        }
    }
}
