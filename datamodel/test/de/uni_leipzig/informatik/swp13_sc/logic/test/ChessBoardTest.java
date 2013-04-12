/**
 * ChessBoardTest.java
 */
package de.uni_leipzig.informatik.swp13_sc.logic.test;

import static org.junit.Assert.*;

import org.junit.Test;

import de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard;

/**
 * 
 *
 * @author Erik
 *
 */
public class ChessBoardTest
{
    /**
     * EMPTY_SQUARE marks an empty square on a chess board
     */
    public final static char EMPTY_SQUARE = ChessBoard.EMPTY_SQUARE;
    
    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#ChessBoard()}.
     */
    @Test
    public final void testChessBoard()
    {
        ChessBoard cb1 = new ChessBoard();
        ChessBoard cb2 = new ChessBoard();
        
        assertNotSame(cb1, cb2);
        
        // TODO: what to test???
    }
    
    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#ChessBoard()}.
     */
    @Test
    public final void testChessBoardString()
    {
        ChessBoard cb1 = new ChessBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", cb1.getFEN(null));
        //assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNr w KQkq - 0 1", cb1.getFEN(null)); //fails
        
        // TODO: what to test???
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#getFEN(java.lang.String)}.
     */
    @Test
    public final void testGetFEN()
    {
        ChessBoard cb = new ChessBoard();
        String suffix = "my own suffix";
        
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", cb.getFEN(null));
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", cb.getFEN(""));        
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR" + suffix, cb.getFEN(suffix));
        
        // should always work ...
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#computeFEN()}.
     */
    @Test
    public final void testComputeFEN()
    {
        ChessBoard cbtemp = new ChessBoard();
        
        // Check base positioning
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", cbtemp.computeFEN());
        
        // TODO: add some moves add check then
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#getFENSuffix()}.
     */
    @Test
    public final void testGetFENSuffix()
    {
        ChessBoard cb = new ChessBoard();
        
        // Test start suffix
        assertEquals("w KQkq - 0 1", cb.getFENSuffix());
        
        // TODO: move and test later
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#getPossibleCastlings()}.
     */
    @Test
    public final void testGetPossibleCastlings()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#getMoveCount()}.
     */
    @Test
    public final void testGetMoveCount()
    {
        ChessBoard cb = new ChessBoard();
        
        assertEquals(0, cb.getMoveCount());
        // TODO: same as #testIncrementMoveNr()
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#incrementMoveNr()}.
     */
    @SuppressWarnings("javadoc")
    @Test
    public final void testIncrementMoveNr()
    {
        ChessBoard cb = new ChessBoard();
        
        assertEquals(0, cb.getMoveCount());

        // TODO: add some moves andd check move count
        //       maybe parse FEN-Suffix for move and half move
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#getPlayerColor()}.
     */
    @Test
    public final void testGetPlayerColor()
    {
        ChessBoard cb = new ChessBoard();
        
        assertEquals('w', cb.getPlayerColor());
        // TODO: add some moves and check later.
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#getFigureAt(int, int)}.
     */
    @Test
    public final void testGetFigureAtIntInt()
    {
        ChessBoard cb = new ChessBoard();
        
        assertEquals('R', cb.getFigureAt(0, 0));
        assertEquals('Q', cb.getFigureAt(0, 3));
        assertEquals('k', cb.getFigureAt(7, 4));
        assertEquals(EMPTY_SQUARE, cb.getFigureAt(4, 4));
        assertNotSame("R", cb.getFigureAt(0, 0));
        assertNotSame('d', cb.getFigureAt(0, 0));
        // ... should work
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#getFigureAt(java.lang.String)}.
     */
    @Test
    public final void testGetFigureAtString()
    {
        ChessBoard cb = new ChessBoard();
        
        assertEquals('R', cb.getFigureAt("h1"));
        assertEquals('R', cb.getFigureAt("H1"));
        assertEquals('q', cb.getFigureAt("d8"));
        assertEquals('k', cb.getFigureAt("E8"));
        assertEquals(EMPTY_SQUARE, cb.getFigureAt("d6"));
        assertNotSame(EMPTY_SQUARE, cb.getFigureAt("a7"));
        
        // errors -> EM...
        assertEquals(EMPTY_SQUARE, cb.getFigureAt("ab"));
        assertEquals(EMPTY_SQUARE, cb.getFigureAt("34"));
        assertEquals(EMPTY_SQUARE, cb.getFigureAt("3b"));
        assertEquals(EMPTY_SQUARE, cb.getFigureAt("H34"));        
        // ...
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#move(java.lang.String)}.
     */
    @Test
    public final void testMove()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#moveAndGetFEN(java.lang.String)}.
     */
    @Test
    public final void testMoveAndGetFEN()
    {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link de.uni_leipzig.informatik.swp13_sc.logic.ChessBoard#getCharChessField()}.
     * Test for char field initialization.
     */
    @Test
    public final void testGetCharChessField()
    {
        char[][] field = new char[8][8];
        // white figures are uppercase, black ones lowercase.
        // base: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR
        field[7][0] = 'r';
        field[7][1] = 'n';
        field[7][2] = 'b';
        field[7][3] = 'q';
        field[7][4] = 'k';
        field[7][5] = 'b';
        field[7][6] = 'n';
        field[7][7] = 'r';
        
        for (int x = 0; x < 8; x ++)
        {
            field[6][x] = 'p';
            field[5][x] = EMPTY_SQUARE;
            field[4][x] = EMPTY_SQUARE;
            field[3][x] = EMPTY_SQUARE;
            field[2][x] = EMPTY_SQUARE;
            field[1][x] = 'P';
        }
        
        field[0][0] = 'R';
        field[0][1] = 'N';
        field[0][2] = 'B';
        field[0][3] = 'Q';
        field[0][4] = 'K';
        field[0][5] = 'B';
        field[0][6] = 'N';
        field[0][7] = 'R';
        
        for (int i = 0; i < 8; i ++)
        {
            assertArrayEquals(field[i], ChessBoard.getCharChessField()[i]);
        }
        
        assertArrayEquals(field, ChessBoard.getCharChessField());
    }

}
