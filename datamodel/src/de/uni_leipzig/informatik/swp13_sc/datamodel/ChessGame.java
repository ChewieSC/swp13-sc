/**
 * ChessGame.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 * @author Erik
 *
 */
public class ChessGame
{
	/**
	 * black
	 */
	private ChessPlayer black;
	/**
	 * white
	 */
	private ChessPlayer white; 
	/**
	 * result
	 */
	private ChessResult result;
	/**
	 * moves
	 */
	private List<ChessMove> moves = new ArrayList<ChessMove>();
	/**
	 * event
	 */
	private String event;
	/**
	 * round
	 */
	private String round;
	/**
	 * location
	 */
	private String location;

	// TODO: setter package only - visibility ?

	public ChessGame()
	{
		this(null, null);
	}

	public ChessGame(ChessPlayer white, ChessPlayer black)
	{
		this(white, black, null, null, "", "", "");
	}

	// TODO: add some constructors
	// TODO: add Builder ?

	/**
	 * @param white
	 * @param black
	 * @param result
	 * @param moves
	 * @param location
	 * @param event
	 * @param round
	 */
	public ChessGame(ChessPlayer white, ChessPlayer black, ChessResult result,
			List<ChessMove> moves, String location, String event, String round)
	{
		this.white = white;
		this.black = black;
		this.result = result;
		if (moves != null)
		{
			this.moves.addAll(moves);
		}
		this.location = location;
		this.event = event;
		this.round = round;
	}

	/**
	 * @return
	 */
	public ChessPlayer getWhite()
	{
		return this.white;
	}

	/**
	 * @param white
	 */
	public void setWhite(ChessPlayer white)
	{
		this.white = white;
	}

	/**
	 * @return
	 */
	public ChessPlayer getBlack()
	{
		return this.black;
	}

	/**
	 * @param black
	 */
	public void setBlack(ChessPlayer black)
	{
		this.black = black;
	}

	/**
	 * @return
	 */
	public ChessResult getResult()
	{
		return this.result;
	}

	/**
	 * @param result
	 */
	public void setResult(ChessResult result)
	{
		this.result = result;
	}

	/**
	 * @return
	 */
	public List<ChessMove> getMoves()
	{
		return Collections.unmodifiableList(this.moves);
	}

	/**
	 * @param moves
	 */
	public void addMoves(List<ChessMove> moves)
	{
		if (moves != null)
		{
			this.moves.addAll(moves);
		}
	}

	/**
	 * @param move
	 */
	public void addMove(ChessMove move)
	{
		if (move != null)
		{
			this.moves.add(move);
		}		
	}

	/**
	 * @return
	 */
	public String getEvent()
	{
		return this.event;
	}

	/**
	 * @param event
	 */
	public void setEvent(String event)
	{
		this.event = event;
	}

	/**
	 * @return
	 */
	public String getRound()
	{
		return this.round;
	}

	/**
	 * @param round
	 */
	public void setRound(String round)
	{
		this.round = round;
	}

	/**
	 * @return
	 */
	public String getLocation()
	{
		return this.location;
	}

	/**
	 * @param location
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}

	/**
	 * Returns the last {@link ChessMove} of this ChessGame.
	 * @return ChessMove or null if no moves are saved.
	 */
	public ChessMove getLastMove()
	{
		if (this.moves.size() > 0)
		{
			return this.moves.get(this.moves.size() - 1);
		}
		else
		{
			return null;
		}
	}

	/**
	 * @return
	 */
	public String getFEN()
	{
		ChessMove m = this.getLastMove();
		if (m != null)
		{
			return m.getFEN();
		}
		else
		{
			// TODO: return null?
					return "";
		}
	}

	/**
	 * @return
	 */
	public String getGBR()
	{
		ChessMove m = this.getLastMove();
		if (m != null)
		{
			return m.getGBR();
		}
		else
		{
			// TODO: see above.
			return "";
		}
	}
}
