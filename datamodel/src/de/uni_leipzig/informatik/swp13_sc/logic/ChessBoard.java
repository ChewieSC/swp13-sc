/**
 * ChessBoard.java
 */

package de.uni_leipzig.informatik.swp13_sc.logic;

//import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;

import java.lang.IllegalArgumentException;
import java.lang.StringBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.uni_leipzig.informatik.swp13_sc.datamodel.pgn.ChessPGNVocabulary;

/**
 * A virtual chess board. Needed for computing FENs.
 *
 * @author Erik
 *
 */
public class ChessBoard
{
    // TODO: how to initialize? Or better way?
    /**
     * The chess field. From y=0..7, x=0..7.<br />
     * The white figures  at the bottom have the y index of 0, 1.<br />
     */
    private char[][] field = getCharChessField();
    /**
     * EMPTY_SQUARE<br />
     * Marks a empty square on a chess board or error.
     */
    public final static char EMPTY_SQUARE = 0x00;
    
    // Chess Figure Characters used in FEN and on Board to
    // differentiate color and piece type.
    
    /**
     * WHITE_PAWN
     */
    public final static char WHITE_PAWN = 'P';
    /**
     * BLACK_PAWN
     */
    public final static char BLACK_PAWN = 'p';
    /**
     * WHITE_ROOK
     */
    public final static char WHITE_ROOK = 'R';
    /**
     * BLACK_ROOK
     */
    public final static char BLACK_ROOK = 'r';
    /**
     * WHITE_KNIGHT
     */
    public final static char WHITE_KNIGHT = 'N';
    /**
     * BLACK_KNIGHT
     */
    public final static char BLACK_KNIGHT = 'n';
    /**
     * WHITE_BISHOP
     */
    public final static char WHITE_BISHOP = 'B';
    /**
     * BLACK_BISHOP
     */
    public final static char BLACK_BISHOP = 'b';
    /**
     * WHITE_QUEEN
     */
    public final static char WHITE_QUEEN = 'Q';
    /**
     * BLACK_QUEEN
     */
    public final static char BLACK_QUEEN = 'q';
    /**
     * WHITE_KING
     */
    public final static char WHITE_KING = 'K';
    /**
     * BLACK_KING
     */
    public final static char BLACK_KING = 'k';
    
    // Chess Figure Characters used in chess moves
    // Notation knows only uppercase
    // Possible to have different if not international games
    
    /**
     * CN_PAWN
     */
    public final static char CN_PAWN = ChessPGNVocabulary.CN_Pawn;
    /**
     * CN_ROOK
     */
    public final static char CN_ROOK = ChessPGNVocabulary.CN_Rook;
    /**
     * CN_KNIGHT
     */
    public final static char CN_KNIGHT = ChessPGNVocabulary.CN_Knight;
    /**
     * CN_BISHOP
     */
    public final static char CN_BISHOP = ChessPGNVocabulary.CN_Bishop;
    /**
     * CN_QUEEN
     */
    public final static char CN_QUEEN = ChessPGNVocabulary.CN_Queen;
    /**
     * CN_KING
     */
    public final static char CN_KING = ChessPGNVocabulary.CN_King;
    
    // Player colors
    
    /**
     * WHITE
     */
    private final static char WHITE = 'w';
    /**
     * BLACK
     */
    private final static char BLACK = 'b';
    
    /**
     * All chess piece chars. For easier access and iteration. Like a enum.
     * Only uppercase needed -> chess notation.
     */
    public final static char[] pieces = new char[]
            {
                CN_PAWN, CN_ROOK, CN_KNIGHT, CN_BISHOP,
                CN_QUEEN, CN_KING
            };
    
    /**
     * All moves of the game.
     */
    private List<String> moves = new ArrayList<String>();
    
    /**
     * Regex for parsing move.
     */
    private final static Pattern REGEX_MOVE = Pattern.compile(ChessPGNVocabulary.regex_move_single_for_engine);
    
    /**
     * INVALID_INDEX is used to determine if a index computation went havoc.
     */
    private final static int INVALID_INDEX = -1;
    
    
    // initial fen values
    private int moveNr = 1;
    private int halfMoveNr = 0;
    private char playerColor = WHITE;
    private String possibleCastlings = "KQkq";
    private String possibleCastlingsForMove = "KQkq";
    private String enPassant = "-";
    
