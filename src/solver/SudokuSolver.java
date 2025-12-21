package solver;
import model.SudokuBoard;

import java.util.List;

public class SudokuSolver {

    private final SudokuBoard board;

    public SudokuSolver(SudokuBoard board) {
        this.board = board;
    }

    public boolean solve() {
        List<int[]> emptyCells = board.getEmptyCells();
        int count = emptyCells.size();
        iterator<int[]> iterator = new PermutationIterator();
// apply guess in place flyweight_board 
        while (iterator.hasNext()) {
            int[] guess = iterator.next();
            for (int i = 0; i < count; i++) {
                int row = emptyCells.get(i)[0];
                int col = emptyCells.get(i)[1];
                board.setDigit(row, col, guess[i]);
            }
            if (board.isValid()) {
                return true;
            } else {
                for (int i = 0; i < count; i++) {
                    int row = emptyCells.get(i)[0];
                    int col = emptyCells.get(i)[1];
                    board.clearDigit(row, col);
                }
            }
        }
        return false;
    }
}
