/**
 * ChessGame.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * The site/location this game was played at.
     */
    private final String site;
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
     * Possible attached meta data.
     */
    private final Map<String, String> meta;
    
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
        this.site        = builder.site;
        this.date        = builder.date;
        this.round       = builder.round;
        this.result      = builder.result;
        this.moves       = new ArrayList<ChessMove>();
        this.moves.addAll( builder.moves);
        this.meta        = new HashMap<String, String>();
        this.meta.putAll(  builder.meta);
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
     * Returns the location/site this game was played at.
     * 
     * @return  String with the location's name/title.
     */
    public String getSite()
    {
        return this.site;
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
     * Returns the internal map of meta data key-value pairs.
     * 
     * @return  Map<String, String>
     */
    public Map<String, String> getMetaData()
    {
        return this.meta;
    }
    
    /**
     * Returns a meta value for the specified key or null if no value has been
     * stored.
     * 
     * @param   key     Key-name of the meta value.
     * @return  Builder
     */
    public String getMetaValue(String key)
    {
        return this.meta.get(key);
    }
        
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("ChessGame [whitePlayer=").append(whitePlayer)
                .append(", blackPlayer=").append(blackPlayer)
                .append(", event=").append(event).append(", site=")
                .append(site).append(", date=").append(date)
                .append(", round=").append(round).append(", result=")
                .append(result).append(", moves=").append(moves)
                .append(", meta=").append(meta).append("]");
        return builder2.toString();
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
        private String site = null;
        private String date = null;
        private String round = null;
        private String result = null;
        private List<ChessMove> moves = new ArrayList<ChessMove>();
        private Map<String, String> meta = new HashMap<String, String>();
        
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
         * Sets the site/location this game was played at.
         * 
         * @param   site    String with site/location
         * @return  Builder
         */
        public Builder setSite(String site)
        {
            this.site = site;
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
         * Adds a meta data key-value pair linked to this game.
         * 
         * @param   key     The key. A identifier for the value.
         * @param   value   The actual value.
         * @return  Builder
         */
        public Builder addMetaData(String key, String value)
        {
            key = key.toLowerCase(); // easier equality check & ontology
            this.meta.put(key, value);
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
            // add all not null meta values
            // TODO: keys lowercase for equality check or original?
            // TODO: add if null for iterator() ?
            if (this.event != null)
            {
                this.meta.put("event", this.event);
            }
            if (this.site != null)
            {
                this.meta.put("site", this.site);
            }
            if (this.date != null)
            {
                this.meta.put("date", this.date);
            }
            if (this.result != null)
            {
                this.meta.put("result", this.result);
            }
            if (this.round != null)
            {
                this.meta.put("round", this.round);
            }
            if ((this.whitePlayer != null) && (this.whitePlayer.getName() != null))
            {
                this.meta.put("white", this.whitePlayer.getName());
            }
            if ((this.blackPlayer != null) && (this.blackPlayer.getName() != null))
            {
                this.meta.put("black", this.blackPlayer.getName());
            }
            return new ChessGame(this);
        }

        
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Builder [whitePlayer=").append(whitePlayer)
                    .append(", blackPlayer=").append(blackPlayer)
                    .append(", event=").append(event).append(", site=")
                    .append(site).append(", date=").append(date)
                    .append(", round=").append(round).append(", result=")
                    .append(result).append(", moves=").append(moves)
                    .append(", meta=").append(meta).append("]");
            return builder.toString();
        }
    }
}
