/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

import exceptions.*;
import java.io.IOException;
import model.Catalog;
import model.UserAction;

/**
 *
 * @author HP
 */
public interface Controllable {
    Catalog getCatalog();
    int[][] getGame(char level)throws NotFoundException;
    void driveGames(int[][] source)throws SolutionInvalidException;
    boolean[][] verifyGame(int[][] game);
    int[][] solveGame(int[][] game)throws InvalidGame;
    void logUserAction(UserAction userAction)throws IOException;
    int[][] getGameFromLink(String file)throws NotFoundException;
}
