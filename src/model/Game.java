/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class Game {
    private SudokuBoard board;
    private DifficultyLevel difficultyLevel;
    public Game(SudokuBoard board,DifficultyLevel difficultyLevel)
    {
        this.board=board;
        this.difficultyLevel=difficultyLevel;
    }
    public SudokuBoard getBoard()
    {
        return board;
    }
    public DifficultyLevel getDifficultyLevel()
    {
        return difficultyLevel;
    }
}
