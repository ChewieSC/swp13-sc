/**
 * Input.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.input;

import de.uni_leipzig.informatik.swp13_sc.converter.DataStore;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.lang.NullPointerException;
import java.lang.RuntimeException;
import java.util.List;

public abstract class Input
{
    private final DataStore datastore;

    public Input(DataStore d)
        throws NullPointerException
    {
        if (d == null)
        {
           throw new NullPointerException("No DataStore d!");
        }
        this.datastore = d;
    }
    
    public DataStore getDataStore()
    {
        return this.datastore;
    }
    
    // converts all
    public abstract List<ChessGame> convert();
    
    // threaded, should add converted parts to Converter c
    public abstract void beginConvert();
    
    // e.g.: "pgn"
    //   or: "file.pgn", "db.pgn", ...
    public static String getFormat()
       throws RuntimeException
    {
        throw new RuntimeException("Method not yet implemented.");
    }
    
    // TODO: needed?
    public abstract String getFormat();
}