    /**
     * Constructs a new ChessBoard with the default positioning of the
     * chess figures.
     * @see #getCharChessField()
     */
    public ChessBoard()
    {
        // better construct as static field, see #getCharChessField()
    }
    
    /**
     * Constructs a new ChessBoard from a given FEN. (It should be a
     * complete FEN, not only the positioning.)
     * 
     * @param   fen     The FEN String
     * @throws IllegalArgumentException
     * @throws NumberFormatException
     */
    public ChessBoard(String fen)
        throws  IllegalArgumentException, NumberFormatException
    {
        if (fen == null)
        {
            throw new IllegalArgumentException("Parameter fen is null!");
        }
        StringTokenizer parts = new StringTokenizer(fen);
        if (parts.countTokens() != 6)
        {
            throw new IllegalArgumentException("FEN consists of 6 Parts! " +
                    "This Paramter has only "+ parts.countTokens()+ " Parts.");
        }
        StringTokenizer rows = new StringTokenizer(parts.nextToken(), "/");
        // FEN
        if (rows.countTokens() != 8)
        {
            throw new IllegalArgumentException(
                    "FEN positioning needs 8 rows!");
        }
        for (int y = 7; y >= 0; y --)
        {
            String row = rows.nextToken();
            int x = 0;
            for (char p : row.toCharArray())
            {
                if (x >= 8)
                {
                    throw new IllegalArgumentException("Too long " + y +
                        ". row!");
                }
                switch (p)
                {
                    // skip fields
                    case '8':
                    {
                        field[y][x] = EMPTY_SQUARE;
                        x ++;
                    }
                    case '7':
                    {
                        field[y][x] = EMPTY_SQUARE;
                        x ++;
                    }
                    case '6':
                    {
                        field[y][x] = EMPTY_SQUARE;
                        x ++;
                    }
                    case '5':
                    {
                        field[y][x] = EMPTY_SQUARE;
                        x ++;
                    }
                    case '4':
                    {
                        field[y][x] = EMPTY_SQUARE;
                        x ++;
                    }
                    case '3':
                    {
                        field[y][x] = EMPTY_SQUARE;
                        x ++;
                    }
                    case '2':
                    {
                        field[y][x] = EMPTY_SQUARE;
                        x ++;
                    }
                    case '1':
                    {
                        field[y][x] = EMPTY_SQUARE;
                        break;
                    }
                    // add figure
                    // TODO: need figure count?
                    case WHITE_PAWN:
                    case BLACK_PAWN:
                    case WHITE_ROOK:
                    case BLACK_ROOK:
                    case WHITE_KNIGHT:
                    case BLACK_KNIGHT:
                    case WHITE_BISHOP:
                    case BLACK_BISHOP:
                    case WHITE_QUEEN:
                    case BLACK_QUEEN:
                    case WHITE_KING:
                    case BLACK_KING:
                    {
                        field[y][x] = p;
                        break;
                    }
                    // unknown char!
                    default:
                    {
                        throw new IllegalArgumentException(
                                "Unknown char in row! " + p);
                    }
                }
                // next field
                x ++;
            }            
            // too many fields in row
            if (x != 8)
            {
                throw new IllegalArgumentException("" + y +
                    ". Row was not 8 squares long!");
            }
        }
        this.playerColor              = parts.nextToken().charAt(0); // Color
        this.possibleCastlingsForMove = parts.nextToken();     // Castlings
        this.possibleCastlings        = this.possibleCastlingsForMove; // (?)
        this.enPassant                = parts.nextToken();     // enPassant
        this.halfMoveNr = Integer.parseInt(parts.nextToken()); // half move 
        this.moveNr     = Integer.parseInt(parts.nextToken()); // move count
    }
    
    /**
     * Generates a FEN figure placement string with an additional suffix.
     * If the suffix is null it will be generated.
     * 
     * @param   suffix  String suffix added at the end.
     * @return  String with FEN
     * @see     http://de.wikipedia.org/wiki/Forsyth-Edwards-Notation
     * @see     http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     */
    @SuppressWarnings("javadoc")
    public String getFEN(String suffix)
    {
        String fen = computeFEN();
        if (suffix == null)
        {
            fen += ' ' + this.getFENSuffix();
        }
        else
        {
            fen += suffix;
        }
        return fen;
    }
    
