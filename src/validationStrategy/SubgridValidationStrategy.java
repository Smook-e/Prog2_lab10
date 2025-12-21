package validationStrategy;

import model.SudokuBoard;
import model.ValidationResult;

import java.util.*;

public class SubgridValidationStrategy implements ValidationStrategy {

    @Override
    public void validate(SudokuBoard board, ValidationResult result) {
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {

                Map<Integer, List<String>> seen = new HashMap<>();
                int startRow = blockRow * 3;
                int startCol = blockCol * 3;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int num = board.getGrid(startRow + i, startCol + j);
                        if (num >= 1 && num <= 9) {
                            String pos = "(" + (startRow + i + 1) + "," + (startCol + j + 1) + ")";
                            seen.computeIfAbsent(num, k -> new ArrayList<>()).add(pos);
                        }
                    }
                }

                for (Map.Entry<Integer, List<String>> entry : seen.entrySet()) {
                    if (entry.getValue().size() > 1) {
                        result.addError(
                                "Box (" + (blockRow + 1) + "," + (blockCol + 1) +
                                        ") has duplicate " + entry.getKey() +
                                        " at positions " + entry.getValue()
                        );
                    }
                }
            }
        }
    }
}