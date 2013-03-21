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
    
    public FileInput(DataStore d, String filename)
        throws NullPointerException
    {
        super(d);
        // TODO: add & check arg filename
        this.filename = filename;
    }
    
    // TODO: some other Constructors
    // TODO: check if constructor of Input can be called - it should be non visible
}
