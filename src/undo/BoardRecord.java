package undo;
public class BoardRecord {
    private final int[][] boardCopy;

    public BoardRecord(int[][] board) {
        this.boardCopy =  BoardCopy(board); 
    }

    private int[][] BoardCopy(int[][] source) {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            System.arraycopy(source[r], 0, copy[r], 0, 9);
        }
        return copy;
    }
    
     public int[][] getcopy() {
        return boardCopy;  
    }
}