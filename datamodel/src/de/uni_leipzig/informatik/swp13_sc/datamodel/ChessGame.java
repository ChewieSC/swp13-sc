/**
 * ChessGame.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;

import java.util.ArrayList;
import java.util.List;

public class ChessGame
{
    private final ChessPlayer whitePlayer;
    private final ChessPlayer blackPlayer;
    private final String event;
    private final String location;
    private final String date;
    private final String round;
    private final String result;
    private final List<ChessMove> moves;
    
    public ChessGame(Builder builder)
    {
        this.whitePlayer = builder.whitePlayer;
        this.blackPlayer = builder.blackPlayer;
        this.event       = builder.event;
        this.location    = builder.location;
        this.date        = builder.date;
        this.round       = builder.round;
        this.result      = builder.result;
        this.moves       = new ArrayList<ChessMove>();
        this.moves.addAll( builder.moves);
    }
    
    public ChessPlayer getWhitePlayer()
    {
        return this.whitePlayer;
    }
    
    public ChessPlayer getBlackPlayer()
    {
        return this.blackPlayer;
    }
    
    public String getEvent()
    {
        return this.event;
    }
    
    public String getLocation()
    {
        return this.location;
    }
    
    public String getDate()
    {
        return this.date;
    }
    
    public String getRound()
    {
        return this.round;
    }
    
    public String getResult()
    {
        return this.result;
    }
    
    public List<ChessMove> getMoves()
    {
        return this.moves;
    }
    
    
    public static class Builder
    {
        private ChessPlayer whitePlayer = null;
        private ChessPlayer blackPlayer = null;
        private String event = null;
        private String location = null;
        private String date = null;
        private String round = null;
        private String result = null;
        private List<ChessMove> moves = new ArrayList<ChessMove>();
        
        public Builder()
        {
        }
        
        public Builder setWhitePlayer(ChessPlayer whitePlayer)
        {
            this.whitePlayer = whitePlayer;
            return this;
        }
        
        public Builder setBlackPlayer(ChessPlayer blackPlayer)
        {
            this.blackPlayer = blackPlayer;
            return this;
        }
        
        public Builder setEvent(String event)
        {
            this.event = event;
            return this;
        }
        
        public Builder setLocation(String location)
        {
            this.location = location;
            return this;
        }
        
        public Builder setDate(String date)
        {
            this.date = date;
            return this;
        }
        
        public Builder setRound(String round)
        {
            this.round = round;
            return this;
        }
        
        public Builder setResult(String result)
        {
            this.result = result;
            return this;
        }
        
        public Builder setMoves(List<ChessMove> moves)
        {
            this.moves = new ArrayList<ChessMove>();
            this.moves.addAll(moves);
            return this;
        }
        
        public Builder addMoves(List<ChessMove> moves)
        {
            this.moves.addAll(moves);
            return this;
        }
        
        public Builder addMove(ChessMove move)
        {
            this.moves.add(move);
            return this;
        }
        
        public ChessGame build()
        {
            return new ChessGame(this);
        }
    }
}
