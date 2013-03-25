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

/**
 *
 *
 * @author Erik
 *
 */
public abstract class FileInput extends Input
{
    /**
     * filename
     */
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
    
    /**
     * @param d
     */
    public FileInput(DataStore d)
    {
        super(d);
    }
    
    /**
     * @param filename
     */
    public void setFilename(String filename)
    {
        this.filename = filename;
    }
    
    // TODO: some other Constructors
    // TODO: check if constructor of Input can be called - it should be non visible
    
    /**
     * @return
     */
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
        catch (Exception e)
        {
        	return false;
        }
        return true;
    }
    
    // ? where is it closed ?
    /**
     * @return
     * @throws FileNotFoundException
     */
    protected BufferedReader getInputFileReader()
        throws FileNotFoundException
    {
        if (this.fileExists())
        {
        	return new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(this.filename))));
        }
        else
        {
        	throw new FileNotFoundException("File " + ((this.filename==null)?"[null]":this.filename) + " does not exist.");
        }
    }
}
