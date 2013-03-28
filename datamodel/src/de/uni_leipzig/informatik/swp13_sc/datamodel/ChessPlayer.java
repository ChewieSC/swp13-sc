/**
 * ChessPlayer.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

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
     * Creates the player with a {@link ChessPlayer.Builder}.
     * 
     * @param   builder The {@link ChessPlayer.Builder} used to set the
     *                  attributes.
     */
    public ChessPlayer(Builder builder)
    {
        this.name = builder.name;
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
