package com.proxiad.merelles.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RemovePieceTests {
	
	private static final PlayerColor MY_COLOR = PlayerColor.WHITE;
	private static final PlayerColor OPPONENTS_COLOR = PlayerColor.BLACK;
	
	private Board board = new Board();
	private PlayerData opponent = new PlayerData(board, OPPONENTS_COLOR, 3);
	private List<Piece> removePieces = new ArrayList<>();	
	
	@Before
	public void setUp() {
		board = new Board();
		opponent = new PlayerData(board, OPPONENTS_COLOR, 3);		
	}

	@Test
	public void testOpponentsPieces() {
		//GIVEN
		board.putPiece(new Location(0,0), OPPONENTS_COLOR);
		board.putPiece(new Location(0,1), OPPONENTS_COLOR);
		board.putPiece(new Location(1,2), OPPONENTS_COLOR);
		board.putPiece(new Location(7,2), MY_COLOR);
		board.putPiece(new Location(0,2), MY_COLOR);
		board.putPiece(new Location(6,2), MY_COLOR);
		board.putPiece(new Location(5,2), MY_COLOR);
		//WHEN
		List<Piece> opponentPieces = opponent.pieces();	
		//THEN
		assertEquals(3, opponentPieces.size());		
	}
	
	@Test
	public void testIsRemovableOk() {
		int id = board.putPiece(new Location(7,2), OPPONENTS_COLOR);
		Piece piece = board.findPieceById(id);
		
		boolean removable = board.isRemovable(piece, OPPONENTS_COLOR);
		assertTrue(removable);
	}

	@Test
	public void testWrongColorIsNotRemovable() {
		int id = board.putPiece(new Location(7,2), MY_COLOR);
		Piece piece = board.findPieceById(id);
		
		boolean removable = board.isRemovable(piece, OPPONENTS_COLOR);
		assertFalse(removable);
	}

	@Test
	public void testInAMillIsNotRemovable() {
		int id = board.putPiece(new Location(7,2), OPPONENTS_COLOR);
		board.putPiece(new Location(7,1), OPPONENTS_COLOR);
		board.putPiece(new Location(7,0), OPPONENTS_COLOR);
		Piece piece = board.findPieceById(id);
		
		boolean removable = board.isRemovable(piece, OPPONENTS_COLOR);
		assertFalse(removable);
	}

	@Test
	public void testValidHintWillBeRemoved() {
		int id = board.putPiece(new Location(7,2), OPPONENTS_COLOR);
		Piece candidate = board.findPieceById(id);

		removePieces.add(candidate);
		
		List<Integer> removePiecesIds = board.selectRemovablePieces(1, removePieces, OPPONENTS_COLOR);
		assertEquals(1, removePiecesIds.size());
		assertEquals(Integer.valueOf(id), removePiecesIds.get(0));
	}

	@Test
	public void testInvalidHintWillNotBeRemoved() {
		int id = board.putPiece(new Location(7,2), MY_COLOR);
		int goodId = board.putPiece(new Location(7,1), OPPONENTS_COLOR);
		Piece candidate = board.findPieceById(id);

		removePieces.add(candidate);
		
		List<Integer> removePiecesIds = board.selectRemovablePieces(1, removePieces, OPPONENTS_COLOR);
		assertEquals(1, removePiecesIds.size());

		int removePieceId = removePiecesIds.get(0);
		assertNotEquals(id, removePieceId);
		assertEquals(goodId, removePieceId);
	}

	@Test
	public void testInvalidNoHintRemovesFirstValid() {
		int id = board.putPiece(new Location(7,2), OPPONENTS_COLOR);

		List<Integer> removePiecesIds = board.selectRemovablePieces(1, removePieces, OPPONENTS_COLOR);
		assertEquals(1, removePiecesIds.size());
		assertEquals(Integer.valueOf(id), removePiecesIds.get(0));
	}
	
	@Test
	public void testTwoPiecesRemoved() {
		int notAHintGoodId = board.putPiece(new Location(7,1), OPPONENTS_COLOR);
		board.putPiece(new Location(0,0), OPPONENTS_COLOR);
		int goodHintId = board.putPiece(new Location(0,1), OPPONENTS_COLOR);
		board.putPiece(new Location(1,2), OPPONENTS_COLOR);
		int wrongHintId = board.putPiece(new Location(7,2), MY_COLOR);
		board.putPiece(new Location(0,2), MY_COLOR);
		board.putPiece(new Location(6,2), MY_COLOR);
		board.putPiece(new Location(5,2), MY_COLOR);

		Piece candidate1 = board.findPieceById(wrongHintId);
		Piece candidate2 = board.findPieceById(goodHintId);

		removePieces.add(candidate1);
		removePieces.add(candidate2);
		
		List<Integer> removePiecesIds = board.selectRemovablePieces(2, removePieces, OPPONENTS_COLOR);
		assertEquals(2, removePiecesIds.size());

		assertTrue(removePiecesIds.contains(Integer.valueOf(goodHintId)));
		assertTrue(removePiecesIds.contains(Integer.valueOf(notAHintGoodId)));
	}
	
	@Test
	public void testNewMillsDetectionAndRemovableDoNotInteract() {
		// Given a mill
		int id = board.putPiece(new Location(7,2), OPPONENTS_COLOR);
		board.putPiece(new Location(7,1), OPPONENTS_COLOR);
		board.putPiece(new Location(7,0), OPPONENTS_COLOR);

		// when new mills detection is called
		board.findMills();
		
		// the mill still prevents its peices to be removed.
		Piece piece = board.findPieceById(id);
		
		boolean removable = board.isRemovable(piece, OPPONENTS_COLOR);
		assertFalse(removable);
	}
}