    /**
     * Generates the complete FEN.
     * 
     * @return  String with FEN
     * @see     ChessBoard#getFEN(String)
     */
    public String getFEN()
    {
        return this.getFEN(null);
    }
    
    /**
     * Computes the FEN.
     * 
     * @return  String with fen. Only the figure's positions.
     */
    public String computeFEN()
    {
        StringBuilder ret = new StringBuilder();
        // starts from the top
        for (int y = 7; y >= 0; y --)
        {
            int emptyRow = 0;
            // starts from left
            for (int x = 0; x < 8; x ++)
            {
                // check for empty squares
                if (this.field[y][x] == EMPTY_SQUARE)
                {
                    emptyRow ++;
                }
                else
                {
                    if (emptyRow > 0)
                    {
                        // only append skipped fields if count > 0
                        ret.append(emptyRow);
                        emptyRow = 0;
                    }
                    // add figure char
                    ret.append(this.field[y][x]);
                }
            }
            // needed if complete empty row => 8 empty squares
            if (emptyRow > 0)
            {
                ret.append(emptyRow);
            }
            // each line is separated from the next with a '/' character
            ret.append('/');
        }
        // delete last '/'
        ret.deleteCharAt(ret.length()-1);
        return ret.toString();
    }
    
    /**
     * Generates a FEN suffix from the information contained in this class.
     * <br />
     * 
     * @return  String FEN suffix
     * @see     http://de.wikipedia.org/wiki/Forsyth-Edwards-Notation
     */
    @SuppressWarnings("javadoc")
    public String getFENSuffix()
    {
        // first "" needed to cast playerColor as a String not a int or byte
        // when returning it
        return "" + this.playerColor + ' ' + this.possibleCastlings + ' '
                + this.enPassant + ' ' + this.halfMoveNr + ' ' + this.moveNr;
    }
    
    /**
     * Returns the number of moves in this game so far.
     * 
     * @return  int
     */
    public int getMoveCount()
    {
        //return this.moves.size();
        return this.moveNr - 1 + this.halfMoveNr;
    }    

    /**
     * Increments the move and half-move numbers.
     */
    protected void incrementMoveNr()
    {
        if (this.halfMoveNr == 0)
        {
            // last player was white
            this.halfMoveNr ++;
            this.playerColor = BLACK;
        }
        else
        {
            // last player was black
            this.moveNr ++;
            this.halfMoveNr = 0;
            this.playerColor = WHITE;
        }
    }
    
    /**
     * Returns a character representation of the next move's player's color.
     * <br /><br />
     * An example:<br />
     * If the next move is from the white player the half move count is even
     * and the player's color is 'w'. After the move the half move count
     * increments and the next color will be 'b';
     * 
     * @return  char
     */
    public char getPlayerColor()
    {
        //return (this.halfMoveNr == 0) ? 'w' : 'b';
        return this.playerColor;
    }
    
    /**
     * Returns a character representing the figure at field[y][x].
     * 
     * @param y height
     * @param x width
     * @return  char
     */
    public char getFigureAt(int y, int x)
    {
        if ((x >= 0) && (x < 8) && (y >= 0) && (y < 8))
        {
            return this.field[y][x];
        }
        else
        {
            return EMPTY_SQUARE;
        }
    }
    
    /**
     * Returns a character representation of the figure at pos.
     * 
     * @param   pos chess position String
     * @return  char with figure or EMPTY_SQUARE if nothing or error
     */
    public char getFigureAt(String pos)
    {
        if (pos.length() == 2)
        {
            pos = pos.toLowerCase();
            int x = 0;
            int y = 0;
            try
            {
                y = Integer.parseInt(pos.substring(1));
            }
            catch (NumberFormatException e)
            {
                return EMPTY_SQUARE;
            }
            // for index
            x --;
            y --;
            switch (pos.charAt(0))
            {
                case 'h':
                    x ++;
                case 'g':
                    x ++;
                case 'f':
                    x ++;
                case 'e':
                    x ++;
                case 'd':
                    x ++;
                case 'c':
                    x ++;
                case 'b':
                    x ++;
                case 'a':
                    x ++;
                    break;
                default:
                    break;
            }
            return this.getFigureAt(y, x);
        }
        else
        {
            return EMPTY_SQUARE;
        }
    }
    
