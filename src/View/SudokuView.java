/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import controller.Controllable;
import controller.Viewable;
import exceptions.InvalidGame;
import exceptions.NotFoundException;
import exceptions.SolutionInvalidException;
import java.io.IOException;
import model.Catalog;
import model.DifficultyLevel;
import static model.DifficultyLevel.EASY;
import model.Game;
import model.SudokuBoard;
import model.UserAction;

/**
 *
 * @author HP
 */
public class SudokuView implements Viewable {
    private Controllable controller;
    public SudokuView(Controllable controller)
    {
        this.controller=controller;
    }
    @Override
    public Catalog getCatalog() {
        return controller.getCatalog();
    }

    @Override
    public Game getGame(DifficultyLevel level) throws NotFoundException {
        char l;
        switch(level)
        {
            case EASY->l='E';
            case MEDIUM ->l='M';
            case HARD ->l='H';
            default->throw new NotFoundException();   
        }
        int[][] boardArray=controller.getGame(l);
        SudokuBoard board=new SudokuBoard(boardArray);
        return new Game(board,level);
    }

    @Override
    public void driveGames(Game source) throws SolutionInvalidException {
        controller.driveGames(source.getBoard().getArray());
    }

    @Override
    public String verifyGame(Game game) {
        boolean[][] check=controller.verifyGame(game.getBoard().getArray());
        for(int i=0;i<9;i++)
        {
            for(int j=0;j<9;j++)
            {
                if(!check[i][j])
                {
                    return "INVALID";
                }
            }
        }
        return "VALID";
    }

    @Override
    public int[] solveGame(Game game) throws InvalidGame {
       int[][] solvedGame= controller.solveGame(game.getBoard().getArray());
       int[] array=new int[81];
       int z=0;
       for(int i=0;i<9;i++)
       {
           for(int j=0;j<9;j++)
           {
               array[z++]=solvedGame[i][j];
           }
       }
       return array;
    }

    @Override
    public void logUserAction(String userAction) throws IOException {
        String[] parts =userAction.split(",");
        int row=Integer.parseInt(parts[0]);
        int col=Integer.parseInt(parts[1]);
        int num=Integer.parseInt(parts[2]);
        int prevNum=Integer.parseInt(parts[3]);
        controller.logUserAction(new UserAction(row,col,num,prevNum));
        
    }
    
}
