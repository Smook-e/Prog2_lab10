package validationStrategy;


import model.SudokuBoard;
import model.ValidationResult;

public class CompletenessValidationStrategy implements ValidationStrategy {

    @Override
    public void validate(SudokuBoard board, ValidationResult result) {
        for (int[] row : board.getArray()) {
            for (int cell : row) {
                if (cell == 0) {
                    result.markIncomplete();
                    return;
                }
            }
        }
        result.markComplete();
    }
}