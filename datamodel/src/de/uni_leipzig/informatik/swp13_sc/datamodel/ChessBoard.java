/**
 * ChessBoard.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessColor;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigure;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigureType;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPosition;

import java.lang.IllegalArgumentException;
import java.lang.StringBuilder;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard
{
    private ChessFigure[][] field = new ChessFigure[8][8];
    private List<ChessFigure> figuresLeft = new ArrayList<ChessFigure>();
    private List<ChessMove> moves = new ArrayList<ChessMove>();
    private boolean canUseFigurePositions = false;
    
    public ChessBoard()
    {
        this.canUseFigurePositions = true;
        for (ChessFigure f : ChessFigure.values())
        {
            ChessPosition pos = f.getStartPosition();
            this.field[pos.getPosY()][pos.getPosX()] = f;
            this.figuresLeft.add(f);
        }
        // Fields left should be null
        //this.figuresLeft.addAll(ChessFigure.values());
    }
    
    public ChessBoard(String fen)
        throws IllegalArgumentException
    {
        // TODO: create ChessBoard with FEN
        //       set & move Figures, create Moves
        String position;
        if (fen.indexOf(' ') == -1)
        {
            position = fen;
        }
        else
        {
            position = fen.substring(0, fen.indexOf(' ')); // TODO: check!
        }
        String[] parts = position.split("/");
        if (parts.length != 8)
        {
            // ERROR
            // TODO: what now?
            throw new IllegalArgumentException("Not 8 Rows in FEN: " + position + ", orig: " + fen);
        }
        
        List<ChessFigure> fig = new ArrayList<ChessFigure>();
        for (ChessFigure f : ChessFigure.values())
        {
            fig.add(f);
        } // addAll() ???
        this.canUseFigurePositions = false;
        for (int y = 7; y >= 0; y --)
        {
            String row = parts[y];
            int x = 0;
            for (char p : row.toCharArray())
            {
                if (x >= 8)
                {
                    throw new IllegalArgumentException("Too long row! - " + row + " in " + fen);
                }
                switch (p)
                {
                    case '8':
                        // this.field[y][x] = null; // should be null
                        x ++; 
                    case '7':
                        x ++;
                    case '6':
                        x ++;
                    case '5':
                        x ++;
                    case '4':
                        x ++;
                    case '3':
                        x ++;
                    case '2':
                        x ++;
                    case '1':
                        x ++;
                        break;
                    case 'P':
                        // TODO: which Pawn ???
                        //       Add some Pawns without Positioning?
                        //       need some next fig function ...
                        break;
                    case 'p':
                        break;
                    case 'R':
                        break;
                    case 'r':
                        break;
                    case 'N':
                        break;
                    case 'n':
                        break;
                    case 'B':
                        break;
                    case 'b':
                        break;
                    case 'Q':
                        if (fig.remove(ChessFigure.WhiteQueen))
                        {
                            this.field[y][x] = ChessFigure.WhiteQueen;
                        }
                        else
                        {
                            throw new IllegalArgumentException("Multiple Queens! - " + row + " in " + fen);
                        }
                        break;
                    case 'q':
                        if (fig.remove(ChessFigure.BlackQueen))
                        {
                            this.field[y][x] = ChessFigure.BlackQueen;
                        }
                        else
                        {
                            throw new IllegalArgumentException("Multiple black Queens! - " + row + " in " + fen);
                        }
                        break;
                    case 'K':
                        break;
                    case 'k':
                        break;
                    default:
                        // should not happen
                        throw new IllegalArgumentException("Unknown Character: " + p + " in " + row + " in " + fen);
                }
            }
        }
    }
    
    
    private void move(ChessMove move)
    {
        // TODO: move on board
    }
    
    public void setMove(ChessMove move)
    {
        if (move != null)
        {
            this.moves.add(move);
            move(move);
        }
    }
    
    public List<ChessMove> getMoves()
    {
        return this.moves;
    }
    
    public boolean hasMoves()
    {
        return (! this.moves.isEmpty());
    }
    
    public String getGBR()
    {
        // TODO: add
        return null;
    }
    
    public String getFEN()
    {
        StringBuilder ret = new StringBuilder();
        for (int y = 7; y >= 0; y --)
        {
            int emptyRow = 0;
            for (int x = 0; x < 8; x ++)
            {
               ChessFigure f = this.field[y][x];
               if (f == null)
               {
                   emptyRow ++;
               }
               else
               {
                   ret.append(emptyRow);
                   emptyRow = 0;
                   char ft;
                   switch (f.getFigureType())
                   {
                       case Pawn:
                           ft = (f.getColor().equals(ChessColor.White)) ? 'P' : 'p';
                           break;
                       case Rook:
                           ft = (f.getColor().equals(ChessColor.White)) ? 'R' : 'r';
                           break;
                       case Knight:
                           ft = (f.getColor().equals(ChessColor.White)) ? 'N' : 'n';
                           break;
                       case Bishop:
                           ft = (f.getColor().equals(ChessColor.White)) ? 'B' : 'b';
                           break;
                       case Queen:
                           ft = (f.getColor().equals(ChessColor.White)) ? 'Q' : 'q';
                           break;
                       case King:
                           ft = (f.getColor().equals(ChessColor.White)) ? 'K' : 'k';
                           break;
                       default:
                           // should not happen
                           ft = 'x';
                           break;
                   }
                   ret.append(ft);
               }
            }
            ret.append('/');
        }
        ret.deleteCharAt(ret.length()-1);
        return ret.toString();
    }
    
    public static ChessBoard getChessBoardFromFEN(String fen)
    {
        // TODO: add
        // direct access ...
        return null;
    }
    
    public static ChessBoard getChessBoardFromGBR(String gbr)
    {
        // TODO: add
        return null;
    }
    
    public static String getFENFromChessBoard(ChessBoard cb)
    {
        return cb.getFEN();
    }
    
    public static String getGBRFromChessBoard(ChessBoard cb)
    {
        return cb.getGBR();
    }
    
    public static String convertFEN2GBR(String fen)
    {
        return ChessBoard.getChessBoardFromFEN(fen).getGBR();
    }
    
    public static String convertGBR2FEN(String gbr)
    {
        // or longer:
        return ChessBoard.getFENFromChessBoard(ChessBoard.getChessBoardFromGBR(gbr));
    }
}
