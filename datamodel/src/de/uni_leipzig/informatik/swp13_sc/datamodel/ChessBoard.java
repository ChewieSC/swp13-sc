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
import java.util.Iterator;
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
            // TODO: what now?
            throw new IllegalArgumentException("Not 8 Rows in FEN: " + position + ", orig: " + fen);
        }
        
        // need it for checking ...
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
                    // empty fields
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
                    
                    // fields with figure on it
                    case 'P':
                        this.field[y][x] = getFigure(fig, ChessFigureType.Pawn, ChessColor.White, row);
                        break;
                    case 'p':
                        this.field[y][x] = getFigure(fig, ChessFigureType.Pawn, ChessColor.Black, row);
                        break;
                    case 'R':
                        this.field[y][x] = getFigure(fig, ChessFigureType.Rook, ChessColor.White, row);
                        break;
                    case 'r':
                        this.field[y][x] = getFigure(fig, ChessFigureType.Rook, ChessColor.Black, row);
                        break;
                    case 'N':
                        this.field[y][x] = getFigure(fig, ChessFigureType.Knight, ChessColor.White, row);
                        break;
                    case 'n':
                        this.field[y][x] = getFigure(fig, ChessFigureType.Knight, ChessColor.Black, row);
                        break;
                    case 'B':
                        this.field[y][x] = getFigure(fig, ChessFigureType.Bishop, ChessColor.White, row);
                        break;
                    case 'b':
                        this.field[y][x] = getFigure(fig, ChessFigureType.Bishop, ChessColor.Black, row);
                        break;
                    case 'Q':
                        this.field[y][x] = getFigure(fig, ChessFigureType.Queen, ChessColor.White, row);
                        break;
                    case 'q':
                        this.field[y][x] = getFigure(fig, ChessFigureType.Queen, ChessColor.Black, row);
                        break;
                    case 'K':
                        this.field[y][x] = getFigure(fig, ChessFigureType.King, ChessColor.White, row);
                        break;
                    case 'k':
                        this.field[y][x] = getFigure(fig, ChessFigureType.King, ChessColor.Black, row);
                        break;
                    default:
                        // should not happen
                        throw new IllegalArgumentException("Unknown Character: " + p + " in " + row + " in " + fen);
                }
            }
        }
    }
    
    private ChessFigure getFigure(List<ChessFigure> list, ChessFigureType type, ChessColor color, String msg)
        throws IllegalArgumentException
    {
        Iterator<ChessFigure> iter = list.iterator();
        while (iter.hasNext())
        {
            ChessFigure f = iter.next();
            if (f.getColor().equals(color))
            {
                if (f.getFigureType().equals(type))
                {
                    iter.remove();
                    return f;
                }
            }
        }
        throw new IllegalArgumentException("Too many " + color + " " + type + "(s) ! - " + msg);
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
        
        // 1st count figures
        // put them in list
        // complete.
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
        return new ChessBoard(fen);
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
