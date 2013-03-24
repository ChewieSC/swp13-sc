/**
 * Converter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.converter.DataStore;
import de.uni_leipzig.informatik.swp13_sc.converter.input.Input;
import de.uni_leipzig.informatik.swp13_sc.converter.output.Output;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 *
 * @author Erik
 *
 */
public abstract class Converter
    implements DataStore
{
    // threadsafe datastore (concurrent, ...)
    /**
     * gameList
     */
    private Vector<ChessGame> gameList = new Vector<ChessGame>();
    // ConcurrentLinkedQueue<E> ?
    //
    
    // selected In-/Output classes for converting
    /**
     * input
     */
    private Input input;
    /**
     * output
     */
    private Output output;
    
    // TODO: make it static?? - no
    //       or simple List?
    /**
     * inputFormats
     */
    private Map<String, Input> inputFormats = new HashMap<String, Input>();
    /**
     * outputFormats
     */
    private Map<String, Output> outputFormats = new HashMap<String, Output>();
    
    /**
     * Intern.<br />
     * isAsync marks the workflow.
     */
    private boolean isAsync;
    
    /**
     * Constructor
     */
    public Converter()
    {
        // TODO: ?
    }
    
    
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#addSingleGame(de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame)
     */
    @Override
    public void addSingleGame(ChessGame cg)
    {
        // TODO: add Game to datastore
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#addBatchGames(java.util.List)
     */
    @Override
    public void addBatchGames(List<ChessGame> cgs)
    {
        // TODO: add Games
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#addAllGames(java.util.List)
     */
    @Override
    public void addAllGames(List<ChessGame> cgs)
    {
        // TODO: add Games
        //       called when finished sync
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#getSingleGame()
     */
    @Override
    public ChessGame getSingleGame()
    {
        // TODO: return next Game & remove
        return null;
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#getBachGames(int)
     */
    @Override
    public List<ChessGame> getBachGames(int count)
    {
        // TODO: return Games
        return null;
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#getAllGames()
     */
    @Override
    public List<ChessGame> getAllGames()
    {
        // TODO: return Games & clear list
        return null;
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#finishedInput()
     */
    @Override
    public void finishedInput()
    {
        // TODO: add
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#finishedOutput()
     */
    @Override
    public void finishedOutput()
    {
        // TODO: add
    }
    
    
    
    // TODO: make protected?
    /**
     * @param input
     */
    public void addInput(Input input)
    {
        // TODO: needs try catch ?
        this.inputFormats.put(input.getFormat(), input);
    }
    
    /**
     * @param output
     */
    public void addOutput(Output output)
    {
        this.outputFormats.put(input.getFormat(), output);
    }
    
    
    /**
     * @param inputFormat
     * @return
     */
    public boolean supportsInput(String inputFormat)
    {
        return this.inputFormats.containsKey(inputFormat);
    }
    
    /**
     * @param outputFormat
     * @return
     */
    public boolean supportsOutput(String outputFormat)
    {
        return this.outputFormats.containsKey(outputFormat);
    }
    
    /**
     * @return
     */
    public List<String> getInputFormats()
    {
        ArrayList<String> formats = new ArrayList<String>();
        for (String format : this.inputFormats.keySet())
        {
            formats.add(format);
        }
        return formats;
    }
    
    /**
     * @return
     */
    public List<String> getOutputFormats()
    {
        ArrayList<String> formats = new ArrayList<String>();
        for (String format : this.outputFormats.keySet())
        {
            formats.add(format);
        }
        return formats;
    }
}
