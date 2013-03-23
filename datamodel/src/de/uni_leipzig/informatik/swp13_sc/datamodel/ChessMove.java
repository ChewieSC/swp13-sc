/**
 * ChessMove.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessBoard;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigure;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigureType;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPosition;

/**
 * Represents a move in chess.
 *
 * @author Erik
 */
public class ChessMove
{
    private final ChessFigure figure;
    private final ChessPosition from;
    private final ChessPosition to;
    private String fen;
    private String origMove;
    private boolean check;
    private boolean checkMate;     // some other method to win?
    private boolean isLast;
    private boolean enPassant;     // ?       // not shown?
    private boolean castling;      // Rochade // format?
    private boolean smallCastling; // ?
    private ChessFigure pawnTransformation;
    private String comment;
    // ...
    
    public ChessMove()
    {
        this(null, null, null, null, false, false, null);
    }
    
    public ChessMove(String origMove, ChessFigure figure, ChessPosition from, ChessPosition to)
    {
        // better, to compute, if check ...
        // compute other too?
        this(origMove, figure, from, to, false, false, null);
    }
    
    
    
    public ChessMove(String origMove, ChessFigure figure, ChessPosition from, ChessPosition to, boolean check,
        boolean checkMate, ChessFigure transformation)
    {
        this.origMove = origMove;
        this.figure = figure;
        this.from = from;
        this.to = to;
        this.check = check;
        this.checkMate = checkMate;
        this.isLast = this.checkMate;     // TODO: check?
        this.pawnTransformation = transformation;
        // ...
    }
    
    public ChessMove(Builder builder)
    {
        this.origMove = builder.origMove;
        this.figure = builder.figure;
        this.from = builder.from;
        this.to = builder.to;
        this.comment = builder.comment;
        this.fen = builder.fen;
        this.check = builder.check;
        this.checkMate = builder.checkMate;
        this.isLast = builder.checkMate;
        this.pawnTransformation = builder.pawnTransformation;
        // ...
    }
    
    /**
     * Returns the Figure used in this move.
     * @return {@link ChessFigure}
     */
    public ChessFigure getFigure()
    {
        return this.figure;
    }
    
    /**
     * Gives the {@link ChessPosition} from where the {@link ChessFigure}
     * moved.
     * @return {@link ChessPosition}
     */
    public ChessPosition getFrom()
    {
        return this.from;
    }
    
    /**
     * Gives the {@link ChessPosition} to where the {@link ChessFigure}
     * moved.
     * @return {@link ChessPosition}
     */
    public ChessPosition getTo()
    {
        return this.to;
    }
    
    /**
     * Returns true if this {@link ChessMove} results in a check.
     * @return boolean
     */
    public boolean isCheck()
    {
        // or compute, if needed?
        return this.check;
    }
    
    /**
     * Comment used from a {@link ChessPlayer}
     * @return String Comment for this move or "" if no comment was given.
     */
    public String getComment()
    {
        return ((this.comment == null) ? "" : this.comment);
    }
    
    /**
     * Sets the comment for this {@link ChessMove}.
     * @param comment Comment for this move.
     */
    public void setComment(String comment)
    {
        this.comment = comment;
    }
    
    /**
     * Checks if this {@link ChessMove} has a comment on it.
     * @return true if this {@link ChessMove} has a comment.
     */
    public boolean hasComment()
    {
        return (this.comment != null);
    }
    
    // TODO: add other Getters
    // ...
    
    
    /**
     * Returns the Forsyth-Edwards-Notation.
     *
     * @return String FEN-Representation of the {@link ChessFigure}s on
     * the {@link ChessBoard}.
     * @see http://de.wikipedia.org/wiki/Forsyth-Edwards-Notation
     * @see http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     */
    public String getFEN()
    {
        // TODO: compute
        return this.fen;
    }
    
    /**
     * Sets the Forsyth-Edwards-Notation.
     * @param fen The Forsyth-Edwards-Notation-String.
     * @see #getFEN()
     */
    public void setFEN(String fen)
    {
        this.fen = fen;
    }
    
    /**
     * Returns the GBR-Code of this ChessMove.
     * @return String GBR-Representation of the {@link ChessFigure}s on
     * the {@link ChessBoard}.
     * @see http://de.wikipedia.org/wiki/GBR-Code
     * @see http://en.wikipedia.org/wiki/GBR_code
     */
    public String getGBR()
    {
        // TODO: compute
        return ChessBoard.convertFEN2GBR(this.fen);
    }
    
    /**
     * Sets the GBR-Code for this move.
     * @param gbr String GBR-Code
     * @see #getGBR()
     */
    protected void setGBR(String gbr)
    {
        this.fen = ChessBoard.convertGBR2FEN(gbr);
    }
    
