package com.proxiad.merelles.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class BoardSetupTests {

	private Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@Test
	public void testBoardStartsEmpty() {
		assertEquals(0, board.pieces().count());
	}

	@Test
	public void testAddOnePiece() {
		Location locationWhite1 = new Location(1, 2);
		int newId = board.putPiece(locationWhite1, PlayerColor.WHITE);
		
		Piece white1 = board.findPieceById(newId);
		assertEquals(newId, white1.getId());
		assertEquals(locationWhite1, white1.getLocation());
		assertEquals(PlayerColor.WHITE, white1.getColor());
		
		assertEquals(1, board.pieces().count());
		List<Piece> actualPieces = board.pieces().collect(Collectors.toList());
		assertSame(white1, actualPieces.get(0));
	}

	@Test
	public void testFindUnknownPiece() {
		Location locationWhite1 = new Location(1, 2);
		int newId = board.putPiece(locationWhite1, PlayerColor.WHITE);
		
		assertNull(board.findPieceById(newId + 1));		
	}

	@Test
	public void testAnotherPiece() {
		Location locationWhite1 = new Location(1, 2);
		int white1Id = board.putPiece(locationWhite1, PlayerColor.WHITE);
		
		Location locationBlack1 = new Location(3, 0);
		int black1Id = board.putPiece(locationBlack1, PlayerColor.BLACK);
		
		assertNotEquals(white1Id, black1Id);
		Piece white1 = board.findPieceById(white1Id);
		Piece black1 = board.findPieceById(black1Id);
		assertEquals(black1Id, black1.getId());
		assertEquals(locationBlack1, black1.getLocation());
		assertEquals(PlayerColor.BLACK, black1.getColor());
		
		assertEquals(2, board.pieces().count());
		List<Piece> blackPieces = board.pieces().filter(piece -> piece.getColor() == PlayerColor.BLACK).collect(Collectors.toList());
		List<Piece> whitePieces = board.pieces().filter(piece -> piece.getColor() == PlayerColor.WHITE).collect(Collectors.toList());
		assertEquals(1, blackPieces.size());
		assertEquals(1, whitePieces.size());
		assertSame(white1, whitePieces.get(0));
		assertSame(black1, blackPieces.get(0));
	}
}
