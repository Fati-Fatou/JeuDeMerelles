package com.proxiad.merelles.game;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MillsTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNoMill() {
		Board board = new Board();
		board.putPiece(new Location(7,2), PlayerColor.BLACK);
		List<Mill> mills = board.findMills();
		assertEquals(0, mills.size());
	}
	
	@Test
	public void testNoMillOneNeighbour() {
		Board board = new Board();
		board.putPiece(new Location(7,2), PlayerColor.BLACK);
		board.putPiece(new Location(7,1), PlayerColor.BLACK);
		List<Mill> mills = board.findMills();
		assertEquals(0, mills.size());
	}
	
	@Test
	public void testRadialMill() {
		Board board = new Board();
		board.putPiece(new Location(7,2), PlayerColor.BLACK);
		board.putPiece(new Location(7,1), PlayerColor.BLACK);
		board.putPiece(new Location(7,0), PlayerColor.BLACK);
		List<Mill> mills = board.findMills();
		assertEquals(1, mills.size());
	}

	@Test
	public void testTransverseMill() {
		Board board = new Board();
		board.putPiece(new Location(6,2), PlayerColor.BLACK);
		board.putPiece(new Location(5,2), PlayerColor.BLACK);
		board.putPiece(new Location(4,2), PlayerColor.BLACK);
		List<Mill> mills = board.findMills();
		assertEquals(1, mills.size());
	}

	@Test
	public void testWrongRadial() {
		Board board = new Board();
		board.putPiece(new Location(0,0), PlayerColor.BLACK);
		board.putPiece(new Location(0,1), PlayerColor.BLACK);
		board.putPiece(new Location(0,2), PlayerColor.BLACK);
		List<Mill> mills = board.findMills();
		assertEquals(0, mills.size());
	}
}
