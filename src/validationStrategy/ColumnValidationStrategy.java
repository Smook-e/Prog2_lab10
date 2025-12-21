package validationStrategy;

import model.SudokuBoard;
import model.ValidationResult;

import java.util.*;

public class ColumnValidationStrategy implements ValidationStrategy {

    @Override
    public void validate(SudokuBoard board, ValidationResult result) {
        for (int col = 0; col < 9; col++) {
            Map<Integer, List<Integer>> seen = new HashMap<>();

            for (int row = 0; row < 9; row++) {
                int num = board.getGrid(row, col);
                if (num >= 1 && num <= 9) {
                    seen.computeIfAbsent(num, k -> new ArrayList<>()).add(row);
                }
            }

            for (Map.Entry<Integer, List<Integer>> entry : seen.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addError(
                            "Column " + (col + 1) +
                                    " has duplicate " + entry.getKey() +
                                    " at rows " + entry.getValue()
                    );
                }
            }
        }
    }
}