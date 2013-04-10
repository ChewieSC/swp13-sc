/**
 * ChessPGNVocabulary.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel.pgn;

/**
 * Collects and holds common constants for PGN specific data.
 * 
 * @author Erik
 * 
 */
public class ChessPGNVocabulary
{
    
    // all the meta keys required

    /**
     * Meta_Key_Black
     */
    public final static String Meta_Key_Black = "Black";
    /**
     * Meta_Key_White
     */
    public final static String Meta_Key_White = "White";
    /**
     * Meta_Key_Event
     */
    public final static String Meta_Key_Event = "Event";
    /**
     * Meta_Key_Date
     */
    public final static String Meta_Key_Date = "Date";
    /**
     * Meta_Key_Result
     */
    public final static String Meta_Key_Result = "Result";
    /**
     * Meta_Key_Round
     */
    public final static String Meta_Key_Round = "Round";
    /**
     * Meta_Key_Site
     */
    public final static String Meta_Key_Site = "Site";

    /**
     * All the meta data keys required for a valid chess game in PGN.
     */
    public final static String[] Required_Meta_Keys =
            {
                Meta_Key_Black, Meta_Key_White, Meta_Key_Event, Meta_Key_Date,
                Meta_Key_Result, Meta_Key_Round, Meta_Key_Site
            };

    // ------------------------------------------------------------------------

    // Chess Figure Characters used in chess moves
    // Notation knows only uppercase
    // Possible to have different if not international games

    /**
     * CN_PAWN
     */
    public final static char CN_Pawn = 'P';
    /**
     * CN_ROOK
     */
    public final static char CN_Rook = 'R';
    /**
     * CN_KNIGHT
     */
    public final static char CN_Knight = 'N';
    /**
     * CN_BISHOP
     */
    public final static char CN_Bishop = 'B';
    /**
     * CN_QUEEN
     */
    public final static char CN_Queen = 'Q';
    /**
     * CN_KING
     */
    public final static char CN_King = 'K';

    /**
     * All pieces/figures in algebraic chess notation. (international version)
     */
    public final static char[] CN_Pieces = new char[]
            {
                CN_Pawn, CN_Rook, CN_Knight, CN_Bishop, CN_Queen, CN_King
            };

}
