/**
 * ChessBoard.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

//import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;

import java.lang.StringBuilder;

import java.util.ArrayList;
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
    private char[][] field = new char[8][8];
    /**
     * EMPTY_SQUARE<br />
     * Marks a empty square on a chess board or error.
     */
    public final static char EMPTY_SQUARE = 0x00;
    private List<String> moves = new ArrayList<String>();
    // initial fen values
    private int moveNr = 1;
    private int halfMoveNr = 0;
    private char playerColor = 'w';
    private String possibleCastlings = "KQkq";
    private String enPassant = "-";
    
    /**
     * Constructs a new ChessBoard with the default positioning of the
     * chess figures.
     */
    public ChessBoard()
    {
        // white figures are uppercase, black ones lowercase.
        // base: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR
        this.field[7][0] = 'r';
        this.field[7][1] = 'n';
        this.field[7][2] = 'b';
        this.field[7][3] = 'q';
        this.field[7][4] = 'k';
        this.field[7][5] = 'b';
        this.field[7][6] = 'n';
        this.field[7][7] = 'r';
        
        for (int x = 0; x < 8; x ++)
        {
            this.field[6][x] = 'p';
            this.field[5][x] = EMPTY_SQUARE;
            this.field[4][x] = EMPTY_SQUARE;
            this.field[3][x] = EMPTY_SQUARE;
            this.field[2][x] = EMPTY_SQUARE;
            this.field[1][x] = 'P';
        }
        
        this.field[0][0] = 'R';
        this.field[0][1] = 'N';
        this.field[0][2] = 'B';
        this.field[0][3] = 'Q';
        this.field[0][4] = 'K';
        this.field[0][5] = 'B';
        this.field[0][6] = 'N';
        this.field[0][7] = 'R';
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
            fen += this.getFENSuffix();
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
     * 
     * @param   move    The 
     * @return  FEN suffix
     * @see     http://de.wikipedia.org/wiki/Forsyth-Edwards-Notation
     */
    public String getFENSuffix()
    {        
        return this.playerColor + ' ' + this.possibleCastlings + ' '
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
        return this.possibleCastlings;
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
            this.playerColor = 'b';
        }
        else
        {
            // last player was black
            this.moveNr ++;
            this.halfMoveNr = 0;
            this.playerColor = 'w';
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
            int x = -1;
            int y = Integer.parseInt(pos.substring(1));
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
     * Moves the figures on the board with the information contained in move.
     * 
     * @param   move    A chess move.
     */
    public synchronized void move(String move)
    {
        // TODO: validity check
        this.moves.add(move);
        this.incrementMoveNr();
        
        // TODO: code ...
        // parse move
        // predict source field
        // move piece
        // set castlings & enPassant
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
}
