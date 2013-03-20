/**
 * ChessResult.java
 */

package de.uni_leipzig.informatik.swt13_sc.datamodel;

public enum ChessResult
{
    WhiteWins,
    BlackWins,
    Draw,
    WhiteWinsWithBlackResigning,
    BlackWinsWithWhiteResigning;
    
    // ChessResultReason als Grund fuer einen Gewinn?
    // - Forfeiting, Nothing, CheckMate
    // oder als Wert mit abspeichern?
    
    // moeglich waere noch entsprechende String-Methode
    // fuer eine schoene Darstellung?
}
