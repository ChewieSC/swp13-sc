/**
 * FileOutput.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.output;

import de.uni_leipzig.informatik.swp13_sc.converter.DataStore;
import de.uni_leipzig.informatik.swp13_sc.converter.output.Output;

import java.lang.NullPointerException;

/**
 *
 *
 * @author Erik
 *
 */
public abstract class FileOutput extends Output
{
    /**
     * filename
     */
    private String filename;
    
    /**
     * @param d
     * @param filename
     * @throws NullPointerException
     */
    public FileOutput(DataStore d, String filename)
        throws NullPointerException
    {
        super(d);
        // TODO: add & check arg filename
        this.filename = filename;
    }
    
    // TODO: some other Constructors
    
    // TODO: ouput config for outputting parts to different files
}
