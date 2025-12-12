/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

import model.Catalog;
import exceptions.*;
import java.io.IOException;
import model.DifficultyLevel;
import model.Game;


/**
 *
 * @author HP
 */
public interface Viewable {
    Catalog getCatalog();
    Game getGame(DifficultyLevel level)throws NotFoundException;
    void driveGames(Game source)throws SolutionInvalidException;
    String verifyGame(Game game);
    int[] solveGame(Game game)throws InvalidGame;
    void logUserAction(String userAction)throws IOException;
}
