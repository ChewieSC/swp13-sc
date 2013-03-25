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
 * Datastore access from {@link Input} and {@link Output}. Is used
 * to store results (complete or incomplete). Only temporary.<br />
 * Used in Workflow from Input --> DataStore --> Output.
 *
 * @author Erik
 */
public interface DataStore
{
    /**
     * Adds a single {@link ChessGame} instance to the DataStore.
     * @param cg ChessGame to add.
     */
    public void addSingleGame(ChessGame cg);
    
    /**
     * Adds a list of {@link ChessGame}s to the DataStore.
     * @param cgs List<ChessGame>
     */
    public void addBatchGames(List<ChessGame> cgs);
    
    /**
     * Adds all parsed/converted {@link ChessGame} as a List to the
     * DataStore. Marks the end of the input.
     * @param cgs List<ChessGame>
     */
    public void addAllGames(List<ChessGame> cgs);
    
    /**
     * Returns a single {@link ChessGame} instance from the DataStore.
     * Returned ChessGame is (should be) removed from internal list.<br />
     * Used bei {@link Output}.
     * @return ChessGame or null if empty
     */
    public ChessGame getSingleGame();
    
    /**
     * Returns count {@link ChessGame}s from the DataStore or less if not
     * count ChessGames were saved.
     * @param count Number of ChessGames to return
     * @return List<ChessGame>
     */
    public List<ChessGame> getBachGames(int count);
    
    /**
     * Returns all the saved {@link ChessGame}s from the DataStore.
     * @return List<ChessGame>
     */
    public List<ChessGame> getAllGames();
    
    // probably better to know if still working
    // polling is not soo good?
    // only for async threads ...
    /**
     * Can/Should be used to mark the end of input. After this call
     * no more {@link ChessGame}s are accepted!
     */
    public void finishedInput();
    
    /**
     * Can/Should be used to notify the program that the output has
     * finished and all the saved data (was temporary to begin with)
     * can be deleted. After this call no more {@link ChessGame}s
     * can be acquired!
     */
    public void finishedOutput();
}
