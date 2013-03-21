/**
 * Output.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.output;

import de.uni_leipzig.informatik.swp13_sc.converter.DataStore;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.lang.NullPointerException;
import java.lang.RuntimeException;
import java.util.List;

public abstract class Output
{
    private final DataStore datastore;
    
    public Output(DataStore d)
        throws NullPointerException
    {
        if (d == null)
        {
           throw new NullPointerException("No DataStore d!");
        }
        this.datastore = d;
    }
    
    public Converter getDataStore()
    {
        return this.datastore;
    }
    
    // TODO: add config for outputting parts only
    //       see FileOutput.java
    
    // async
    public abstract void outputPart(List<ChessGame> games);
    
    // sync, all
    public abstract void outputAll(List<ChessGame> games);
    
    // e.g.: "pgn"
    //   or: "file.pgn", "db.pgn", ...
    public static String getFormat()
       throws RuntimeException
    {
        throw new RuntimeException("Method not yet implemented.");
    }
}
