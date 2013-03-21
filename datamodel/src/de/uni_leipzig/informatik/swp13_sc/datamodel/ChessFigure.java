/**
 * ChessFigure.java
 */

package de.uni_leipzig.informatik.swt13_sc.datamodel;

import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessColor;
import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessFigureType;
import de.uni_leipzig.informatik.swt13_sc.datamodel.ChessPosition;

public enum ChessFigure
{
    WhitePawn1(ChessColor.White, ChessFigureType.Pawn, ChessPosition.A2),
    WhitePawn2(ChessColor.White, ChessFigureType.Pawn, ChessPosition.B2),
    WhitePawn3(ChessColor.White, ChessFigureType.Pawn, ChessPosition.C2),
    WhitePawn4(ChessColor.White, ChessFigureType.Pawn, ChessPosition.D2),
    WhitePawn5(ChessColor.White, ChessFigureType.Pawn, ChessPosition.E2),
    WhitePawn6(ChessColor.White, ChessFigureType.Pawn, ChessPosition.F2),
    WhitePawn7(ChessColor.White, ChessFigureType.Pawn, ChessPosition.G2),
    WhitePawn8(ChessColor.White, ChessFigureType.Pawn, ChessPosition.H2),
    WhiteRook1(ChessColor.White, ChessFigureType.Rook, ChessPosition.A1),
    WhiteRook2(ChessColor.White, ChessFigureType.Rook, ChessPosition.H1),
    WhiteKnight1(ChessColor.White, ChessFigureType.Knight, ChessPosition.B1),
    WhiteKnight2(ChessColor.White, ChessFigureType.Knight, ChessPosition.G1),
    WhiteBishop1(ChessColor.White, ChessFigureType.Bishop, ChessPosition.C1),
    WhiteBishop2(ChessColor.White, ChessFigureType.Bishop, ChessPosition.F1),
    WhiteQueen(ChessColor.White, ChessFigureType.Queen, ChessPosition.D1),
    WhiteKing(ChessColor.White, ChessFigureType.King, ChessPosition.E1),
    BlackPawn1(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.A7),
    BlackPawn2(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.B7),
    BlackPawn3(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.C7),
    BlackPawn4(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.D7),
    BlackPawn5(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.E7),
    BlackPawn6(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.F7),
    BlackPawn7(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.G7),
    BlackPawn8(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.H7),
    BlackRook1(ChessColor.Black, ChessFigureType.Rook, ChessPosition.A8),
    BlackRook2(ChessColor.Black, ChessFigureType.Rook, ChessPosition.H8),
    BlackKnight1(ChessColor.Black, ChessFigureType.Knight, ChessPosition.B8),
    BlackKnight2(ChessColor.Black, ChessFigureType.Knight, ChessPosition.G8),
    BlackBishop1(ChessColor.Black, ChessFigureType.Bishop, ChessPosition.C8),
    BlackBishop2(ChessColor.Black, ChessFigureType.Bishop, ChessPosition.F8),
    BlackQueen(ChessColor.Black, ChessFigureType.Queen, ChessPosition.E8),
    BlackKing(ChessColor.Black, ChessFigureType.King, ChessPosition.D8);
    
    private final static ChessFigure[] WhitePawns = {WhitePawn1, WhitePawn2,
        WhitePawn3, WhitePawn4, WhitePawn5, WhitePawn6, WhitePawn7, WhitePawn8};
    private final static ChessFigure[] BlackPawns = {BlackPawn1, BlackPawn2,
        BlackPawn3, BlackPawn4, BlackPawn5, BlackPawn6, BlackPawn7, BlackPawn8};
    // private final static List<ChessFigure> = Arrays.asList();
    // TODO: add some more?
    //       better alternative?
    
    
    private final ChessFigureType type;
    private final ChessColor color;
    private final ChessPosition startPosition;
    // TODO: needs more attributes?
    
    private ChessFigure(ChessColor color, ChessFigureType type, ChessPosition start)
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
		return null;
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
		return null;
    }
    
    public final static ChessFigure[] getFigures(ChessColor color)
    {
        // TODO: add code
		return null;
    }
    
}
