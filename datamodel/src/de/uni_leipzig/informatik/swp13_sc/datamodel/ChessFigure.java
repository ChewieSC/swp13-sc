/**
 * ChessFigure.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessColor;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigureType;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPosition;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Erik
 *
 */
public enum ChessFigure
{
    /**
     * WhitePawn1(ChessColor.White, ChessFigureType.Pawn, ChessPosition.A2)
     */
    WhitePawn1(ChessColor.White, ChessFigureType.Pawn, ChessPosition.A2),
    /**
     * WhitePawn2(ChessColor.White, ChessFigureType.Pawn, ChessPosition.B2)
     */
    WhitePawn2(ChessColor.White, ChessFigureType.Pawn, ChessPosition.B2),
    /**
     * WhitePawn3(ChessColor.White, ChessFigureType.Pawn, ChessPosition.D2)
     */
    WhitePawn4(ChessColor.White, ChessFigureType.Pawn, ChessPosition.D2),
    /**
     * WhitePawn3(ChessColor.White, ChessFigureType.Pawn, ChessPosition.C2)
     */
    WhitePawn3(ChessColor.White, ChessFigureType.Pawn, ChessPosition.C2),
    /**
     * WhitePawn5(ChessColor.White, ChessFigureType.Pawn, ChessPosition.F2)
     */
    WhitePawn6(ChessColor.White, ChessFigureType.Pawn, ChessPosition.F2),
    /**
     * WhitePawn5(ChessColor.White, ChessFigureType.Pawn, ChessPosition.E2)
     */
    WhitePawn5(ChessColor.White, ChessFigureType.Pawn, ChessPosition.E2),
    /**
     * WhitePawn7(ChessColor.White, ChessFigureType.Pawn, ChessPosition.G2)
     */
    WhitePawn7(ChessColor.White, ChessFigureType.Pawn, ChessPosition.G2),
    /**
     * WhitePawn8(ChessColor.White, ChessFigureType.Pawn, ChessPosition.H2)
     */
    WhitePawn8(ChessColor.White, ChessFigureType.Pawn, ChessPosition.H2),
    /**
     * WhiteRook2(ChessColor.White, ChessFigureType.Rook, ChessPosition.A1)
     */
    WhiteRook1(ChessColor.White, ChessFigureType.Rook, ChessPosition.A1),
    /**
     * WhiteRook2(ChessColor.White, ChessFigureType.Rook, ChessPosition.H1)
     */
    WhiteRook2(ChessColor.White, ChessFigureType.Rook, ChessPosition.H1),
    /**
     * WhiteKnight1(ChessColor.White, ChessFigureType.Knight, ChessPosition.B1)
     */
    WhiteKnight1(ChessColor.White, ChessFigureType.Knight, ChessPosition.B1),
    /**
     * WhiteKnight2(ChessColor.White, ChessFigureType.Knight, ChessPosition.G1)
     */
    WhiteKnight2(ChessColor.White, ChessFigureType.Knight, ChessPosition.G1),
    /**
     * WhiteBishop1(ChessColor.White, ChessFigureType.Bishop, ChessPosition.C1)
     */
    WhiteBishop1(ChessColor.White, ChessFigureType.Bishop, ChessPosition.C1),
    /**
     * WhiteBishop2(ChessColor.White, ChessFigureType.Bishop, ChessPosition.F1)
     */
    WhiteBishop2(ChessColor.White, ChessFigureType.Bishop, ChessPosition.F1),
    /**
     * WhiteQueen(ChessColor.White, ChessFigureType.Queen, ChessPosition.D1)
     */
    WhiteQueen(ChessColor.White, ChessFigureType.Queen, ChessPosition.D1),
    /**
     * WhiteKing(ChessColor.White, ChessFigureType.King, ChessPosition.E1)
     */
    WhiteKing(ChessColor.White, ChessFigureType.King, ChessPosition.E1),
    /**
     * BlackPawn1(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.A7)
     */
    BlackPawn1(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.A7),
    /**
     * BlackPawn2(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.B7)
     */
    BlackPawn2(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.B7),
    /**
     * BlackPawn3(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.C7)
     */
    BlackPawn3(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.C7),
    /**
     * BlackPawn4(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.D7)
     */
    BlackPawn4(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.D7),
    /**
     * BlackPawn5(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.E7)
     */
    BlackPawn5(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.E7),
    /**
     * BlackPawn6(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.F7)
     */
    BlackPawn6(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.F7),
    /**
     * BlackPawn7(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.G7)
     */
    BlackPawn7(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.G7),
    /**
     * BlackPawn8(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.H7)
     */
    BlackPawn8(ChessColor.Black, ChessFigureType.Pawn, ChessPosition.H7),
    /**
     * BlackRook1(ChessColor.Black, ChessFigureType.Rook, ChessPosition.A8)
     */
    BlackRook1(ChessColor.Black, ChessFigureType.Rook, ChessPosition.A8),
    /**
     * BlackRook2(ChessColor.Black, ChessFigureType.Rook, ChessPosition.H8)
     */
    BlackRook2(ChessColor.Black, ChessFigureType.Rook, ChessPosition.H8),
    /**
     * BlackKnight1(ChessColor.Black, ChessFigureType.Knight, ChessPosition.B8)
     */
    BlackKnight1(ChessColor.Black, ChessFigureType.Knight, ChessPosition.B8),
    /**
     * BlackKnight2(ChessColor.Black, ChessFigureType.Knight, ChessPosition.G8)
     */
    BlackKnight2(ChessColor.Black, ChessFigureType.Knight, ChessPosition.G8),
    /**
     * BlackBishop1(ChessColor.Black, ChessFigureType.Bishop, ChessPosition.C8)
     */
    BlackBishop1(ChessColor.Black, ChessFigureType.Bishop, ChessPosition.C8),
    /**
     * BlackBishop2(ChessColor.Black, ChessFigureType.Bishop, ChessPosition.F8)
     */
    BlackBishop2(ChessColor.Black, ChessFigureType.Bishop, ChessPosition.F8),
    /**
     * BlackQueen(ChessColor.Black, ChessFigureType.Queen, ChessPosition.E8)
     */
    BlackQueen(ChessColor.Black, ChessFigureType.Queen, ChessPosition.E8),
    /**
     * BlackKing(ChessColor.Black, ChessFigureType.King, ChessPosition.D8)
     */
    BlackKing(ChessColor.Black, ChessFigureType.King, ChessPosition.D8);
    
