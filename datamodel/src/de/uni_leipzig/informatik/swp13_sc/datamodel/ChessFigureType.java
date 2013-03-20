/**
 * ChessFigureType.java
 */

package de.uni_leipzig.informatik.swt13_sc.datamodel;

public enum ChessFigureType
{
    Pawn,
    Rook,
    Knight,
    Bishop,
    Queen,
    King;
    
    // TODO: Wertung mit abspeichern? (Schlagwert, Bauerneinheiten)
    //       Zugrichtung? Bewegungsmatrix? (siehe andere PGN-Engines)
}
