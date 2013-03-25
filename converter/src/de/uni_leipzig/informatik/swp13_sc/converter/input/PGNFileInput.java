/**
 * PGNFileInput.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.input;

import de.uni_leipzig.informatik.swp13_sc.converter.DataStore;
import de.uni_leipzig.informatik.swp13_sc.converter.input.FileInput;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigure;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPosition;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessResult;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.Runnable;
import java.util.ArrayList;
import java.util.List;

// TODO: make it final?
/**
 * Reads and parses a PGN-File and returns a List of {@link ChessGame}s.
 *
 * @author Erik
 *
 */
public final class PGNFileInput extends FileInput
    implements Runnable
{
    // should be set by convert-methods
    /**
     * isAsync
     */
    private boolean isAsync;
    /**
     * isConverting
     */
    private boolean isConverting;
    /**
     * gameList
     */
    private List<ChessGame> gameList;
    /**
     * tc
     */
    private Thread tc;
    
    
    /**
     * @param d
     */
    public PGNFileInput(DataStore d)
    {
        super(d);
    }
    
    
    /**
     * Internal.<br />
     * Adds a {@link ChessGame} to the DataStore.
     * @param cg ChessGame to add.
     */
    private void addGame(ChessGame cg)
    {
        if (isAsync)
        {
            // TODO: add single or batch?
            this.getDataStore().addSingleGame(cg);
            // this.getDataStore().addBatchGames(gameBatch);
        }
        else
        {
            this.gameList.add(cg);
        }
    }
    
    /**
     * 
     */
    private void converting()
    {
        if (! super.fileExists())
        {
            return;
        }
        
        String strLine = null;
        BufferedReader br = null;
        try
        {
            br = super.getInputFileReader();
            
            while((strLine = br.readLine()) != null)
            {
                
            }
        }
        catch (FileNotFoundException e)
        {
            // ?
        }
        catch (IOException e)
        {
            // ?
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (IOException e)
            {
                // ?
            }
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        converting();
        
        this.isConverting = false;
        this.getDataStore().finishedInput();
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.input.Input#convert()
     */
    @Override
    public List<ChessGame> convert()
    {
        this.isAsync = false;
        this.isConverting = true;
        this.gameList = new ArrayList<ChessGame>();
        
        converting();
        
        //this.getDataStore().addAllGames(this.gameList);
        //this.getConverter().finishedInput();
        this.isConverting = false;
        
        return this.gameList;
    }

    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.input.Input#beginConvert()
     */
    @Override
    public void beginConvert()
    {
        this.isAsync = true;
        this.isConverting = true;
        this.gameList = new ArrayList<ChessGame>(); // ?
        
        tc = new Thread(this);
        tc.start();
    }
    
    /* (non-Javadoc)
     * @see de.uni_leipzig.informatik.swp13_sc.converter.input.Input#getFormat()
     */
    @Override
    public String getFormat()
    {
        return "pgn";
    }
}