    private final static ChessFigure[] WhitePawns = {WhitePawn1, WhitePawn2,
        WhitePawn3, WhitePawn4, WhitePawn5, WhitePawn6, WhitePawn7, WhitePawn8};
    private final static ChessFigure[] BlackPawns = {BlackPawn1, BlackPawn2,
        BlackPawn3, BlackPawn4, BlackPawn5, BlackPawn6, BlackPawn7, BlackPawn8};
    // private final static List<ChessFigure> = Arrays.asList();
    // TODO: add some more?
    //       better alternative?
    
    
    /**
     * The figure type of a chess piece.
     */
    private final ChessFigureType type;
    /**
     * The color of a chess piece.
     */
    private final ChessColor color;
    /**
     * The start position of a chess piece.
     */
    private final ChessPosition startPosition;
    // TODO: needs more attributes?
    
    /**
     * Internal
     * @param color {@link ChessColor}
     * @param type {@link ChessFigureType}
     * @param start {@link ChessPosition}
     */
    private ChessFigure(ChessColor color, ChessFigureType type, ChessPosition start)
    {
        this.type = type;
        this.color = color;
        this.startPosition = start;
    }
    
    /**
     * Returns the {@link ChessFigureType} of this ChessFigure.
     * @return ChessFigureType
     */
    public final ChessFigureType getFigureType()
    {
        return type;
    }
    
    /**
     * Returns the {@link ChessColor} of this chess piece.
     * @return ChessColor
     */
    public final ChessColor getColor()
    {
        return color;
    }
    
    /**
     * Returns the starting position of this chess piece
     * @return {@link ChessPosition}
     */
    public final ChessPosition getStartPosition()
    {
        // getStartField() ?
        return startPosition;
    }
    
    /**
     * Gets the {@link Chessfigure} which starting {@link ChessPosition}
     * equals the given ChessPosition pos.
     * @param pos ChessPosition from where a ChessFigure started.
     * @return ChessFigure
     */
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
    
    /**
     * Returns a List of ChessFigures whose {@link ChessFigureType} equals
     * the given Parameter type.
     * @param type ChessFigureType
     * @return List<ChessFigure>, is never null if <strong>valid</strong> ChessFigureType!
     */
    public final static List<ChessFigure> getFigures(ChessFigureType type)
    {
        ArrayList<ChessFigure> l = new ArrayList<ChessFigure>();
        for (ChessFigure f : ChessFigure.values())
        {
            if (f.getFigureType().equals(type))
            {
                l.add(f);
            }
        }
        return l;
    }
    
    /**
     * Returns  List of ChessFigures whose {@link ChessColor} equals the
     * given Parameter color.
     * @param color ChessColor
     * @return List<ChessFigure>
     * @see #getFigures(ChessFigureType)
     */
    public final static List<ChessFigure> getFigures(ChessColor color)
    {
        ArrayList<ChessFigure> l = new ArrayList<ChessFigure>();
        for (ChessFigure f : ChessFigure.values())
        {
            if (f.getColor().equals(color))
            {
                l.add(f);
            }
        }
        return l;
    }
    
    // TODO: add an example: white figures & pawns
    /**
     * Gets the intersection of two Lists of ChessFigures. Used for generating list of
     * ChessFigures whose attributes are the same. (?)
     * @param first List<ChessFigure>
     * @param second List<ChessFigure>
     * @return List<ChessFigure>
     * @see #getFigures(ChessFigureType)
     * @see #getFigures(ChessColor)
     */
    public final static List<ChessFigure> intersect(List<ChessFigure> first,
        List<ChessFigure> second)
    {
        List<ChessFigure> l = new ArrayList<ChessFigure>();
        // which is better? (performance)
        // or write own code?
        if (first.size() > second.size())
        {
            l.addAll(second);
            l.retainAll(first);
            return l;
        }
        else // (first <= second)
        {
            l.addAll(second);
            l.retainAll(first);
            return l;
        }
    }
    
    static
    {
        // TODO: add some collections (final)
    }
}
