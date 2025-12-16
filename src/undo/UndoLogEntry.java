package undo;
public class UndoLogEntry {
    public final int row;
    public final int col;
    public final int value;
    public final int prev;

    public UndoLogEntry(int row, int col, int value, int prev) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.prev = prev;
    }

    // (x,y,val,prev)
    public String toLine() {
        return row + "," + col + "," + value + "," + prev;
    }

    public static UndoLogEntry fromLine(String line) {
        String[] parts = line.split(",");
        return new UndoLogEntry(
            Integer.parseInt(parts[0]),
            Integer.parseInt(parts[1]),
            Integer.parseInt(parts[2]),
            Integer.parseInt(parts[3])
        );
    }
}
