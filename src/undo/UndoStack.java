package undo;


import java.util.ArrayDeque;
import java.util.Deque;

public class UndoStack {
    private final Deque<BoardRecord> stack = new ArrayDeque<>();

    public void push(int[][] boardState) {
        stack.push(new BoardRecord(boardState));
    }

    public int[][] undo() {
        if (stack.isEmpty())
            throw new IllegalStateException("Nothing to undo");

        return stack.pop().getcopy();
    }

    public boolean hasUndo() {
        return !stack.isEmpty();
    }

    public void clear() {
        stack.clear();
    }
}
