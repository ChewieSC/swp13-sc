/**
 * PGNToRDFConverterStream.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Simple class for use in Vaadin.
 *
 * @author Erik
 *
 */
public class PGNToRDFConverterStream
{
    private InputStream input;
    private OutputStream output;
    
    /**
     * Creates a new PGNToRDFConverter for use in Vaadin.
     * 
     * @param   input   Stream to read from
     * @param   output  Stream to write into
     */
    public PGNToRDFConverterStream(InputStream input, OutputStream output)
    {
        this.input = input;
        this.output = output;
    }
    
    /**
     * Starts converting the input stream into the output stream.
     * 
     * @return  true if successful else false
     */
    public boolean start()
    {
        boolean ret = true;
        // Converter erstellen
        PGNToChessDataModelConverter pgn2cdm = new PGNToChessDataModelConverter();
        pgn2cdm.setInputStream(this.input);
        ChessDataModelToRDFConverter cdm2rdf = new ChessDataModelToRDFConverter();
        
        // abort with false if error
        // parse all games
        ret = ret && pgn2cdm.parse();
        if (! ret)
        {
            return false;
        }
        
        // convert all games
        ret = ret && cdm2rdf.convert(pgn2cdm.getGames());
        if (! ret)
        {
            return false;
        }
        
        // Write data
        ret = ret && cdm2rdf.write(this.output);
                
        return ret;
    }
}
