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

import java.lang.Runnable;
import java.util.ArrayList;
import java.util.List;

// TODO: make it final?
public final class PGNFileInput extends FileInput
    implements Runnable
{
    // should be set by convert-methods
    private boolean isAsync;
    private boolean isConverting;
    private List<ChessGame> gameList;
    private Thread tc;
    
    
    public PGNFileInput(DataStore d)
    {
        super(d);
    }
    
    
    private void addGame(ChessGame cg)
    {
        // TODO: async?
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
    
    private void converting()
    {
        // TODO: add code for converting
        //
        // code for parsing pgns
    }
    
    @Override
    public void run()
    {
        converting();
        
        this.isConverting = false;
        //this.getConverter().finishedInput();
    }
    
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

    @Override
    public void beginConvert()
    {
        this.isAsync = true;
        this.isConverting = true;
        this.gameList = new ArrayList<ChessGame>(); // ?
        
        tc = new Thread(this);
        tc.start();
    }
    
    @Override
    public String getFormat()
    {
        return "pgn";
    }
}
