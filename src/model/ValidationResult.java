/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;


public class ValidationResult {

    private boolean valid = true;
    private boolean complete = true;
    private List<String> errors = new ArrayList<>();

    public boolean isValid() {
        return valid;
    }

    public boolean isComplete() {
        return complete;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addError(String error) {
        valid = false;
        errors.add(error);
    }

    public void markIncomplete() {
        complete = false;
    }
    @Override
    public String toString() {
        if (valid) {
            return "Board is valid!";
        }
        StringBuilder sb = new StringBuilder("Board has errors:\n");
        for (String error : errors) {
            sb.append("- ").append(error).append("\n");
        }
        return sb.toString();
    }
}