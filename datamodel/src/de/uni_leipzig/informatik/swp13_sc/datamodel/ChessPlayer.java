/**
 * ChessPlayer.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a player in chess. It could be a computer or human.
 *
 * @author Erik
 *
 */
public class ChessPlayer
{
    /**
     * The player's name.
     */
    private final String name;
    /**
     * Possible attached meta data.
     */
    private final Map<String, String> meta;
    
    /**
     * Creates the player with a {@link ChessPlayer.Builder}.
     * 
     * @param   builder The {@link ChessPlayer.Builder} used to set the
     *                  attributes.
     */
    public ChessPlayer(Builder builder)
    {
        this.name      = builder.name;
        this.meta      = new HashMap<String, String>();
        this.meta.putAll(builder.meta);
    }
    
    /**
     * Returns the name of this player.
     * 
     * @return  String with name
     */
    public String getName()
    {
        return this.name;
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
     * @return  String (meta data value) or null if not available
     */
    public String getMetaValue(String key)
    {
        return this.meta.get(key);
    }
    
    /**
     * Returns true if this class stores additional meta data for a chess
     * player.
     * 
     * @return  true if
     */
    public boolean hasAdditionalMetaData()
    {
        return (! this.meta.isEmpty());
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("ChessPlayer [name=").append(name).append(", meta=")
                .append(meta).append("]");
        return builder2.toString();
    }
    

    /**
     * A class for easy construction of {@link ChessPlayer}s. It contains only
     * Setter-methods for configuring the attributes. To create the ChessPlayer
     * the {@link #build()} method has to be called.
     *
     * @author Erik
     *
     */
    public static class Builder
    {
        private String name;
        private Map<String, String> meta = new HashMap<String, String>();
        
        /**
         * Standard constructor.
         */
        public Builder()
        {
        }
        
        /**
         * Sets the name for a {@link ChessPlayer}.
         * 
         * @param   name    The player's name. (forname, surname, complete,
         *                  ...)
         * @return  Builder
         */
        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }
        
        /**
         * Adds a meta data key-value pair linked to this player.
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
        
        
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Builder [name=").append(name).append(", meta=")
                    .append(meta).append("]");
            return builder.toString();
        }
        

        /**
         * Called last to create the {@link ChessPlayer} object.
         * 
         * @return  {@link ChessPlayer}
         */
        public ChessPlayer build()
        {
            return new ChessPlayer(this);
        }
    }
}