    /**
     * Converts a char in chess to a index.
     * 
     * @param   c   Char to convert.
     * @return  int or {@link #INVALID_INDEX} if something went wrong.
     */
    private int chessCharToInt(char c)
    {
        int x = INVALID_INDEX;
        switch (c)
        {
            case 'h':
                x ++;
            case 'g':
                x ++;
            case 'f':
                x ++;
            case 'e':
                x ++;
            case 'd':
                x ++;
            case 'c':
                x ++;
            case 'b':
                x ++;
            case 'a':
                x ++;
                break;
            default:
                System.out.println("Why a default clause in chessCharToInt <"
                        + c + "-" + x + ">?");
                break;
        }
        return x;
    }

    
    /**
     * Moves the figures on the board with the information contained in move.
     * 
     * @param   move    A chess move.
     * @return  true if successful parsed and moved
     * @throws NumberFormatException
     */
    public synchronized boolean move(String move)
        throws NumberFormatException
    {        
        if ((move == null) || (move.length() <= 1))
        {
            System.out.println("Got invalid move! (too short)");
            return false;
        }
        
        Matcher m = REGEX_MOVE.matcher(move);
        if (! m.find())
        {
            System.out.println("No match found for move \"" + move + "\"!");
            return false;
        }
        
        boolean isWhite = this.playerColor == WHITE;
        boolean kingSideCastling = false;
        boolean queenSideCastling = false;
        
        // --------------------------------------------------------------------
        
        // Check Castling first and return if happend
        if (m.group(16) != null)
        {
            queenSideCastling = true;
            if (isWhite)
            {
                field[0][0] = EMPTY_SQUARE;
                field[0][2] = WHITE_KING;
                field[0][3] = WHITE_ROOK;
                field[0][4] = EMPTY_SQUARE;
            }
            else
            {
                field[7][0] = EMPTY_SQUARE;
                field[7][2] = BLACK_KING;
                field[7][3] = BLACK_ROOK;
                field[7][4] = EMPTY_SQUARE;
            }
        }
        else if (m.group(15) != null)
        {
            kingSideCastling = true;
            if (isWhite)
            {
                field[0][4] = EMPTY_SQUARE;
                field[0][5] = WHITE_ROOK;
                field[0][6] = WHITE_KING;
                field[0][7] = EMPTY_SQUARE;
            }
            else
            {
                field[7][4] = EMPTY_SQUARE;
                field[7][5] = BLACK_ROOK;
                field[7][6] = BLACK_KING;
                field[7][7] = EMPTY_SQUARE;
            }            
        }
        if (queenSideCastling || kingSideCastling)
        {
            // set remaining castlings
            if (isWhite)
            {
                this.possibleCastlings = this.possibleCastlings.replace("K", "").replace("Q", "");
            }
            else
            {
                this.possibleCastlings = this.possibleCastlings.replace("k", "").replace("q", "");
            }
            
            // add move to list and finish
            this.moves.add(move);
            this.incrementMoveNr();
            return true; // finish
        }
        
        this.enPassant = "-";
        
        // --------------------------------------------------------------------
        
        int x;
        int y;
        int oldX;
        int oldY;
        char charX;
        char oldCharX;
        String pos;
        
        char curFigure = CN_PAWN;
        
        // --------------------------------------------------------------------
        // move the figures
        
        // pawn move
        if (m.group(3) != null)
        {
            // check capture
            if (m.group(4) != null)
            {
             // hit and run ...
                // => 'captured' ?
                // moved diagonal
                pos = m.group(6); // new pos - destination square
                charX = pos.charAt(0);
                oldCharX = m.group(5).charAt(0); // old x pos, column, index=0?
                
                x = chessCharToInt(charX);
                oldX = chessCharToInt(oldCharX);
                y = Integer.parseInt(pos.substring(1));
                y --;
                
                // move, no validation...
                if (isWhite)
                {
                    field[y-1][oldX] = EMPTY_SQUARE;
                    field[y][x] = WHITE_PAWN;
                }
                else
                {
                    field[y+1][oldX] = EMPTY_SQUARE;
                    field[y][x] = BLACK_PAWN;
                }
            }
            else
            {
                // check if moved two fields -> en passant
                pos = m.group(6);
                charX = pos.charAt(0);
                x = chessCharToInt(charX);
                y = Integer.parseInt(pos.substring(1));
                y --; // need index
                // on error, abort - shouldn't occur
                                    
                // get source field
                if (isWhite)
                {
                    if (field[y-1][x] == WHITE_PAWN)
                    {
                        field[y-1][x] = EMPTY_SQUARE;
                        field[y][x] = WHITE_PAWN;
                        this.enPassant = "-";
                    }
                    else
                    {
                        field[y-2][x] = EMPTY_SQUARE;
                        field[y][x] = WHITE_PAWN;
                        this.enPassant = "" + charX + y;
                    }
                }
                else
                {
                    if (field[y+1][x] == BLACK_PAWN)
                    {
                        field[y+1][x] = EMPTY_SQUARE;
                        field[y][x] = BLACK_PAWN;
                        this.enPassant = "-";
                    }
                    else
                    {
                        field[y+2][x] = EMPTY_SQUARE;
                        field[y][x] = BLACK_PAWN;
                        this.enPassant = "" + charX + (y + 2);
                    }
                }
            }
            
            // check promotion and overwrite destination field if needed
            if (m.group(7) != null)
            {
                // was transformed
                // 'promoted' ?
                field[y][x] = m.group(8).charAt(0); // no check so far
            }
        }
        // --------------------------------------------------------------------
        // move of other figures
        else if (m.group(9) != null)
        {
            // get figure
            curFigure = m.group(10).charAt(0);
            char figure = (isWhite) ? curFigure : Character.toLowerCase(curFigure);
            
            // get destination position
            pos = m.group(14);
            charX = pos.charAt(0);
            x = chessCharToInt(charX);
            y = Integer.parseInt(pos.substring(1));
            y --;
            
            // get source position - hopefully safe ...
            try
            {
                pos = m.group(11); // column -> x
                oldX = (pos == null) ? INVALID_INDEX : chessCharToInt(pos.charAt(0));
                pos = m.group(12); // row -> y
                oldY = (pos == null) ? INVALID_INDEX : Integer.parseInt(pos) - 1;
            }
            catch (Exception e)
            {
                System.out.println("Error while parsing source field of move \""
                        + move + "\"!");
                e.printStackTrace();
                oldX = INVALID_INDEX;
                oldY = INVALID_INDEX;
            }
            
            // ----------------------------------------------------------------
            // compute figure moves ...
            
            // common to all -> set figure to destination square
            field[y][x] = figure;
            
            switch (curFigure)
            {
                case CN_ROOK:
                {
                    if (oldX != INVALID_INDEX)
                    {
                        field[y][oldX] = EMPTY_SQUARE;
                    }
                    else if (oldY != INVALID_INDEX)
                    {
                        field[oldY][x] = EMPTY_SQUARE;
                    }
                    // have to search manually ... :(
                    else
                    {
                        boolean found = false;
                        // search left
                        for (int i = x - 1; ! found && i >= 0; i --)
                        {
                            if (field[y][i] == figure)
                            {
                                field[y][i] = EMPTY_SQUARE;
                                found = true;
                                oldY = y;
                                oldX = i;
                                break;
                            }
                        }
                        // search right
                        for (int i = x + 1; ! found && i < 8; i ++)
                        {
                            if (field[y][i] == figure)
                            {
                                field[y][i] = EMPTY_SQUARE;
                                found = true;
                                oldY = y;
                                oldX = i;
                                break;
                            }
                        }
                        // search top
                        for (int i = y + 1; ! found && i < 8; i ++)
                        {
                            if (field[i][x] == figure)
                            {
                                field[i][x] = EMPTY_SQUARE;
                                found = true;
                                oldY = i;
                                oldX = x;
                                break;
                            }
                        }
                        // search bottom
                        for (int i = y - 1; ! found && i >= 0; i --)
                        {
                            if (field[i][x] == figure)
                            {
                                field[i][x] = EMPTY_SQUARE;
                                found = true;
                                oldY = i;
                                oldX = x;
                                break;
                            }
                        }
                        if (! found)
                        {
                            System.out.println("Don't tell me you couldn't " +
                                    "find a single chess piece ... I'm dis" +
                                    "appointed ... \"" + move + "\"");
                        }
                        else
                        {
                         // set castlings
                            if (isWhite)
                            {
                                // kingside ?
                                if (oldX == 7)
                                {
                                    this.possibleCastlings = this.possibleCastlings.replace("K", "");
                                }
                                // queenside ?
                                else if (oldX == 0)
                                {
                                    this.possibleCastlings = this.possibleCastlings.replace("Q", "");
                                }
                            }
                            else
                            {
                                // kingside ?
                                if (oldX == 7)
                                {
                                    this.possibleCastlings = this.possibleCastlings.replace("k", "");
                                }
                                // queenside ?
                                else if (oldX == 0)
                                {
                                    this.possibleCastlings = this.possibleCastlings.replace("q", "");
                                }
                            }
                        }
                    }
                    break;
                }
                // ------------------------------------------------------------
                case CN_KNIGHT:
                {
                    int diff;
                    
                    // x is given -> only two possibilities for y
                    if (oldX != INVALID_INDEX)
                    {
                        diff = (oldX > x) ? oldX - x : x - oldX;
                        
                        // moved one in the x direction (left or right)
                        if (diff == 1)
                        {
                            // -> has to move 2 in y direction
                            if ((y + 2 < 8) && (field[y+2][oldX] == figure))
                            {
                                field[y+2][oldX] = EMPTY_SQUARE;
                            }
                            else if ((y - 2 >= 0) && (field[y-2][oldX] == figure))
                            {
                                field[y-2][oldX] = EMPTY_SQUARE;
                            }
                            else
                            {
                                System.out.println("Can't reach here ... \"" +
                                        move + "\"!");
                            }
                        }
                        // moved two in the x direction
                        else if (diff == 2)
                        {
                            // has to move 1 in the y direction
                            if ((y + 1 < 8) && (field[y+1][oldX] == figure))
                            {
                                field[y+1][oldX] = EMPTY_SQUARE;
                            }
                            else if ((y - 1 >= 0) && (field[y-1][oldX] == figure))
                            {
                                field[y-1][oldX] = EMPTY_SQUARE;
                            }
                            else
                            {
                                System.out.println("Can't reach here ... \"" +
                                        move + "\"!");
                            }
                        }
                        // more than two difference is not possible
                        else
                        {
                            System.out.println("Can't reach here ... \"" +
                                    move + "\"!");
                        }
                    }
                    // y is given -> only two possibilities for x
                    else if (oldY != INVALID_INDEX)
                    {
                        diff = (oldY > y) ? oldY - x : y - oldY;
                        
                        // moved one in the y direction (left or right)
                        if (diff == 1)
                        {
                            // -> has to move 2 in x direction
                            if ((x + 2 < 8) && (field[oldY][x+2] == figure))
                            {
                                field[oldY][x+2] = EMPTY_SQUARE;
                            }
                            else if ((x - 2 >= 0) && (field[oldY][x-2] == figure))
                            {
                                field[oldY][x-2] = EMPTY_SQUARE;
                            }
                            else
                            {
                                System.out.println("Can't reach here ... \"" +
                                        move + "\"!");
                            }
                        }
                        // moved two in the y direction
                        else if (diff == 2)
                        {
                            // has to move 1 in the x direction
                            if ((x + 1 < 8) && (field[oldY][x+1] == figure))
                            {
                                field[oldY][x+1] = EMPTY_SQUARE;
                            }
                            else if ((x - 1 >= 0) && (field[oldY][x-1] == figure))
                            {
                                field[oldY][x-1] = EMPTY_SQUARE;
                            }
                            else
                            {
                                System.out.println("Can't reach here ... \"" +
                                        move + "\"!");
                            }
                        }
                        // more than two difference is not possible
                        else
                        {
                            System.out.println("Can't reach here ... \"" +
                                    move + "\"!");
                        }
                    }
                    else
                    {
                        // have to check all movement possibilities
                        if ((x + 2 < 8) && (y + 1 < 8) && (field[y+1][x+2] == figure))
                        {
                            field[y+1][x+2] = EMPTY_SQUARE;
                        }
                        else if ((x - 2 >= 0) && (y + 1 < 8) && (field[y+1][x-2] == figure))
                        {
                            field[y+1][x-2] = EMPTY_SQUARE;
                        }
                        else if ((x + 2 < 8) && (y - 1 >= 0) && (field[y-1][x+2] == figure))
                        {
                            field[y-1][x+2] = EMPTY_SQUARE;
                        }
                        else if ((x - 2 >= 0) && (y - 1 >= 0) && (field[y-1][x-2] == figure))
                        {
                            field[y-1][x-2] = EMPTY_SQUARE;
                        }
                        else if ((y + 2 < 8) && (x + 1 < 8) && (field[y+2][x+1] == figure))
                        {
                            field[y+2][x+1] = EMPTY_SQUARE;
                        }
                        else if ((y - 2 >= 0) && (x + 1 < 8) && (field[y-2][x+1] == figure))
                        {
                            field[y-2][x+1] = EMPTY_SQUARE;
                        }
                        else if ((y + 2 < 8) && (x - 1 >= 0) && (field[y+2][x-1] == figure))
                        {
                            field[y+2][x-1] = EMPTY_SQUARE;
                        }
                        else if ((y - 2 >= 0) && (x - 1 >= 0) && (field[y-2][x-1] == figure))
                        {
                            field[y-2][x-1] = EMPTY_SQUARE;
                        }
                        else
                        {
                            System.out.println("Can't reach here ... \"" +
                                    move + "\"! ... Super Knight ;)");
                        }
                    }
                    break;
                }
                // ------------------------------------------------------------
                case CN_BISHOP:
                {
                    if (oldX != INVALID_INDEX)
                    {
                        System.out.println("BISHOP MOVE POSSIBLE ?? \"" + move + "\"");
                    }
                    else if (oldY != INVALID_INDEX)
                    {
                        System.out.println("BISHOP MOVE POSSIBLE ?? \"" + move + "\"");
                    }
                    else
                    {
                        boolean found = false;
                        
                        // search diagonal
                        // search bottom left
                        for (int i = x - 1, j = y - 1; ! found && i >= 0 && j >= 0; i --, j -- )
                        {
                            if (field[j][i] == figure)
                            {
                                field[j][i] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        // search top left
                        for (int i = x - 1, j = y + 1; ! found && i >= 0 && j < 8; i --, j ++ )
                        {
                            if (field[j][i] == figure)
                            {
                                field[j][i] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        // search bottom right
                        for (int i = x + 1, j = y - 1; ! found && i < 8 && j >= 0; i ++, j -- )
                        {
                            if (field[j][i] == figure)
                            {
                                field[j][i] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        // search top right
                        for (int i = x + 1, j = y + 1; ! found && i < 8 && j < 8; i ++, j ++ )
                        {
                            if (field[j][i] == figure)
                            {
                                field[j][i] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        
                        if (! found)
                        {
                            System.out.println("OMG ... I can't find anything" +
                                    " ... again : \"" + move + "\"");
                        }
                    }
                    break;
                }
                // ------------------------------------------------------------
                case CN_QUEEN:
                {
                    if (oldX != INVALID_INDEX)
                    {
                        field[y][oldX] = EMPTY_SQUARE;
                    }
                    else if (oldY != INVALID_INDEX)
                    {
                        field[oldY][x] = EMPTY_SQUARE;
                    }
                    // check all directions
                    else
                    {
                        boolean found = false;
                        
                        // search vertical and horizontal
                        // search left
                        for (int i = x - 1; ! found && i >= 0; i --)
                        {
                            if (field[y][i] == figure)
                            {
                                field[y][i] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        // search right
                        for (int i = x + 1; ! found && i < 8; i ++)
                        {
                            if (field[y][i] == figure)
                            {
                                field[y][i] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        // search top
                        for (int i = y + 1; ! found && i < 8; i ++)
                        {
                            if (field[i][x] == figure)
                            {
                                field[i][x] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        // search bottom
                        for (int i = y - 1; ! found && i >= 0; i --)
                        {
                            if (field[i][x] == figure)
                            {
                                field[i][x] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        
                        // ----------------------------------------------------
                        // search diagonal
                        
                        // search bottom left
                        for (int i = x - 1, j = y - 1; ! found && i >= 0 && j >= 0; i --, j -- )
                        {
                            if (field[j][i] == figure)
                            {
                                field[j][i] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        // search top left
                        for (int i = x - 1, j = y + 1; ! found && i >= 0 && j < 8; i --, j ++ )
                        {
                            if (field[j][i] == figure)
                            {
                                field[j][i] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        // search bottom right
                        for (int i = x + 1, j = y - 1; ! found && i < 8 && j >= 0; i ++, j -- )
                        {
                            if (field[j][i] == figure)
                            {
                                field[j][i] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        // search top right
                        for (int i = x + 1, j = y + 1; ! found && i < 8 && j < 8; i ++, j ++ )
                        {
                            if (field[j][i] == figure)
                            {
                                field[j][i] = EMPTY_SQUARE;
                                found = true;
                                break;
                            }
                        }
                        
                        if (! found)
                        {
                            System.out.println("Don't tell me you couldn't " +
                                    "find a single chess piece ... I'm dis" +
                                    "appointed ... again : \"" + move + "\"");
                        }
                    }
                    break;
                }
                // ------------------------------------------------------------
                case CN_KING:
                {
                    // set old field to empty square ...
                    // from left
                    if ((x - 1 >= 0) && (field[y][x-1] == figure))
                    {
                        field[y][x-1] = EMPTY_SQUARE;
                    }
                    // from left bottom
                    else if ((y - 1 >= 0) && (x - 1 >= 0) && (field[y-1][x-1] == figure))
                    {
                        field[y-1][x-1] = EMPTY_SQUARE;
                    }
                    // from bottom
                    else if ((y - 1 >= 0) && (field[y-1][x] == figure))
                    {
                        field[y-1][x] = EMPTY_SQUARE;
                    }
                    // from right
                    else if ((x + 1 < 8) && (field[y][x+1] == figure))
                    {
                        field[y][x+1] = EMPTY_SQUARE;
                    }
                    // from right top
                    else if ((y + 1 < 8) && (x + 1 < 8) && (field[y+1][x+1] == figure))
                    {
                        field[y+1][x+1] = EMPTY_SQUARE;
                    }
                    // from top
                    else if ((y + 1 < 8) && (field[y+1][x] == figure))
                    {
                        field[y+1][x] = EMPTY_SQUARE;
                    }
                    // from top left
                    else if ((y + 1 < 8) && (x - 1 >= 0) && (field[y+1][x-1] == figure))
                    {
                        field[y+1][x-1] = EMPTY_SQUARE;
                    }
                    // from bottom right
                    else if ((y - 1 >= 0) && (x + 1 < 8) && (field[y-1][x+1] == figure))
                    {
                        field[y-1][x+1] = EMPTY_SQUARE;
                    }
                    else
                    {
                        System.out.println("Huh? Where came I from? \"" +
                                move + "\"");
                    }
                    break;
                }
                // error case, abort
                default:
                {
                    System.out.println("Unrecognized figure \"" + curFigure +
                            "\" in move \"" + move + "\"!");
                   return false; 
                }   
            }
        }
        
        // --------------------------------------------------------------------
        
        // not used yet
        if (m.group(17) != null)
        {
            // '+' check
            // '#' check mate, last move
        }
        
        this.moves.add(move);
        this.incrementMoveNr();
        return true;
    }
    
    /**
     * Moves the figures on the chess board and returns the computed FEN
     * String.
     * 
     * @param   move    Move to do.
     * @return  String with FEN
     * @throws  NumberFormatException
     */
    public String moveAndGetFEN(String move)
        throws NumberFormatException
    {
        this.move(move);
        return this.getFEN(null);
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ChessBoard [field=").append(Arrays.toString(field))
                .append(", moves=").append(moves).append(", moveNr=")
                .append(moveNr).append(", halfMoveNr=").append(halfMoveNr)
                .append(", playerColor=").append(playerColor)
                .append(", possibleCastlings=").append(possibleCastlings)
                .append(", possibleCastlingsForMove=")
                .append(possibleCastlingsForMove).append(", enPassant=")
                .append(enPassant).append(", computeFEN()=")
                .append(computeFEN()).append(", getFENSuffix()=")
                .append(getFENSuffix()).append(", getMoveCount()=")
                .append(getMoveCount()).append("]");
        return builder.toString();
    }
    
    


    /**
     * Creates a new 2-dimensional character based chess field with all chess
     * figures on their start squares.
     * 
     * @return  char[][]
     */
    public final static char[][] getCharChessField()
    {
        // white figures are uppercase, black ones lowercase.
        // base: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR
        return new char[][]
            {
                {
                    WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN,
                    WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK 
                },
                {
                    WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN,
                    WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN
                },
                {
                    EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE,
                    EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE
                },
                {
                    EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE,
                    EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE
                },
                {
                    EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE,
                    EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE
                },
                {
                    EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE,
                    EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE, EMPTY_SQUARE
                },
                {
                    BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN,
                    BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN
                },
                {
                    BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN,
                    BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK
                }
            };
    }
}
