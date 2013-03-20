/**
 * ChessMove.java
 */

package de.uni_leipzig.informatik.swt13_sc.datamodel;

import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessBoard;
import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessFigure;
import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessPosition;

public class ChessMove
{
    private final ChessFigure figure;
    private final ChessPosition from;
    private final ChessPosition to;
    private String fen;
    private String origMove;
    private boolean check;
    private boolean isLast;
    private boolean enPassant; //?

    // ...
    
    public ChessMove(ChessFigure figure, ChessPosition from, ChessPosition to)
    {
        // better, to compute, if check ...
        // compute other too?
        this(figure, from, to, false);
    }
    
    public ChessMove(ChessFigure figure, ChessPosition from, ChessPosition to, boolean check)
    {
        this.figure = figure;
        this.from = from;
        this.to = to;
        this.check = check;
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
    
    public String setPGNMove(String pgnmove)
    {
        this.origMove = pgnmove;
    }
}
