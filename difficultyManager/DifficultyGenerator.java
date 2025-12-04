package difficultyManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.DifficultyLevel;

public class DifficultyGenerator {
   private final RandomPairs randomPairs = new RandomPairs();
    public SudokuBoard removeCells(SudokuBoard solvedBoard, DifficultyLevel level) {
        SudokuBoard newBoard = solvedBoard.cloneBoard(); 
        int count = level.getCellsToRemove();
        Set<Integer> uniqueLinearIndices = new HashSet<>();
        
        while (uniqueLinearIndices.size() < count) {
            List<int[]> pairs = randomPairs.generateDistinctPairs(1); 
            int x = pairs.get(0)[0];
            int linearIndex = x % 81; 
            
            if (uniqueLinearIndices.add(linearIndex)) {
                int r = linearIndex / 9;
                int c = linearIndex % 9;
                newBoard.setCell(r, c, 0); 
            }
        }
        return newBoard;
    }

    public SudokuBoard generateEasy(SudokuBoard solved) {
        return removeCells(solved, DifficultyLevel.EASY);
    }
    public SudokuBoard generateMedium(SudokuBoard solved) {
        return removeCells(solved, DifficultyLevel.MEDIUM);
    }
    public SudokuBoard generateHard(SudokuBoard solved) {
        return removeCells(solved, DifficultyLevel.HARD);
    } 
}
