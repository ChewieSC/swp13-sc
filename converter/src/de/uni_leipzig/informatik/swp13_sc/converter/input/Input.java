/**
 * Input.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.input;

import de.uni_leipzig.informatik.swp13_sc.converter.DataStore;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.lang.NullPointerException;
import java.util.List;

/**
 *
 *
 * @author Erik
 *
 */
public abstract class Input
{
    /**
     * datastore
     */
    private final DataStore datastore;

    /**
     * @param d
     * @throws NullPointerException
     */
    public Input(DataStore d)
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
    
    /**
     * Converts the whole Input and returns it eventually.
     * @return
     */
    public abstract List<ChessGame> convert();
    
    // threaded, should add converted parts to Converter c
    /**
     * Converts in an extra thread.
     * Adds converted parts to the {@link DataStore}.
     * Finishes with {@link DataStore#finishedInput()}.
     */
    public abstract void beginConvert();
    
    /**
     * Returns the supported Format.<br />
     * e. g. "pgn" or<br />
     * "file.pgn", "db.pgn", ...
     * @return Format as String
     */
    public abstract String getFormat();
}
