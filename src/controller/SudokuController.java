/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import difficultyManager.DifficultyGenerator;
import exceptions.InvalidGame;
import exceptions.NotFoundException;
import exceptions.SolutionInvalidException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import model.Catalog;
import model.DifficultyLevel;
import model.SudokuBoard;
import model.SudokuVerifier;
import model.UserAction;
import model.ValidationResult;
import solver.SudokuSolver;
import storageManager.FileManager;
import storageManager.GameCatalog;
import storageManager.GameStorageManager;
import undo.UndoForGUI;

/**
 *
 * @author HP
 */
public class SudokuController implements Controllable {
    private DifficultyGenerator difficultyGenerator= new DifficultyGenerator();
    private GameStorageManager storageManager=new GameStorageManager();
    private GameCatalog gameCatalog=new GameCatalog();
    private SudokuBoard current;
    public GameStorageManager getGameStorageManager()
    {
        return storageManager;
    }
    @Override
    public Catalog getCatalog() {
        Catalog catalog=new Catalog();
        catalog.current=gameCatalog.hasIncomplete();
        catalog.allModesExist=gameCatalog.hasAllLevels();
        return catalog;   
    }
    @Override
    public int[][] getGame(char level) throws NotFoundException {
       SudokuBoard board;
       switch(level)
       {
           case 'E'->board=storageManager.loadGame(DifficultyLevel.EASY);
           case 'M'->board=storageManager.loadGame(DifficultyLevel.MEDIUM);
           case 'H'->board=storageManager.loadGame(DifficultyLevel.HARD);
           case 'I'->board=storageManager.loadCurrentGame();
           default->throw new NotFoundException();     
       }
       if(board==null)throw new NotFoundException();
       current=board;
       return board.getArray();
    }

    @Override
    public void driveGames(int[][] source) throws SolutionInvalidException {
        SudokuBoard solvedBoard= new SudokuBoard(copy(source));
        SudokuVerifier verifier=new SudokuVerifier(); 
        ValidationResult validationResult = verifier.validate(solvedBoard);
        if(!validationResult.isValid() || !validationResult.isComplete())
        {
            throw new SolutionInvalidException();
        }
        for(DifficultyLevel d:DifficultyLevel.values())
        {
            SudokuBoard generatedBoard=difficultyGenerator.generate(solvedBoard,d);
            storageManager.saveGame(d, generatedBoard);
        }
        
    }

    @Override
    public boolean[][] verifyGame(int[][] game) {
        SudokuBoard board =new SudokuBoard(copy(game));
        SudokuVerifier verifier=new SudokuVerifier(); 
        ValidationResult validationResult = verifier.validate(board);
        boolean[][] validCheck =new boolean[9][9];
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                validCheck[i][j]=true;
            }
        }
        if(!validationResult.isValid())
        {
            for(String error:validationResult.getErrors())
            {
                String type;
                int index;
                if(error.startsWith("ROW"))
                {
                    type="ROW";
                }else if(error.startsWith("COLUMN"))
                {
                    type="COLUMN";
                }else if(error.startsWith("BOX"))
                {
                    type="BOX";
                }else{
                    continue;
                }
                String[] parts=error.split("[ ,]+");
                index=Integer.parseInt(parts[1])-1;
                if(type.equals("ROW"))
                {
                    for(int z=0;z<9;z++)
                    {
                        validCheck[index][z]=false;
                    }
                }
                if(type.equals("COLUMN"))
                {
                    for(int h=0;h<9;h++)
                    {
                        validCheck[h][index]=false;
                    }
                }
                if(type.equals("BOX"))
                {   int sRow=(index/3)*3;
                    int sCol=(index%3)*3;
                    for(int z=sRow;z<sRow+3;z++)
                    {
                        for(int h=sCol;h<sCol+3;h++)
                        {
                            validCheck[z][h]=false;
                        }
                        
                    }
                } 
                
            }
        }
        return validCheck;
        
    }

    @Override
    public int[][] solveGame(int[][] game) throws InvalidGame {
       SudokuBoard board=new SudokuBoard(copy(game));
       if(board.getEmptyCells().size()!=5)
       {
           throw new InvalidGame();
       }
       SudokuSolver solver=new SudokuSolver(board);
       if(!solver.solve())
       {
           throw new InvalidGame();
       }
       return board.getArray();
    }

    @Override
    public void logUserAction(UserAction userAction) throws IOException {
        File folder=new File("current");
        folder.mkdirs();
        File log=new File(folder,"moves.log");
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(log,true)))
        {
            writer.write("("+userAction.getRow()+","+userAction.getColumn()
            +","+userAction.getNum()+","+userAction.getPrevNum()+")");
            writer.newLine();
        }
        
    }
    private int[][] copy(int[][] source)
    {
        int[][] copy=new int[9][9];
        for(int i=0;i<9;i++)
        {
            System.arraycopy(source[i], 0, copy[i],0, 9);
        }
        return copy;
    }

    @Override
    public int[][] getGameFromLink(String file) throws NotFoundException {
       try{
           SudokuBoard board=FileManager.loadBoard(file);
           return board.getArray();
       }catch(IOException e)
       {
           throw new NotFoundException();
       }
    }
    
    
}
