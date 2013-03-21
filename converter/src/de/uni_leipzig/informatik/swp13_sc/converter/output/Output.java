/**
 * Output.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.output;

import de.uni_leipzig.informatik.swp13_sc.converter.Converter;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.lang.NullPointerException;
import java.lang.RuntimeException;
import java.util.List;

public abstract class Output
{
    private final Converter converter;
    
    public Output(Converter c)
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
