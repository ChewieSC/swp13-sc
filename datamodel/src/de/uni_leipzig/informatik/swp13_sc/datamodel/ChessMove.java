/**
 * ChessMove.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

public class ChessMove
{
    private final String move;
    private final String fen;
    private final int nr;
    private final String comment;
    
    public ChessMove(Builder builder)
    {
        this.move    = builder.move;
        this.fen     = builder.fen;
        this.nr      = builder.nr;
        this.comment = builder.comment;
    }
    
    public String getMove()
    {
        return this.move;
    }
    
    public String getFEN(boolean complete)
    {
        if (this.fen == null)
        {
            // TODO: or generate?
            return "";
        }
        
        if (complete)
        {
            return this.fen;
        }
        else
        {
            // TODO: check.
            return this.fen.substring(0, this.fen.indexOf(' '));
        }
    }
    
    public String getFEN()
    {
        return this.getFEN(true);
    }
    
    public int getNr()
    {
        return this.nr;
    }
    
    public boolean isWhite()
    {
        return (this.nr % 2) == 1;
    }
    
    public String getComment()
    {
        return this.comment;
    }
    
    public boolean hasComment()
    {
        return this.comment != null;
    }
    
    
    public static class Builder
    {
        private String move = null;
        private String fen = null;
        private int nr = 0;
        private String comment = null;
        
        public Builder()
        {
        }
        
        public Builder setMove(String move)
        {
            this.move = move;
            return this;
        }
        
        public Builder setFEN(String fen)
        {
            // TODO: check?
            this.fen = fen;
            return this;
        }
        
        public Builder setNr(int nr)
        {
            this.nr = nr;
            return this;
        }
        
        public Builder setComment(String comment)
        {
            this.comment = comment;
            return this;
        }
        
        public ChessMove build()
        {
            return new ChessMove(this);
        }   
    }
}
