/**
 * ChessMove.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

/**
 * Represents a single move in a chess game.<br />
 * <br />
 * This class is read-only. Modifications are only possible with a
 * {@link ChessMove.Builder} object.
 *
 * @author  Erik
 *
 */
public class ChessMove
{
    /**
     * The actual name of this move. In standard notation.
     */
    private final String move;
    /**
     * The FEN representation of this move.
     */
    private final String fen;
    /**
     * The number of this move.
     */
    private final int nr;
    /**
     * A comment a player has supplied.
     */
    private final String comment;
    
    /**
     * Creates a ChessMove object from a given {@link ChessMove.Builder}.
     *  
     * @param   builder {@link ChessMove.Builder}
     */
    public ChessMove(Builder builder)
    {
        this.move    = builder.move;
        this.fen     = builder.fen;
        this.nr      = builder.nr;
        this.comment = builder.comment;
    }
    
    /**
     * Returns the move notation as a String.
     * 
     * @return  a chess move in chess notation
     * @see     http://de.wikipedia.org/wiki/Schachnotation
     * @see     http://en.wikipedia.org/wiki/Chess_notation
     */
    public String getMove()
    {
        return this.move;
    }
    
    /**
     * Returns the Forsyth-Edwards-Notation of this move.
     * 
     * @param   complete    true if valid FEN or false for short form (only 
     *                      the position of figures)
     * @return  String
     * @see     http://de.wikipedia.org/wiki/Forsyth-Edwards-Notation
     * @see     http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     */
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
    
    /**
     * Returns the valid FEN.
     * 
     * @return  String with FEN
     * @see     #getFEN(boolean)
     */
    public String getFEN()
    {
        return this.getFEN(true);
    }
    
    /**
     * Returns the number of this move.
     * 
     * @return int
     */
    public int getNr()
    {
        return this.nr;
    }
    
    /**
     * Returns true if the move was from the chess player playing with white
     * figures.
     * 
     * @return  true if player color is white
     */
    public boolean isWhite()
    {
        return (this.nr % 2) == 1;
    }
    
    /**
     * Returns the comment for this move if one had been supplied.
     * 
     * @return  String with comment
     */
    public String getComment()
    {
        return this.comment;
    }
    
    /**
     * Returns true if the move has a comment attached to it.
     * 
     * @return  true if move has comment.
     */
    public boolean hasComment()
    {
        return this.comment != null;
    }
    
    
    /**
     * A class for easy construction of {@link ChessMove}s. It contains only
     * Setter-methods for configuring the attributes. To create the ChessMove
     * the {@link #build()} method has to be called.
     *
     * @author Erik
     *
     */
    public static class Builder
    {
        private String move = null;
        private String fen = null;
        private int nr = 0;
        private String comment = null;
        
        /**
         * Standard constructor. Has no parameters.
         */
        public Builder()
        {
        }
        
        /**
         * Sets the name of this {@link ChessMove}.
         * 
         * @param   move    the name
         * @return  Builder
         */
        public Builder setMove(String move)
        {
            this.move = move;
            return this;
        }
        
        /**
         * Sets the FEN for this {@link ChessMove}.
         * 
         * @param   fen     String with FEN-Notation
         * @return  Builder
         * @see ChessMove#getFEN(boolean)
         */
        public Builder setFEN(String fen)
        {
            // TODO: check?
            this.fen = fen;
            return this;
        }
        
        /**
         * Sets the number of this {@link ChessMove}.
         * 
         * @param   nr  The number in the game.
         * @return  Builder
         */
        public Builder setNr(int nr)
        {
            this.nr = nr;
            return this;
        }
        
        /**
         * Sets the comment for a {@link ChessMove}. It is used to supply
         * additional information or mark it as special.
         * 
         * @param   comment A comment supplied from a chess player or user.
         * @return  Builder
         */
        public Builder setComment(String comment)
        {
            this.comment = comment;
            return this;
        }
        
        /**
         * This method is called last to generate the {@link ChessMove}.<br />
         * (It is possible to call this method and work with this Builder later
         * but it is not recommended. But possible for inspecting the object
         * created ...)
         * 
         * @return  {@link ChessMove}
         */
        public ChessMove build()
        {
            return new ChessMove(this);
        }   
    }
}
