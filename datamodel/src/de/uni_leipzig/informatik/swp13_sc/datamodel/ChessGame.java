/**
 * ChessGame.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chess game played between two {@link ChessPlayer}s.
 *
 * @author Erik
 *
 */
public class ChessGame
{
    /**
     * The white Player.
     */
    private final ChessPlayer whitePlayer;
    /**
     * The black Player.
     */
    private final ChessPlayer blackPlayer;
    /**
     * The event this game was played at.
     */
    private final String event;
    /**
     * The location this game was played at.
     */
    private final String location;
    /**
     * The date this game was played at.
     */
    private final String date;
    /**
     * The round of a series of chess games.
     */
    private final String round;
    /**
     * The result of this game.
     */
    private final String result;
    /**
     * The {@link ChessMove}s in this game.
     */
    private final List<ChessMove> moves;
    
    /**
     * Constructor used to create this chess game.
     * 
     * @param   builder {@link ChessGame.Builder} used to set the attributes.
     */
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
    
    /**
     * Returns the white {@link ChessPlayer} of this game.
     * 
     * @return  {@link ChessPlayer}
     */
    public ChessPlayer getWhitePlayer()
    {
        return this.whitePlayer;
    }
    
    /**
     * Returns the black {@link ChessPlayer} of this game.
     * 
     * @return  {@link ChessPlayer}
     */
    public ChessPlayer getBlackPlayer()
    {
        return this.blackPlayer;
    }
    
    /**
     * Returns the event this game was played at.
     * 
     * @return  String with event name/title
     */
    public String getEvent()
    {
        return this.event;
    }
    
    /**
     * Returns the locations this game was played at.
     * 
     * @return  String with the location's name/title.
     */
    public String getLocation()
    {
        return this.location;
    }
    
    /**
     * Returns the date this game was played at.
     * 
     * @return  String with date. Can be standardized but can't be guaranteed.
     */
    public String getDate()
    {
        return this.date;
    }
    
    /**
     * Returns the round of this chess game.
     * 
     * @return  String with round notation
     */
    public String getRound()
    {
        return this.round;
    }
    
    /**
     * Returns the result of this chess game.
     * 
     * @return  String with standard (?) chess result notation.
     */
    public String getResult()
    {
        return this.result;
    }
    
    /**
     * Returns the internal list of {@link ChessMove}s in this game.
     * 
     * @return  List<{@link ChessMove}>
     */
    public List<ChessMove> getMoves()
    {
        return this.moves;
    }
    
    
    /**
     * A class for easy construction of {@link ChessGame}s. It contains only
     * Setter-methods for configuring the attributes. To create the ChessGame
     * the {@link #build()} method has to be called.
     *
     * @author Erik
     *
     */
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
        
        /**
         * Standard empty constructor.
         */
        public Builder()
        {
        }
        
        /**
         * Sets the white {@link ChessPlayer} of this game.
         * 
         * @param   whitePlayer The white {@link ChessPlayer}.
         * @return  Builder
         */
        public Builder setWhitePlayer(ChessPlayer whitePlayer)
        {
            this.whitePlayer = whitePlayer;
            return this;
        }
        
        /**
         * Sets the black {@link ChessPlayer} of this game.
         * 
         * @param   blackPlayer The black {@link ChessPlayer}.
         * @return  Builder
         */
        public Builder setBlackPlayer(ChessPlayer blackPlayer)
        {
            this.blackPlayer = blackPlayer;
            return this;
        }
        
        /**
         * Sets the event name/title this game was played at.
         * 
         * @param   event   String with event name.
         * @return  Builder
         */
        public Builder setEvent(String event)
        {
            this.event = event;
            return this;
        }
        
        /**
         * Sets the location this game was played at.
         * 
         * @param   location    String with location
         * @return  Builder
         */
        public Builder setLocation(String location)
        {
            this.location = location;
            return this;
        }
        
        /**
         * Sets the date this game was played at. Would be great to be a
         * standardized date String but needn't be so.
         * 
         * @param   date    String with date
         * @return  Builder
         */
        public Builder setDate(String date)
        {
            this.date = date;
            return this;
        }
        
        /**
         * Sets the round of this game.
         * 
         * @param   round   String with round notation.
         * @return  Builder
         */
        public Builder setRound(String round)
        {
            this.round = round;
            return this;
        }
        
        /**
         * Sets the result of this game.
         * 
         * @param   result  String with chess result notation.
         * @return  Builder
         */
        public Builder setResult(String result)
        {
            this.result = result;
            return this;
        }
        
        /**
         * Sets the moves of this {@link ChessGame}. If there were some old
         * moves left they are overwritten.
         * 
         * @param   moves   List<{@link ChessMove}>
         * @return  Builder
         */
        public Builder setMoves(List<ChessMove> moves)
        {
            this.moves = new ArrayList<ChessMove>();
            this.moves.addAll(moves);
            return this;
        }
        
        /**
         * Adds a list of {@link ChessMove}s to this {@link ChessGame}. Old
         * ones are preserved.
         * 
         * @param   moves   List<{@link ChessMove}>
         * @return  Builder
         */
        public Builder addMoves(List<ChessMove> moves)
        {
            this.moves.addAll(moves);
            return this;
        }
        
        /**
         * Adds a single {@link ChessMove} to this {@link ChessGame}.
         * 
         * @param   move    {@link ChessMove}
         * @return  Builder
         */
        public Builder addMove(ChessMove move)
        {
            this.moves.add(move);
            return this;
        }
        
        /**
         * This method is called last to generate the {@link ChessGame}.<br />
         * (It is possible to call this method and work with this Builder later
         * but it is not recommended. But possible for inspecting the object
         * created ...)
         * 
         * @return  {@link ChessGame}
         */
        public ChessGame build()
        {
            return new ChessGame(this);
        }
    }
}
