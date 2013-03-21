/**
 * Input.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.input;

import de.uni_leipzig.informatik.swp13_sc.converter.Converter;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.lang.NullPointerException;
import java.lang.RuntimeException;
import java.util.List;

public abstract class Input
{
    private final Converter converter;
    
    public Input(Converter c)
        throws NullPointerException
    {
        if (c == null)
        {
           throw new NullPointerException("No Converter c!");
        }
        this.converter = c;
    }
    
    public Converter getConverter()
    {
        return this.converter;
    }
    
    // converts all
    public abstract List<ChessGame> convertFile();
    
    // threaded, should add converted parts to Converter c
    public abstract void beginConvert();
    
    // e.g.: "pgn"
    //   or: "file.pgn", "db.pgn", ...
    public static String getFormat()
       throws RuntimeException
    {
        throw new RuntimeException("Method not yet implemented.");
    }
}
