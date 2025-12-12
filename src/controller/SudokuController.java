/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import difficultyManager.DifficultyGenerator;
import exceptions.InvalidGame;
import exceptions.NotFoundException;
import exceptions.SolutionInvalidException;
import java.io.IOException;
import model.Catalog;
import model.DifficultyLevel;
import model.UserAction;
import storageManager.GameStorageManager;
import undo.UndoForGUI;

/**
 *
 * @author HP
 */
public class SudokuController implements Controllable {
    private DifficultyGenerator difficultyGenerator;
    private GameStorageManager storageManager;
    private UndoForGUI undoForGUI;
    public SudokuController()
    {
        this.difficultyGenerator= new DifficultyGenerator();
        this.storageManager=new GameStorageManager();
        this.undoForGUI=new UndoForGUI();
    }
    @Override
    public Catalog getCatalog() {
        Catalog catalog=new Catalog();
        catalog.current=storageManager.hasCurrentGame();
        if(storageManager.hasCurrentGame()&&
            (storageManager.loadGame(DifficultyLevel.EASY)!=null)&&
            (storageManager.loadGame(DifficultyLevel.MEDIUM)!=null)&&(storageManager.loadGame(DifficultyLevel.HARD)!=null))
        {
            catalog.allModesExist=true;
        }
        else
            catalog.allModesExist=false;
        return catalog;   
    }
    @Override
    public int[][] getGame(char level) throws NotFoundException {
        
    }

    @Override
    public void driveGames(int[][] source) throws SolutionInvalidException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean[][] verifyGame(int[][] game) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int[][] solveGame(int[][] game) throws InvalidGame {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void logUserAction(UserAction userAction) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
