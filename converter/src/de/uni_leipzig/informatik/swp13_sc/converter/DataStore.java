/**
 * Converter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.util.List;

public interface DataStore
{
    public void addSingleGame(ChessGame cg);
    
    public void addBatchGames(List<ChessGame> cgs);
    
    public void addAllGames(List<ChessGame> cgs);
    
    public ChessGame getSingleGame();
    
    public List<ChessGame> getBachGames(int count);
    
    public List<ChessGame> getAllGames();
}
