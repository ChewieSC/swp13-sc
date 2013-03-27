/**
 * ChessBoard.java
 */

package de.uni_leipzig.informatik.swp13_sc.datamodel;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessMove;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard
{
    // TODO: how to initialize?
    private char[][] field = new char[8][8];
    private final char EMPTY_SQUARE = 0x00;
    private List<String> moves = new ArrayList<String>();
    
    public ChessBoard()
    {
        // TODO: check position of figures
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
    
    public String getFEN(String prefix)
    {
        return "";
    }
    
    public String getFENPrefix(String move)
    {
        // TODO: add code
        
        // color
        // special ...
        
        return "";
    }
    
    public void move(String move)
    {
        // TODO: add some code
        
        // parse move
        // predict source field
        // move piece
    }
    
    public String moveAndGetFEN(String move)
    {
        this.move(move);
        return this.getFEN(this.getFENPrefix(move));
    }
}
