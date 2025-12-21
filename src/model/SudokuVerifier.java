/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import validationStrategy.*;

import java.util.*;


public class SudokuVerifier {

    private final List<ValidationStrategy> strategies = new ArrayList<>();

    public SudokuVerifier() {
        strategies.add(new CompletenessValidationStrategy());
        strategies.add(new RowValidationStrategy());
        strategies.add(new ColumnValidationStrategy());
        strategies.add(new SubgridValidationStrategy());
    }

    public ValidationResult validate(SudokuBoard board) {
        ValidationResult result = new ValidationResult();

        for (ValidationStrategy strategy : strategies) {
            strategy.validate(board, result);
        }

        return result;
    }
}

