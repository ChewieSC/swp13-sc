/**
 * FileInput.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.input;

import de.uni_leipzig.informatik.swp13_sc.converter.DataStore;
import de.uni_leipzig.informatik.swp13_sc.converter.input.Input;

import java.lang.NullPointerException;

public abstract class FileInput extends Input
{
    private String filename;
    
    /*
    // for adding to converter not so good ...
    public FileInput(DataStore d, String filename)
        throws NullPointerException
    {
        super(d);
        // TODO: add & check arg filename
        this.filename = filename;
    }
    */
    
    public FileInput(DataStore d)
    {
        super(d);
    }
    
    public void setFilename(String filename)
    {
        this.filename = filename;
    }
    
    // TODO: some other Constructors
    // TODO: check if constructor of Input can be called - it should be non visible
}
