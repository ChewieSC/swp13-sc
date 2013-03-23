/**
 * ChessFigureType.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

/**
 * Indicates the {@link ChessFigure}s type.
 * 
 * @author Erik
 */
public enum ChessFigureType
{
    /**
     * Pawn
     */
    Pawn,
    /**
     * Rook
     */
    Rook,
    /**
     * Knight
     */
    Knight,
    /**
     * Bishop
     */
    Bishop,
    /**
     * Queen
     */
    Queen,
    /**
     * King
     */
    King;
    
    // TODO: Wertung mit abspeichern? (Schlagwert, Bauerneinheiten)
    //       Zugrichtung? Bewegungsmatrix? (siehe andere PGN-Engines)
    
    // TODO: safe prnbqk-PRNBQK ? (FEN)
}
