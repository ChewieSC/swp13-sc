/**
 * ChessPlayer.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

public class ChessPlayer
{
    private final String name;
    
    public ChessPlayer(Builder builder)
    {
        this.name = builder.name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    
    public static class Builder
    {
        private String name;
        
        public Builder()
        {
        }
        
        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }
        
        public ChessPlayer build()
        {
            return new ChessPlayer(this);
        }
    }
}
