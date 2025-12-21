package validationStrategy;

import model.SudokuBoard;
import model.ValidationResult;

public interface ValidationStrategy {
    void validate(SudokuBoard board, ValidationResult result);
}