    /**
     * Returns a chess notation of this move.
     * @return String Chess Notation (e. g. used in PGNs)
     * @see http://de.wikipedia.org/wiki/Schachnotation
     * @see http://en.wikipedia.org/wiki/Chess_notation
     */
    public String getPGNMove()
    {
        return this.origMove;
    }
    
    /**
     * Sets the chess notation of this move.
     * @param pgnmove String with Chess Notation
     * @see #getPGNMove()
     */
    public void setPGNMove(String pgnmove)
    {
        this.origMove = pgnmove;
    }
    
    
    /* ********************************************* */
    // Builder
    
    /**
     * Builder for easier construction of {@link ChessMove}s.
     * Recommended way to create a ChessMove.
     * Consists of Setters only. Getters shouldn't be relevant.
     *
     * @author Erik
     */
    public static class Builder
    {
        private String origMove = null;
        private ChessFigure figure = null;
        private ChessPosition from = null;
        private ChessPosition to = null;
        private boolean check = false;
        private boolean checkMate = false;
        private boolean isLast = false;
        private ChessFigure pawnTransformation = null;
        private String comment = null;
        private String fen = null;
        
        /**
         * Constructor
         */
        public Builder()
        {
        }
        
        /**
         * Sets the Chess Notation of this {@link ChessMove}
         * @param pgnMove
         * @return Builder
         * @see ChessMove#setPGNMove(String)
         */
        public Builder setPGNMove(String pgnMove)
        {
            this.origMove = pgnMove;
            // TODO: ChessBoard -> getPosition from/to ?
            return this;
        }
        
        /**
         * Sets the {@link ChessFigure} used in this move.
         * @param figure ChessFigure used
         * @return Builder
         */
        public Builder setFigure(ChessFigure figure)
        {
            this.figure = figure;
            // could check with ChessFigure.getPawns/Figures ...
            if ((this.pawnTransformation != null) && (this.figure != null) && (! this.figure.getFigureType().equals(ChessFigureType.Pawn)))
            {
                this.pawnTransformation = null;
            }
            return this;
        }
        
        /**
         * Sets the {@link ChessPosition} from where this move originated.
         * @param from origin ChessPosition
         * @return Builder
         */
        public Builder setFrom(ChessPosition from)
        {
            this.from = from;
            return this;
        }
        
        /**
         * Sets the destination {@link ChessPosition} of this move.
         * @param to destination ChessPosition
         * @return Builder
         */
        public Builder setTo(ChessPosition to)
        {
            this.to = to;
            return this;
        }
        
        /**
         * Only used if {@link ChessFigure} with type {@link ChessFigureType#Pawn}
         * moved and as a result was transformed into ChessFigure figure.
         * @param figure ChessFigure into which the Pawn transformed.
         * @return Builder
         */
        public Builder transformInto(ChessFigure figure)
        {
            this.pawnTransformation = figure;
            if ((this.figure == null) || (! this.figure.equals(ChessFigureType.Pawn)))
            {
                // TODO: this.figure should be Pawn
                return null; // TODO: fix this
            }
            return this;
        }
        
        /**
         * Sets the comment for this {@link ChessMove}.
         * @param comment Comment for this move.
         * @return Builder
         * @see ChessMove#setComment(String)
         */
        public Builder setComment(String comment)
        {
            this.comment = comment;
            return this;
        }
        
        /**
         * Only set if this move checked the opponent.
         * @return Builder
         */
        public Builder setCheck()
        {
            return this.setCheck(true);
        }
        
        /**
         * Used to undo things ...
         * @param check true if check else false
         * @return Builder
         * @see #setCheck()
         */
        public Builder setCheck(boolean check)
        {
            this.check = check;
            return this;
        }
        
        /**
         * Set if this move finished the {@link ChessGame} in a checkmate.
         * @return Builder
         */
        public Builder setCheckMate()
        {
            this.checkMate = true;
            this.check = true;
            this.isLast = true;
            return this;
        }
        
        /**
         * Marks this move as the last.
         * @return Builder
         */
        public Builder isLast()
        {
            this.isLast = true;
            return this;
        }
        
        /**
         * Sets the FEN-Representation of this move.
         * @param fen FEN-String
         * @return Builder
         * @see ChessMove#setFEN(String)
         */
        public Builder setFEN(String fen)
        {
            this.fen = fen;
            return this;
        }
        
        /**
         * Used to finish the creation of a {@link ChessMove}.
         * @return {@link ChessMove}
         */
        public ChessMove build()
        {
            return new ChessMove(this);
        }
    }
}
