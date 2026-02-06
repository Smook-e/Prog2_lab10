package solver;
import java.util.ArrayList;
import java.util.List;
import model.SudokuBoard;

public class SudokuSolver implements SolutionObserver {

    private final SudokuBoard board;
    private int[] finalSolution = null;

    private List<SolverWorker> workers = new ArrayList<>();

    public SudokuSolver(SudokuBoard board) {
        this.board = board;
    }

    public boolean solve() {

        List<int[]> empty = board.getEmptyCells();
        if (empty.size() != 5) {
            System.out.println("Solver works only for exactly 5 empty cells");
            return false;
        }

        List<int[]> all = generateAll();
        int part = all.size() / 3;

        List<int[]> p1 = all.subList(0, part);
        List<int[]> p2 = all.subList(part, part*2);
        List<int[]> p3 = all.subList(part*2, all.size());

        workers.add(new SolverWorker(board, empty, p1, this));
        workers.add(new SolverWorker(board, empty, p2, this));
        workers.add(new SolverWorker(board, empty, p3, this));

        // start threads
        for (Thread t : workers) t.start();

        // wait threads
        for (Thread t : workers){
            try { t.join(); } catch(Exception e){}
        }

        // if solution found → write final once
        if(finalSolution != null){
            for(int i=0;i<5;i++){
                int r = empty.get(i)[0];
                int c = empty.get(i)[1];
                board.setDigit(r,c, finalSolution[i]);
            }
            return true;
        }

        return false;
    }

    // generate all 9^5 guesses
    private List<int[]> generateAll(){
        List<int[]> list = new ArrayList<>();

        for(int a=1;a<=9;a++)
        for(int b=1;b<=9;b++)
        for(int c=1;c<=9;c++)
        for(int d=1;d<=9;d++)
        for(int e=1;e<=9;e++)
            list.add(new int[]{a,b,c,d,e});

        return list;
    }

    // 🔴 OBSERVER METHOD
    public void solutionFound(int[] solution) {
    if (finalSolution != null) return; // already found
    finalSolution = solution;
    for (SolverWorker w : workers) w.stopWorker();
}
}