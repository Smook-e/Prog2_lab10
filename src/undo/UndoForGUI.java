package undo;

public class UndoForGUI {
    private final UndoStack history = new UndoStack();

    public void save(int[][] board) {
        history.push(board);
    }

    public int[][] undo() {
        return history.undo();
    }

    public boolean canUndo() {
        return history.hasUndo();
    }

    public void clear() {
        history.clear();
    }
}
