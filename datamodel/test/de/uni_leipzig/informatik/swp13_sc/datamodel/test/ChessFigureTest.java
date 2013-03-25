package de.uni_leipzig.informatik.swp13_sc.datamodel.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessColor;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigure;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessFigureType;
import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPosition;

public class ChessFigureTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testGetFigureType() {
		assertEquals(ChessFigure.BlackBishop1.getFigureType(), ChessFigure.WhiteBishop2.getFigureType());
		assertNotSame(ChessFigure.BlackKnight1.getFigureType(), ChessFigure.BlackPawn1.getFigureType());
	}

	@Test
	public final void testGetColor() {
		assertEquals(ChessFigure.BlackKing.getColor(), ChessColor.Black);
		assertEquals(ChessFigure.WhiteQueen.getColor(), ChessColor.White);
		// ...
	}

	@Test
	public final void testGetStartPosition() {
		assertEquals(ChessFigure.BlackBishop1.getStartPosition(), ChessPosition.C8);
		assertNotSame(ChessFigure.BlackPawn1.getStartPosition(), ChessPosition.C1);
		assertSame(ChessFigure.WhiteKing.getStartPosition(), ChessPosition.E1);
		// ...
	}

	@Test
	public final void testGetFigureFromPosition() {
		assertNull(ChessFigure.getFigureFromPosition(ChessPosition.A3));
		assertNull(ChessFigure.getFigureFromPosition(ChessPosition.D4));
		
		assertNotNull(ChessFigure.getFigureFromPosition(ChessPosition.D2));
		for (ChessFigure f : ChessFigure.values())
		{
			assertNotNull(ChessFigure.getFigureFromPosition(f.getStartPosition()));
			assertEquals(f, ChessFigure.getFigureFromPosition(f.getStartPosition()));
		}
	}

	/*@Test
	public final void testGetWhitePawns() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetBlackPawns() {
		fail("Not yet implemented"); // TODO
	}*/

	@Test
	public final void testGetFiguresChessFigureType() {
		for (ChessFigureType ft : ChessFigureType.values())
		{
			for (ChessFigure f : ChessFigure.getFigures(ft))
			{
				assertEquals(ft, f.getFigureType());
			}
		}
		
		assertNotNull(ChessFigure.getFigures((ChessFigureType) null));
	}

	@Test
	public final void testGetFiguresChessColor() {
		for (ChessFigure f : ChessFigure.getFigures(ChessColor.White))
		{
			assertEquals(f.getColor(), ChessColor.White);
		}
		for (ChessFigure f : ChessFigure.getFigures(ChessColor.Black))
		{
			assertEquals(f.getColor(), ChessColor.Black);
		}
		
		assertNotNull(ChessFigure.getFigures((ChessColor) null));
		assertEquals(0, ChessFigure.getFigures((ChessColor) null).size());
		assertNotNull(ChessFigure.getFigures(ChessColor.valueOf("White")));
	}

	@Test
	public final void testIntersect() {
		assertEquals(new ArrayList<ChessFigure>() {{add(ChessFigure.WhiteKing); }},
				ChessFigure.intersect(ChessFigure.getFigures(ChessColor.White),
						ChessFigure.getFigures(ChessFigureType.King)));
		assertEquals(ChessFigure.WhiteKing,
				ChessFigure.intersect(ChessFigure.getFigures(ChessColor.White),
						ChessFigure.getFigures(ChessFigureType.King)).get(0));
		assertEquals(new ArrayList<ChessFigure>(),
				ChessFigure.intersect(ChessFigure.getFigures(ChessColor.Black),
						ChessFigure.getFigures(ChessColor.White)));
		assertNotNull(ChessFigure.intersect(ChessFigure.getFigures(ChessColor.Black),
						ChessFigure.getFigures(ChessColor.White)));
	}

}
