package com.proxiad.merelles.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardMovesTests {

	private Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@Test
	public void testMovedPieceHasMoved() throws InvalidCommandException {
		Location initialLocation = new Location(2, 2);
		Location targetLocation = new Location(3, 2);
		
		int pieceId = board.putPiece(initialLocation, PlayerColor.WHITE);
		
		Piece piece = board.findPieceById(pieceId);
		
		board.movePiece(piece, targetLocation);
		
		Piece pieceAfterMove =board.findPieceById(pieceId);
		assertEquals(targetLocation, pieceAfterMove.getLocation());
	}
}
