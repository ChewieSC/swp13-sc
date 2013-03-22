/**
 * ChessMove.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessBoard;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigure;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigureType;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPosition;

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
        this.check = builder.check;
        this.checkMate = builder.checkMate;
        this.isLast = builder.checkMate;
        this.pawnTransformation = builder.pawnTransformation;
        // ...
    }
    
    public ChessFigure getFigure()
    {
        return this.figure;
    }
    
    public ChessPosition getFrom()
    {
        return this.from;
    }
    
    public ChessPosition getTo()
    {
        return this.to;
    }
    
    public boolean isCheck()
    {
        // or compute, if needed?
        return this.check;
    }
    
    // ...
    
    
    public String getFEN()
    {
        // TODO: compute
        return this.fen;
    }
    
    public void setFEN(String fen)
    {
        this.fen = fen;
    }
    
    public String getGBR()
    {
        // TODO: compute
        return ChessBoard.convertFEN2GBR(this.fen);
    }
    
    protected void setGBR(String gbr)
    {
        this.fen = ChessBoard.convertGBR2FEN(gbr);
    }
    
    public String getPGNMove()
    {
        return this.origMove;
    }
    
    public void setPGNMove(String pgnmove)
    {
        this.origMove = pgnmove;
    }
    
    
    /* ********************************************* */
    // Builder
    
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
        
        public Builder()
        {
        }
        
        public Builder setPGNMove(String pgnMove)
        {
            this.origMove = pgnMove;
            // TODO: ChessBoard -> getPosition from/to ?
            return this;
        }
        
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
        
        public Builder setFrom(ChessPosition from)
        {
            this.from = from;
            return this;
        }
        
        public Builder setTo(ChessPosition to)
        {
            this.to = to;
            return this;
        }
        
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
        
        public Builder setCheck()
        {
            return this.setCheck(true);
        }
        
        public Builder setCheck(boolean check)
        {
            this.check = check;
            return this;
        }
        
        public Builder setCheckMate()
        {
            this.checkMate = true;
            this.check = true;
            this.isLast = true;
            return this;
        }
        
        public Builder isLast()
        {
            this.isLast = true;
            return this;
        }
        
        public ChessMove build()
        {
            return new ChessMove(this);
        }
    }
}
