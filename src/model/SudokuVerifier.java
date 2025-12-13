/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class SudokuVerifier {

    public ValidationResult validate(SudokuBoard board) {

        ValidationResult vr = new ValidationResult();

        // check incomplete
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getGrid(r, c) == 0) {
                    vr.markIncomplete();
                }
            }
        }

        // rows
        for (int r = 0; r < 9; r++) {
            boolean[] seen = new boolean[10];
            for (int c = 0; c < 9; c++) {
                int v = board.getGrid(r, c);
                if (v == 0) continue;
                if (seen[v]) {
                    vr.addError("ROW " + r + " DUPLICATE " + v);
                }
                seen[v] = true;
            }
        }

        // columns
        for (int c = 0; c < 9; c++) {
            boolean[] seen = new boolean[10];
            for (int r = 0; r < 9; r++) {
                int v = board.getGrid(r, c);
                if (v == 0) continue;
                if (seen[v]) {
                    vr.addError("COLUMN " + c + " DUPLICATE " + v);
                }
                seen[v] = true;
            }
        }

        // boxes
        for (int box = 0; box < 9; box++) {
            boolean[] seen = new boolean[10];
            int startRow = (box / 3) * 3;
            int startCol = (box % 3) * 3;

            for (int r = startRow; r < startRow + 3; r++) {
                for (int c = startCol; c < startCol + 3; c++) {
                    int v = board.getGrid(r, c);
                    if (v == 0) continue;
                    if (seen[v]) {
                        vr.addError("BOX " + box + " DUPLICATE " + v);
                    }
                    seen[v] = true;
                }
            }
        }

        return vr;
    }
}