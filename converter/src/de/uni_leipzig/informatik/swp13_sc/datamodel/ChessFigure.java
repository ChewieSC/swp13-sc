/**
 * ChessFigure.java
 */

package de.uni_leipzig.informatik.swt13_sc.datamodel;

import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessColor;
import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessFigureType;
import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessPosition;

public enum ChessFigure
{
    WhitePawn1(White, Pawn, A2), WhitePawn2(White, Pawn, B2),
    WhitePawn3(White, Pawn, C2), WhitePawn4(White, Pawn, D2),
    WhitePawn5(White, Pawn, E2), WhitePawn6(White, Pawn, F2),
    WhitePawn7(White, Pawn, G2), WhitePawn8(White, Pawn, H2),
    WhiteRook1(White, Rook, A1), WhiteRook2(White, Rook, H1),
    WhiteKnight1(White, Knight, B1), WhiteKnight2(White, Knight, G1),
    WhiteBishop1(White, Bishop, C1), WhiteBishop2(White, Bishop, F1),
    WhiteQueen(White, Queen, D1), WhiteKing(White, King, E1),
    BlackPawn1(Black, Pawn, A7), BlackPawn2(Black, Pawn, B7),
    BlackPawn3(Black, Pawn, C7), BlackPawn4(Black, Pawn, D7),
    BlackPawn5(Black, Pawn, E7), BlackPawn6(Black, Pawn, F7),
    BlackPawn7(Black, Pawn, G7), BlackPawn8(Black, Pawn, H7),
    BlackRook1(Black, Rook, A8), BlackRook2(Black, Rook, H8),
    BlackKnight1(Black, Knight, B8), BlackKnight2(Black, Knight, G8),
    BlackBishop1(Black, Bishop, C8), BlackBishop2(Black, Bishop, F8),
    BlackQueen(Black, Queen, E8), BlackKing(Black, King, D8);
    
    private final static ChessFigure[] WhitePawns = {WhitePawn1, WhitePawn2,
        WhitePawn3, WhitePawn4, WhitePawn5, WhitePawn6, WhitePawn7, WhitePawn8};
    private final static Chessfigure[] BlackPawns = {BlackPawn1, BlackPawn2,
        BlackPawn3, BlackPawn4, BlackPawn5, BlackPawn6, BlackPawn7, BlackPawn8};
    // TODO: add some more?
    //       better alternative?
    
    
    private final ChessFigureType type;
    private final ChessColor color;
    private final ChessPosition startPosition;
    // TODO: needs more attributes?
    
    private ChessFigure(ChessFigureType type, ChessColor color, ChessPosition start)
    {
        this.type = type;
        this.color = color;
        this.startPosition = start;
    }
    
    public final ChessFigureType getFigureType()
    {
        return type;
    }
    
    public final ChessColor getColor()
    {
        return color;
    }
    
    public final ChessPosition getStartPosition()
    {
        // getStartField() ?
        return startPosition;
    }
    
    public static ChessFigure getFigureFromPosition(ChessPosition pos)
    {
        for (ChessFigure f : ChessFigure.values())
        {
            if (f.getStartPosition().equals(pos))
            {
            	return f;
            } 
        }
    }
    
    public final static ChessFigure[] getWhitePawns()
    {
        return ChessFigure.WhitePawns;
    }
    
    public final static ChessFigure[] getBlackPawns()
    {
        return ChessFigure.BlackPawns;
    }
    
    public final static ChessFigure[] getFigures(ChessFigureType type)
    {
        // TODO: add code
    }
    
    public final static ChessFigure[] getFigures(ChessColor color)
    {
        // TODO: add code
    }
    
}
