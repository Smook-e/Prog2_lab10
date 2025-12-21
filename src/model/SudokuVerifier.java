/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.*;


public class SudokuVerifier {
        public ValidationResult validate(SudokuBoard board) {
            ValidationResult result = new ValidationResult();
            if(!isComplete(board)) {
                result.markIncomplete();
            }
            else{
                result.markComplete();
            }
            // Check rows
            for (int row = 0; row < 9; row++) {
                validateRow(board, row, result);
            }

            // Check columns
            for (int col = 0; col < 9; col++) {
                validateColumn(board,col, result);
            }

            // Check box
            for (int blockRow = 0; blockRow < 3; blockRow++) {
                for (int blockCol = 0; blockCol < 3; blockCol++) {
                    validateSubgrid(board,blockRow * 3, blockCol * 3, result);
                }
            }

            return result;
        }

        private void validateRow(SudokuBoard board, int row, ValidationResult result) {
            Map<Integer, List<Integer>> seen = new HashMap<>();
            for (int col = 0; col < 9; col++) {
                int num = board.getGrid(row,col);
                if (num >= 1 && num <= 9) {
                    seen.computeIfAbsent(num, k -> new ArrayList<>()).add(col);
                }
            }
            for (Map.Entry<Integer, List<Integer>> entry : seen.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addError("Row" + (row + 1) + " has duplicate " + entry.getKey() + " at columns " +  entry.getValue()
                           );
                }
            }
        }

        private void validateColumn(SudokuBoard board, int col, ValidationResult result) {
            Map<Integer, List<Integer>> seen = new HashMap<>();
            for (int row = 0; row < 9; row++) {
                int num = board.getGrid(row,col);
                if (num >= 1 && num <= 9) {
                    seen.computeIfAbsent(num, k -> new ArrayList<>()).add(row);
                }
            }
            for (Map.Entry<Integer, List<Integer>> entry : seen.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addError("Column" + (col + 1) + " has duplicate " + entry.getKey() + " at columns " +  entry.getValue()
                    );

                }
            }
        }

        private void validateSubgrid(SudokuBoard board, int startRow, int startCol, ValidationResult result) {
            Map<Integer, List<String>> seen = new HashMap<>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int num = board.getGrid(startRow + i, startCol + j);
                    if (num >= 1 && num <= 9) {
                        String pos = String.format("(%d,%d)", startRow + i + 1, startCol + j + 1);
                        seen.computeIfAbsent(num, k -> new ArrayList<>()).add(pos);
                    }
                }
            }
            for (Map.Entry<Integer, List<String>> entry : seen.entrySet()) {
                if (entry.getValue().size() > 1) {
                    result.addError(String.format("Box (%d,%d) has duplicate %d at positions %s",
                            startRow / 3 + 1, startCol / 3 + 1, entry.getKey(), entry.getValue()));
                }
            }
        }


        public boolean isComplete(SudokuBoard board) {

            for (int[] row : board.getArray()) {
                for (int cell : row) {
                    if (cell == 0) return false;
                }
            }
            return true;
        }
}

