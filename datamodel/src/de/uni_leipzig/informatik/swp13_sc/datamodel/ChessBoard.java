/**
 * ChessBoard.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

//import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;

import java.lang.StringBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A virtual chess board. Needed for computing FENs.
 *
 * @author Erik
 *
 */
public class ChessBoard
{
    // TODO: how to initialize? Or better way?
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
    public final static char CN_PAWN = 'P';
    /**
     * CN_ROOK
     */
    public final static char CN_ROOK = 'R';
    /**
     * CN_KNIGHT
     */
    public final static char CN_KNIGHT = 'N';
    /**
     * CN_BISHOP
     */
    public final static char CN_BISHOP = 'B';
    /**
     * CN_QUEEN
     */
    public final static char CN_QUEEN = 'Q';
    /**
     * CN_KING
     */
    public final static char CN_KING = 'K';
    
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
    
    private List<String> moves = new ArrayList<String>();
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
     * Generates a FEN figure placement string with an additional suffix.
     * If the suffix is null it will be generated.
     * 
     * @param   suffix  String suffix added at the end.
     * @return  String with FEN
     * @see     http://de.wikipedia.org/wiki/Forsyth-Edwards-Notation
     * @see     http://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     */
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
                if (this.field[y][y] == EMPTY_SQUARE)
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
    public String getFENSuffix()
    {
        // first "" needed to cast playerColor as a String not a int or byte
        // when returning it
        return "" + this.playerColor + ' ' + this.possibleCastlings + ' '
                + this.enPassant + ' ' + this.halfMoveNr + ' ' + this.moveNr;
    }
    
    
    /**
     * Returns the possible castlings for this positioning.
     * 
     * @return  String
     */
    public String getPossibleCastlings()
    {
        // TODO: see how to check
        //       or compute in move()
        return this.possibleCastlingsForMove;
    }
    
    /**
     * Returns the number of moves in this game so far.
     * 
     * @return  int
     */
    public int getMoveCount()
    {
        //return this.moves.size();
        return this.moveNr - this.halfMoveNr;
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
     * @return  int
     */
    private int chessCharToInt(char c)
    {
        int x = -1;
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
                break;
        }
        return x;
    }

    
    /**
     * Moves the figures on the board with the information contained in move.
     * 
     * @param   move    A chess move.
     */
    public synchronized void move(String move)
        throws NumberFormatException
    {
        if ((move == null) || (move.length() <= 1))
        {
            return;
        }
        
        boolean isWhite = this.playerColor == 'w';
        boolean kingSideCastling = false;
        boolean queenSideCastling = false;
        
        // Check Castling first and return if happend
        if (move.equalsIgnoreCase("O-O"))
        {
            kingSideCastling = true;
            // move & set class variables above
        }
        else if (move.equalsIgnoreCase("O-O-O"))
        {
            queenSideCastling = true;
            // move & set class variables above
        }
        if (queenSideCastling || kingSideCastling)
        {
            // set remaining castlings
            if (isWhite)
            {
                if (this.possibleCastlings.length() == 4)
                {
                    this.possibleCastlings = "kq";
                }
                else // (this.possibleCastlings.length() == 2)
                {
                    this.possibleCastlings = "";
                }
            }
            else
            {
                if (this.possibleCastlings.length() == 4)
                {
                    this.possibleCastlings = "KQ";
                }
                else // (this.possibleCastlings.length() == 2)
                {
                    this.possibleCastlings = "";
                }
            }
            
            // add move to list and finish
            this.moves.add(move);
            this.incrementMoveNr();
            return; // finish
        }
        
        // --------------------------------------------------------------------
        
        int i;
        int y;
        int oldY;
        int x;
        int oldX;
        char charX;
        char oldCharX;
        String pos;
        char curFigure = move.charAt(0);
        
        // get the figure
        if (Character.isUpperCase(curFigure))
        {
            // remove first
            move = move.substring(1);
        }
        else
        {
            // implicit pawn
            curFigure = CN_PAWN;
        }
        
        // --------------------------------------------------------------------
        
        // move the figures
        switch (curFigure)
        {
            case CN_PAWN:
            {
                // TODO: have to check ':', too?
                if ((i = move.indexOf('x')) != -1)
                {
                    // hit and run ...
                    // 'captured' ?
                    // moved diagonal
                    pos = move.substring(i+1, i+2+1); // new pos
                    charX = pos.charAt(0);
                    oldCharX = move.charAt(i-1); // old x pos, column
                    x = chessCharToInt(charX);
                    y = Integer.parseInt(pos.substring(1));
                    
                }
                else
                {
                    // moved vertical / or implicitly has moved diagonal?
                    // check if moved two fields -> en passant
                    pos = move.substring(0, 2);
                    charX = pos.charAt(0);
                    x = chessCharToInt(charX);
                    y = Integer.parseInt(pos.substring(1)); // on error, abort
                                        
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
                            // ((field[y-1][x] == EMPTY_SQUARE) &&
                            // (field[y-2][x] == WHITE_PAWN))
                            field[y-2][x] = EMPTY_SQUARE;
                            field[y][x] = WHITE_PAWN;
                            this.enPassant = "" + charX + "" + (y-1);
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
                            // ((field[y+1][x] == EMPTY_SQUARE) &&
                            // (field[y+2][x] == BLACK_PAWN))
                            field[y+2][x] = EMPTY_SQUARE;
                            field[y][x] = BLACK_PAWN;
                            this.enPassant = "" + charX + "" + (y+1);
                        }
                    }
                }
                if ((i = move.indexOf('=')) != -1)
                {
                    // was transformed
                    // 'promoted' ?
                }
                break;
            }
            case CN_ROOK:
            {
                
            }
            case CN_KNIGHT:
            {
                
            }
            case CN_BISHOP:
            {
                
            }
            case CN_QUEEN:
            {
                
            }
            case CN_KING:
            {
                
            }
            // error case, abort
            default:
            {
               return; 
            }   
        }
        
        // --------------------------------------------------------------------
        
        // not used yet
        if (move.indexOf('+') != -1)
        {
            // check
        }
        else if (move.indexOf('#') != -1)
        {
            // check mate
            // last move
        }
        
        // TODO: validity check ?
        this.moves.add(move);
        this.incrementMoveNr();
    }
    
    /**
     * Moves the figures on the chess board and returns the computed FEN
     * String.
     * 
     * @param   move    Move to do.
     * @return  String with FEN
     */
    public String moveAndGetFEN(String move)
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
