package solver;


import java.util.List;
import model.SudokuBoard;

public class SolverWorker extends Thread {

    private final SudokuBoard board;
    private final List<int[]> emptyCells;
    private final iterator<int[]> iterator;
    private final SolutionObserver observer;

    private boolean stopRequested = false;

    public SolverWorker(
            SudokuBoard board,
            List<int[]> emptyCells,
            iterator<int[]> iterator,
            SolutionObserver observer) {

        this.board = board;
        this.emptyCells = emptyCells;
        this.iterator = iterator;
        this.observer = observer;
    }

    public void requestStop() {
        stopRequested = true;
    }

    @Override
    public void run() {
        int count = emptyCells.size();

        while (!stopRequested && iterator.hasNext()) {
            int[] guess = iterator.next();
            
            for (int i = 0; i < count; i++) {
                int r = emptyCells.get(i)[0];
                int c = emptyCells.get(i)[1];
                board.setDigit(r, c, guess[i]);
            }

            if (board.isValid()) {
                observer.solutionFound(guess);
                return;
            }

            for (int i = 0; i < count; i++) {
                int r = emptyCells.get(i)[0];
                int c = emptyCells.get(i)[1];
                board.clearDigit(r, c);
            }
        }
    }
}
