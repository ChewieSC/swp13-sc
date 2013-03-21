/**
 * PGNFileInput.java
 */

package de.uni_leipzig.informatik.swp13_sc.converter.input;

import de.uni_leipzig.informatik.swp13_sc.converter.input.FileInput;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigure;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessGame;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPosition;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessResult;

import java.lang.Runnable;

// TODO: make it final?
public final class PGNFileInput extends FileInput
    implements Runnable
{
    // should be set by convert-methods
    private boolean isAsync;
    private boolean isConverting;
	private List<ChessGame> gameList;
    private Thread tc;
    
    private addGame(ChessGame cg)
    {
        // TODO: async?
		if (isAsync)
		{
		    // TODO: add single or batch?
			this.getConverter().addGame(cg);
			// this.getConverter().addBatchGames(gameBatch);
		}
		else
		{
		    this.gameList.add(cg);
		}
    }
    
	private converting()
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
		this.getConverter().finishedInput();
	}
    
    @Override
	public List<ChessGame> convert()
	{
	    this.isAsync = false;
		this.isConverting = true;
        this.gameList = new ArrayList<ChessGame>():
		
		converting()
        
		this.getConverter().addAllGames(this.gameList);
		this.getConverter().finishedInput();
		this.isConverting = false;
	}

	@Override
	public void beginConvert()
	{
	    this.isAsync = true;
		this.isConverting = true;
        
        tc = new Thread(this);
		tc.start()
	}
    
	@Override
    public String getFormat()
	{
	    return PGNFileInput.getFormat();
	}
    
	@Override
	public static String getFormat()
	{
	    return "pgn";
	    //return "file.pgn"
	}
}
