/**
 * ChessOpening.java
 */
package de.uni_leipzig.informatik.swp13_sc.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an opening of a chess game. A list of moves.
 *
 * @author Erik
 */
public class ChessOpening
{
    /**
     * The code name of this opening. (or category)
     */
    private final String code;
    /**
     * The name of the opening.
     */
    private final String name;
    /**
     * The {@link ChessMove}s in this game.
     */
    private final List<ChessMove> moves;
    
    
    /**
     * Constructor used to create this chess opening.
     * 
     * @param   builder {@link ChessOpening.Builder} used to set the
     *          attributes.
     */
    public ChessOpening(Builder builder)
    {
        this.code       = builder.code;
        this.name       = builder.name;
        this.moves      = new ArrayList<ChessMove>();
        this.moves.addAll(builder.moves);
    }
    
    /**
     * Returns the code name of this chess opening.
     * 
     * @return  String code name ([A-E][0-9][0-9])
     */
    public String getCode()
    {
        return this.code;
    }
    
    /**
     * Returns the name of the chess opening.
     * 
     * @return  String
     */
    public String getName()
    {
        return this.name;
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
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder2 = new StringBuilder();
        builder2.append("ChessOpening [code=").append(code).append(", name=")
                .append(name).append(", moves=").append(moves).append("]");
        return builder2.toString();
    }


    /**
     * A class for easy construction of {@link ChessOpening}s. It contains only
     * Setter-methods for configuring the attributes. To create the
     * ChessOpening call {@link #build()} method.
     *
     * @author Erik
     */
    public static class Builder
    {
        private String code = null;
        private String name = null;
        private List<ChessMove> moves = new ArrayList<ChessMove>();
        
        /**
         * Standard empty constructor.
         */
        public Builder()
        {
        }
        
        /**
         * Sets the code name of the {@link ChessOpening}.
         * @param   code    Code name of the opening
         * @return  Builder
         */
        public Builder setCode(String code)
        {
            this.code = code;
            return this;
        }
        
        /**
         * Sets the name of this {@link ChessOpening}.
         * 
         * @param   name    Name of the opening
         * @return  Builder
         */
        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }
        
        /**
         * Sets the moves of this {@link ChessOpening}. If there were some old
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
         * Adds a list of {@link ChessMove}s to this {@link ChessOpening}. Old
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
         * Adds a single {@link ChessMove} to this {@link ChessOpening}.
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
         * This method is called last to generate the {@link ChessOpening}.<br />
         * (It is possible to call this method and work with this Builder later
         * but it is not recommended. But possible for inspecting the object
         * created ...)
         * 
         * @return  {@link ChessOpening}
         */
        public ChessOpening build()
        {
            return new ChessOpening(this);
        }

        
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Builder [code=").append(code).append(", name=")
                    .append(name).append(", moves=").append(moves).append("]");
            return builder.toString();
        }
    }
}
