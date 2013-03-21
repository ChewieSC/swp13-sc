/**
 * Converter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.converter.input.Input;
import de.uni_leipzig.informatik.swp13_sc.converter.output.Output;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public abstract class Converter
{
    // threadsafe datastore (concurrent, ...)
    private Vector<ChessGame> gameList = new Vector<ChessGame>();
    // ConcurrentLinkedQueue<E> ?
    //
    
    // selected In-/Output classes for converting
    private Input input;
    private Output output;
    
    // TODO: make it static?? - no
    //       or simple List?
    private Map<String, Input> inputFormats = new HashMap<String, Input>();
    private Map<String, Output> outputFormats = new HashMap<String, Output>();
    
    private boolean isAsync;
    
    public Converter()
    {
        // TODO: ?
    }
    
    
    
    public void addSingleGame(ChessGame cg)
    {
        // TODO: add Game to datastore
    }
    
    public void addBatchGames(List<ChessGame> cgs)
    {
        // TODO: add Games
    }
    
    public void addAllGames(List<ChessGame> cgs)
    {
        // TODO: add Games
        //       called when finished sync
    }
    
    public ChessGame getSingleGame()
    {
        // TODO: return next Game & remove
    }
    
    public List<ChessGame> getBachGames(int count)
    {
        // TODO: return Games
    }
    
    public List<ChessGame> getAllGames()
    {
        // TODO: return Games & clear list
    }
    
    public void finishedInput()
    {
        // TODO:
    }
    
    public void finishedOutput()
    {
        // TODO:
    }
    
    
    
    // TODO: make protected?
    public void addInput(Input input)
    {
        // TODO: needs try catch ?
        this.inputFormats.put(input.getFormat(), input);
    }
    
    public void addOutput(Output output)
    {
        this.outputFormats.put(input.getFormat(), output);
    }
    
    public List<String> getInputFormats()
    {
        ArrayList<String> formats = new ArrayList<String>();
        for (String format : this.inputFormats.keySet())
        {
            formats.add(format);
        }
        return formats;
    }
    
    public boolean supportsInput(String inputFormat)
    {
        return this.inputFormats.containsKey(inputFormat);
    }
    
    public boolean supportsOutput(String outputFormat)
    {
        return this.outputFormats.containsKey(outputFormat);
    }
    
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
