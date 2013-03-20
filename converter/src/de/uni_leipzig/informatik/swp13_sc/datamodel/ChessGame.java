/**
 * ChessGame.java
 */

package de.uni_leipzig.informatik.swt13_sc.datamodel;

import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessPlayer;
import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessGame
{
   private ChessPlayer black;
   private ChessPlayer white; 
   private ChessResult result;
   private List<ChessMove> moves = new ArrayList<ChessMove>();
   private String event;
   private String round;
   private String location;
   
   // TODO: setter package only - visibility ?
   
   public ChessGame()
   {
       this(null, null);
   }
   
   public ChessGame(ChessPlayer white, ChessPlayer black)
   {
       this(white, black, null, null, "", "", ""):
   }
   
   // TODO: add some constructors
   
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
   
   public ChessPlayer getWhite()
   {
       return this.white;
   }
   
   public void setWhite(ChessPlayer white)
   {
       this.white = white;
   }
   
   public ChessPlayer getBlack()
   {
       return this.black;
   }
   
   public void setBlack()
   {
       this.black = black;
   }
   
   public ChessResult getResult()
   {
       return this.result;
   }
   
   public void setResult(ChessResult result)
   {
       this.result = result;
   }
   
   public List<ChessMove> getMoves()
   {
       return Collections.unmodifiableList(this.moves);
   }
   
   public void addMoves(List<ChessMove> moves)
   {
       if (moves != null)
       {
           this.moves.addAll(moves)
       }
   }
   
   public void addMove(ChessMove move)
   {
       // TODO: check necessary?
       this.moves.add(move);
   }
   
   public String getEvent()
   {
       return this.event;
   }
   
   public void setEvent(String event)
   {
       this.event = event;
   }
   
   public String getRound()
   {
       return this.round;
   }
   
   public void setRound(String round)
   {
       this.round = round;
   }
   
   public String getLocation()
   {
       return this.location;
   }
   
   public void setLocation(String location)
   {
       this.location = location;
   }
   
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
