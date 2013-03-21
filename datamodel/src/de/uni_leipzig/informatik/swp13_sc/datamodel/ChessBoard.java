/**
 * ChessBoard.java
 */

package de.uni_leipzig.informatik.swt13_sc.datamodel;

import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessFigure;
import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessFigureType;
import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessMove;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard
{
    private ChessFigure[][] field = new ChessFigure[8][8];
    private byte whitePawnCount = 8;
    private byte blackPawnCount = 8;
    private List<ChessMove> moves = new ArrayList<ChessMove>();
    
    public ChessBoard()
    {
    }
    
    
    private void move(ChessMove move)
    {
        // TODO: move on board
    }
    
    public void setMove(ChessMove move)
    {
        if (move != null)
        {
            this.moves.add(move);
            move(move);
        }
    }
    
    public List<ChessMove> getMoves()
    {
        return this.moves;
    }
    
    public boolean hasMoves()
    {
        return (! this.moves.isEmpty());
    }
    
    public String getGBR()
    {
        // TODO: add
		return null;
    }
    
    public String getFEN()
    {
        // TODO: add
		return null;
    }
    
    public static ChessBoard getChessBoardFromFEN(String fen)
    {
        // TODO: add
        // direct access ...
		return null;
    }
    
    public static ChessBoard getChessBoardFromGBR(String gbr)
    {
        // TODO: add
		return null;
    }
    
    public static String getFENFromChessBoard(ChessBoard cb)
    {
        return cb.getFEN();
    }
    
    public static String getGBRFromChessBoard(ChessBoard cb)
    {
        return cb.getGBR();
    }
    
    public static String convertFEN2GBR(String fen)
    {
        return ChessBoard.getChessBoardFromFEN(fen).getGBR();
    }
    
    public static String convertGBR2FEN(String gbr)
    {
        // or longer:
        return ChessBoard.getFENFromChessBoard(ChessBoard.getChessBoardFromGBR(gbr));
    }
}
