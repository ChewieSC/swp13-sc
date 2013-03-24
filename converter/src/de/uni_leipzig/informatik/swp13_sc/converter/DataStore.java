/**
 * Converter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.util.List;

// TODO: need  InputDataStore,
//            OutputDataStore.
//       for Input & Output separate!?
/**
 *
 *
 * @author Erik
 *
 */
public interface DataStore
{
    /**
     * @param cg
     */
    public void addSingleGame(ChessGame cg);
    
    /**
     * @param cgs
     */
    public void addBatchGames(List<ChessGame> cgs);
    
    /**
     * @param cgs
     */
    public void addAllGames(List<ChessGame> cgs);
    
    /**
     * @return
     */
    public ChessGame getSingleGame();
    
    /**
     * @param count
     * @return
     */
    public List<ChessGame> getBachGames(int count);
    
    /**
     * @return
     */
    public List<ChessGame> getAllGames();
    
    // probably better to know if still working
    // polling is not soo good?
    // only for async threads ...
    /**
     * 
     */
    public void finishedInput();
    
    /**
     * 
     */
    public void finishedOutput();
}
