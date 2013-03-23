/**
 * ChessResult.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

/**
 * Shows the result of a {@link ChessGame}.
 *
 * @author Erik
 */
public enum ChessResult
{
    /**
     * The white Player won.
     */
    WhiteWins,
    /**
     * The black Player won.
     */
    BlackWins,
    /**
     * No Player won. It ended in a draw.
     */
    Draw,
    /**
     * The white Player won because black resigned.
     */
    WhiteWinsWithBlackResigning,
    /**
     * The black Player won because white resigned.
     */
    BlackWinsWithWhiteResigning;
    
    // ChessResultReason als Grund fuer einen Gewinn?
    // - Forfeiting, Nothing, CheckMate
    // oder als Wert mit abspeichern?
    
    // moeglich waere noch entsprechende String-Methode
    // fuer eine schoene Darstellung?
}
