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
    
    // additional optional meta keys
    
    /**
     * The ELO rang of the white player.
     */
    public final static String Meta_Key_WhiteElo = "WhiteElo";
    /**
     * The ELO rang of the black player.
     */
    public final static String Meta_Key_BlackElo = "BlackElo";
    /**
     * The ECO code of the chess game. (the opening)
     */
    public final static String Meta_Key_ECO = "ECO";
    /**
     * The FEN position code of the last move.
     */
    public final static String Meta_Key_FEN = "FEN";
    
    /**
     * Additional meta keys used in PGNs. Not the complete list!
     */
    public final static String[] Additional_Meta_Keys =
        {
            Meta_Key_WhiteElo, Meta_Key_BlackElo, Meta_Key_ECO, Meta_Key_FEN
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
    
    // ------------------------------------------------------------------------
    
    // regex strings needed for parsing PGN files
    
    /**
     * Regex for the figure types (without pawn).
     */
    public final static String regex_move_figure = "[RNBQK]";
    /**
     * Regex for the row field part notation.
     */
    public final static String regex_move_source_row = "[1-8]";
    /**
     * Regex for the column field notation.
     */
    public final static String regex_move_source_column = "[a-h]";
    /**
     * Regex for partial source field notation.
     */
    public final static String regex_move_source =
            regex_move_source_column + "?" + regex_move_source_row + "?";
    /**
     * Regex for a capture move.
     */
    public final static String regex_move_captured = "x";
    /**
     * Regex for the destination field in a move.
     */
    public final static String regex_move_destination =
            regex_move_source_column + regex_move_source_row;
    
    /**
     * Regex for promotion. e. g. a pawn was transformed into a queen -> "=Q"
     */
    public final static String regex_move_promotion = "=" + regex_move_figure;
    
    /**
     * Regex for check modifier.
     */
    public final static String regex_move_check = "\\+";
    /**
     * Regex for checkmate.
     */
    public final static String regex_move_checkmate = "#"; // ?
    /**
     * Regex for check or checkmate.
     */
    public final static String regex_move_checkCheckmate =
            "(" + regex_move_check + "|" + regex_move_checkmate + ")?";
    
    /**
     * Regex for the round number.
     */
    public final static String regex_move_number = "\\b" + "[1-9][0-9]*\\."; // checked!
    /**
     * Regex for a comment between moves.
     */
    public final static String regex_move_comment = "(" + "\\{[^\\}]*\\}" + ")?"; // ungreedy
    
    /**
     * Regex for queenside castling move. Not used.
     */
    public final static String regex_move_castling_queen = "O\\-O\\-O";
    /**
     * Regex for queenside castling move. Not used.
     */
    public final static String regex_move_castling_king = "O\\-O";
    //(O\\-O(\\-O)?)   <->   (O\\-O\\-O)|(O\\-O)   ???
    /**
     * Regex for a castling move.
     */
    public final static String regex_move_castling =
            //regex_move_castling_queen + "|" + regex_move_castling_king;
            // checked!? -> needs greedy
            "O\\-O(\\-O)?";
    
    /**
     * Regex for a pawn move.
     */
    public final static String regex_move_pawn =
            "(" + regex_move_source_column + regex_move_captured + ")?" + 
                    regex_move_destination +
                    "(" + regex_move_promotion + ")?"; // checked!
    /**
     * Regex for a move other than pawn.
     */
    public final static String regex_move_otherfigure =
            regex_move_figure + regex_move_source + regex_move_captured
                    + "?" + regex_move_destination;
    
    // castling -> check ? maybe possible ...
    /**
     * <p>Regex for a single move notation. Most important and mostly used.</p>
     * <p>
     *  * Group 0: complete (pos, check, comment)<br />
     *  * Group 1: move without comment<br />
     *  * Group 2: move only (source, dest. pos, NO check!)<br />
     *  * Group 3: pawn moves only (source, capture, dest., promo, figure)<br />
     *  * Group 4: pawn move (capture + source column)<br />
     *  * Group 5: promotion (promo, dest. figure)<br />
     *  * Group 6: move of other figures (figure, source, capture, dest.)<br />
     *  * Group 7: castlings (king, queen)<br />
     *  * Group 8: queen side castlings (queen part ? -O)<br />
     *  * Group 9: check (check, checkmate (?))<br />
     *  * Group 10: comments (-> {...})<br />
     * </p>
     */
    public final static String regex_move_single =
            "\\b" + "(((" + regex_move_pawn + ")|(" + regex_move_otherfigure +
                    ")|(" + regex_move_castling + "))" + "\\b" + regex_move_checkCheckmate +
                    ")" + "\\s*" + regex_move_comment + "\\s*"; // checked !!
    
    /**
     * Regex for a round (round number, first move and if present the second move).
     */
    public final static String regex_move_double =
            "(" + regex_move_number + "\\s*" + ")" + regex_move_single + "(" +
                    regex_move_single + ")?"; // checked !!
    
    /**
     * Regex for pgn move part - the result at the end.
     */
    public final static String regex_result =
            "\\b" + "(1\\-0)|(0\\-1)|(1\\/2\\-1\\/2)" + "\\b"; // checked!
    
    
    /**
     * Regex for meta key name.
     */
    public final static String regex_meta_key = "([A-Z]\\w*)";
    /**
     * Regex for meta key value part.
     */
    public final static String regex_meta_value = "\\\"([^\\\"]*)\\\"";
    /**
     * Regex for separator between key and value.
     */
    public final static String regex_meta_separator = "\\s+";
    /**
     * Regex for meta data entry start char.
     */
    public final static String regex_meta_start = "\\[";
    /**
     * Regex for meta data entry end.
     */
    public final static String regex_meta_end = "\\]";
    /**
     * <p>Regex for meta data entry element.</p>
     * <p>
     *  * Group 0: complete<br />
     *  * Group 1: meta key<br />
     *  * Group 2: meta value<br />
     * </p>
     */
    public final static String regex_meta =
            regex_meta_start + regex_meta_key + regex_meta_separator +
                    regex_meta_value + regex_meta_end;
    // needs multi-line modifier if ^PATTERN$
    // check for multi-line modifier ... below
    /**
     * Regex for meta data entry start. Used to check if there is one.
     */
    public final static String regex_meta_start_line =
            "^" + regex_meta_start;
    
}
