package solver;

import model.SudokuBoard;
import java.util.List;


public class SolverWorker extends Thread {
    private final SudokuBoard board;
    private final List<int[]> emptyCells;
    private final List<int[]> guesses;
    private final SolutionObserver observer;
    private boolean stop = false;

    public SolverWorker(SudokuBoard board, List<int[]> emptyCells, List<int[]> guesses, SolutionObserver observer) {
        this.board = board;
        this.emptyCells = emptyCells;
        this.guesses = guesses;
        this.observer = observer;
    }

    public void stopWorker() { stop = true; }

    @Override
    public void run() {
        for (int[] guess : guesses) {
            if (stop) return;

            // Create a temporary board copy
            SudokuBoard temp = board.newBoard();

            // Apply the guess in temp
            for (int i = 0; i < 5; i++) {
                int r = emptyCells.get(i)[0];
                int c = emptyCells.get(i)[1];
                temp.setDigit(r, c, guess[i]);
            }

            if (temp.isValid()) {
                observer.solutionFound(guess);
                return;
            }
        }
    }
}
