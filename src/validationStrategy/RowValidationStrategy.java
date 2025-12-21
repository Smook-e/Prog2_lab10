package validationStrategy;

import model.SudokuBoard;
import model.ValidationResult;

import java.util.*;

public class RowValidationStrategy implements ValidationStrategy {

    @Override
    public void validate(SudokuBoard board, ValidationResult result) {
        for (int row = 0; row < 9; row++) {
            Map<Integer, List<Integer>> seen = new HashMap<>();

            for (int col = 0; col < 9; col++) {
                int num = board.getGrid(row, col);
                if (num >= 1 && num <= 9) {
                    seen.computeIfAbsent(num, k -> new ArrayList<>()).add(col);
                }
            }

            for (Map.Entry<Integer, List<Integer>> entry : seen.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addError(
                            "Row " + (row + 1) +
                                    " has duplicate " + entry.getKey() +
                                    " at columns " + entry.getValue()
                    );
                }
            }
        }
    }


}