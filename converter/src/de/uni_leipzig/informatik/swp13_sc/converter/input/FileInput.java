/**
 * FileInput.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.input;

import de.uni_leipzig.informatik.swp13_sc.converter.DataStore;
import de.uni_leipzig.informatik.swp13_sc.converter.input.Input;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

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
    
    public boolean fileExists()
    {
        try
        {
            File f = new File(this.filename);
            if (! f.exists())
            {
                return false;
            }
            if (f.isDirectory())
            {
                return false;
            }
        }
        catch (NullPointerException e)
        {
            return false;
        }
        return true;
    }
    
    // ? where is it closed ?
    protected BufferedReader getInputFileReader()
        throws FileNotFoundException
    {
        return new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(this.filename))));
    }
}
