/**
 * Output.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.output;

import de.uni_leipzig.informatik.swp13_sc.converter.DataStore;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.lang.NullPointerException;
import java.lang.RuntimeException;
import java.util.List;

/**
 *
 *
 * @author Erik
 *
 */
public abstract class Output
{
    /**
     * datastore
     */
    private final DataStore datastore;
    
    /**
     * @param d
     * @throws NullPointerException
     */
    public Output(DataStore d)
        throws NullPointerException
    {
        if (d == null)
        {
           throw new NullPointerException("No DataStore d!");
        }
        this.datastore = d;
    }
    
    /**
     * @return
     */
    public DataStore getDataStore()
    {
        return this.datastore;
    }
    
    // TODO: add config for outputting parts only
    //       see FileOutput.java
    
    // async
    /**
     * 
     */
    public abstract void startOutput();
    
    /**
     * @param games
     */
    public abstract void outputPart(List<ChessGame> games);
    
    // sync, all
    /**
     * @param games
     */
    public abstract void outputAll(List<ChessGame> games);
    
    /**
     * Returns the supported Format.<br />
     * e. g. "pgn" or<br />
     * "file.pgn", "db.pgn", ...
     * @return Format as String
     */
    public abstract String getFormat();
}
