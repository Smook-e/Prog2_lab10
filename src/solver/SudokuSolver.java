package solver;
import java.util.ArrayList;
import model.SudokuBoard;

import java.util.List;

public class SudokuSolver implements SolutionObserver {

    private final SudokuBoard originalBoard;
    private final List<SolverWorker> workers = new ArrayList<>();

    private boolean solved = false;
    private int[] solution;

    public SudokuSolver(SudokuBoard board) {
        this.originalBoard = board;
    }
    public boolean solve() {

        List<int[]> emptyCells = originalBoard.getEmptyCells();
        if (emptyCells.size() != 5) {
            return false;
        }

        int totalPermutations = (int) Math.pow(9, 5); // 59049
        int threadCount = 3;
        int range = totalPermutations / threadCount;

        for (int i = 0; i < threadCount; i++) {
            int start = i * range;
            int end = (i == threadCount - 1)? totalPermutations - 1: (start + range - 1);

            SudokuBoard workerBoard =
                    new SudokuBoard(originalBoard.getArray());

            iterator<int[]> iterator =
                    new RangePermutationIterator(start, end, 5);

            SolverWorker worker =
                    new SolverWorker(workerBoard, emptyCells, iterator, this);

            workers.add(worker);
            worker.start();
        }

        for (SolverWorker w : workers) {
            try {
                w.join();
            } catch (InterruptedException ignored) {}
        }

        if (solved) {
            applySolution();
        }

        return solved;
    }

    @Override
    public void solutionFound(int[] solution) {
        if (solved) return;

        solved = true;
        this.solution = solution;

        // stop others(notify)
        for (SolverWorker w : workers) {
            w.requestStop();
        }
    }

    private void applySolution() {
        List<int[]> emptyCells = originalBoard.getEmptyCells();
        for (int i = 0; i < 5; i++) {
            int r = emptyCells.get(i)[0];
            int c = emptyCells.get(i)[1];
            originalBoard.setDigit(r, c, solution[i]);
        }
    }
}
