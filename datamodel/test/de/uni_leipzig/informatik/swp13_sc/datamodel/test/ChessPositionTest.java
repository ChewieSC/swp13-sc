package de.uni_leipzig.informatik.swp13_sc.datamodel.test;

import static org.junit.Assert.*;

import org.junit.Test;

import de.uni_leipzig.informatik.swp13_sc.datamodel.ChessPosition;

public class ChessPositionTest {

	@Test
	public final void testToString() {
		assertEquals(ChessPosition.H8.toString(), "h8");
	}

	@Test
	public final void testEqualTo() {
		assertTrue(ChessPosition.B2.equalTo("b2"));
		assertTrue(ChessPosition.A1.equalTo("A1"));
		assertFalse(ChessPosition.D3.equalTo("3D"));
		assertFalse(ChessPosition.H3.equalTo("HC"));
		
		assertTrue(ChessPosition.C7.equalTo(ChessPosition.C7.toString()));
		assertTrue(ChessPosition.E8.equalTo(ChessPosition.E8.name()));
		
		assertTrue(ChessPosition.F6.equals(ChessPosition.F6));
	}

	@Test
	public final void testGetLetter() {
		assertEquals(ChessPosition.E3.getLetter(), 'e');
		assertNotSame(ChessPosition.F4.getLetter(), 'F');
	}

	@Test
	public final void testGetNumber() {
		assertEquals(ChessPosition.F5.getNumber(), 5);
		assertEquals(ChessPosition.A8.getNumber(), 8);
		assertEquals(ChessPosition.D8.getNumber(), 8);
	}

	@Test
	public final void testGetPosX() {
		// Indizes
		assertEquals(ChessPosition.A2.getPosX(), 1-1);
		assertEquals(ChessPosition.H3.getPosX(), 8-1);
		assertEquals(ChessPosition.D4.getPosX(), 4-1);
	}

	@Test
	public final void testGetPosY() {
		assertEquals(ChessPosition.A2.getPosY(), 2-1);
		assertEquals(ChessPosition.H3.getPosY(), 3-1);
		assertEquals(ChessPosition.D5.getPosY(), ChessPosition.D5.getNumber()-1);
	}

}
