/**
 * Converter.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter;

import de.uni_leipzig.informatik.swp13_sc.converter.DataStore;
import de.uni_leipzig.informatik.swp13_sc.converter.input.Input;
import de.uni_leipzig.informatik.swp13_sc.converter.output.Output;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

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
     * List (Queue) of all Games. Only used with threads.
     */
	private ConcurrentLinkedQueue<ChessGame> gameList = new ConcurrentLinkedQueue<ChessGame>();
    //private Vector<ChessGame> gameList = new Vector<ChessGame>();


    // selected In-/Output classes for converting
    /**
     * input
     */
    private Input input = null;
    /**
     * output
     */
    private Output output = null;
    
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
    private boolean isAsync = false;
    /**
     * Intern<br />isWorking
     */
    private boolean isWorking = false;
    
    /**
     * Intern.<br />finishedInput
     */
    private boolean finishedInput = false;
    /**
     * Intern.<br />finishedOutput
     */
    private boolean finishedOutput = false;
    
    /**
     * Constructor
     */
    public Converter()
    {
        // TODO: ?
    }
    
    
    /**
     * Starts the asynchronous conversion process.
     * @return true if successfully started else false
     */
    public boolean beginConvert()
    {
    	// right application of synchronized?
    	synchronized (this) {
			if ((! this.isWorking) && (this.hasInput()) && (this.hasOutput()))
	    	{
	    		this.isWorking = true;
	    		this.isAsync = true;
	    	}
			else
			{
				return false;
			}
		}
    	if (! this.isWorking)
    	{
    		this.input.beginConvert();
    		this.output.startOutput();
    		return true;
    	}
    	return false;
    }
    
    /**
     * Starts the synchronous conversion process
     */
    public void convert()
    {
    	synchronized (this) {
    		if ((! this.isWorking) && (this.hasInput()) && (this.hasOutput()))
	    	{
	    		this.isWorking = true;
	    		this.isAsync = false;
	    	}
			else
			{
				return;
			}
		}
    	if (! this.isWorking)
    	{
    		this.addAllGames(this.input.convert());
    		this.output.outputAll(this.getAllGames());
    	}
    }
    
    
    
    //
    // DataStore interface implementation
    //
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#addSingleGame(de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame)
     */
    @Override
    public void addSingleGame(ChessGame cg)
    {
    	if (! this.finishedInput)
    	{
    		this.gameList.offer(cg);
    	}
    }    
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#addBatchGames(java.util.List)
     */
    @Override
    public void addBatchGames(List<ChessGame> cgs)
    {
        for (ChessGame cg : cgs)
        {
        	this.addSingleGame(cg);
        }
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#addAllGames(java.util.List)
     */
    @Override
    public void addAllGames(List<ChessGame> cgs)
    {
        this.addBatchGames(cgs);
        this.finishedInput();
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#getSingleGame()
     */
    @Override
    public ChessGame getSingleGame()
    {
    	if (this.finishedOutput)
    	{
    		return null;
    	}
    	else
    	{
    		return this.gameList.poll();
    	}        
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#getBachGames(int)
     */
    @Override
    public List<ChessGame> getBachGames(int count)
    {
        ArrayList<ChessGame> cgs = new ArrayList<ChessGame>();
        ChessGame cg;
        for (int i = count; i > 0; i --)
        {
        	if ((! this.gameList.isEmpty()) && ((cg = this.getSingleGame()) != null))
        	{
        		cgs.add(cg);
        	}
        	else
        	{
        		break;
        	}
        }
        return cgs;
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#getAllGames()
     */
    @Override
    public List<ChessGame> getAllGames()
    {
    	if (this.finishedOutput) {
    		return new ArrayList<ChessGame>();
    	}
    	else
    	{
    		// TODO: Test !
            return new ArrayList<ChessGame>(Arrays.asList((ChessGame[]) this.gameList.toArray()));
            
            // TODO: finished Output or are some games later added ?
    	}    	
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#finishedInput()
     */
    @Override
    public void finishedInput()
    {
        this.finishedInput = true;
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.DataStore#finishedOutput()
     */
    @Override
    public void finishedOutput()
    {
        this.finishedOutput = true;
    }
    
    
    /**
     * Returns true if the conversion process has started.
     * @return true if working
     */
    public boolean isWorking()
    {
    	return this.isWorking;
    }
    
    /**
     * Returns true if working in Threads.
     * @return true if asynchronous
     */
    public boolean isAsync()
    {
    	return this.isAsync;
    }
    
    //
    // Input/Output module coordination
    //
    
    /**
     * Sets the curren {@link Input} module if not already set.
     * @param input Module used for input. Parsing/Converting data to
     * memory structures.
     * @return true if changed, false if not.
     */
    public boolean setInput(Input input)
    {
    	if (this.input == null)
    	{
    		if (input != null)
    		{
    			this.input = input;
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Sets the current {@link Output} module if not already set.
     * @param output Module used for outputting data.
     * @return true if changed, false if already set or error occured.
     */
    public boolean setOutput(Output output)
    {
    	if (this.output == null)
    	{
    		if (output != null)
    		{
    			this.output = output;
    			return true;
    		}
    	}
    	return false;
    }
    
    
    /**
     * Returns the active {@link Input} module.
     * @return Input or null if not set.
     */
    public Input getInput()
    {
    	return this.input;
    }
    
    /**
     * Returns the active {@link Output} module.
     * @return Output or null if not set.
     */
    public Output getOutput()
    {
    	return this.output;
    }
    
    /**
     * Returns a value which determines if this converter instance
     * has selected an input module.
     * @return true if already selected else false
     */
    public boolean hasInput()
    {
    	return this.getInput() != null;
    }
    /**
     * Returns a value which determines if this converter instance
     * has selected an output module.
     * @return true if already selected else false
     */
    public boolean hasOutput()
    {
    	return this.getOutput() != null;
    }
    
    
    //
    // Input/Output dataformats 
    //
    
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
    
    
    /**
     * Returns an Input module for the inputFormat
     * @param inputFormat Actual Format.
     * @return Input or null if not found.
     */
    protected Input getInput(String inputFormat)
    {
    	return this.inputFormats.get(inputFormat);
    }
    
    /**
     * Returns an Output module for the outputFormat
     * @param outputFormat Actual Format.
     * @return Output or null if not found.
     */
    protected Output getOutput(String outputFormat)
    {
    	return this.outputFormats.get(outputFormat);
    }
}
