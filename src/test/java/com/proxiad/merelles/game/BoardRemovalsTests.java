package com.proxiad.merelles.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BoardRemovalsTests {

	private Board board;
	private PlayerData whitePlayer;
	private PlayerData blackPlayer;
	private Piece piece;
	private int pieceId;
	
	@Mock
	private Board.BoardObserver boardObserver;
	
	@Mock
	private Piece.PieceObserver pieceObserver;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		
		whitePlayer = new PlayerData(board, PlayerColor.WHITE, 9);		
		blackPlayer = new PlayerData(board, PlayerColor.BLACK, 9);
		whitePlayer.setOpponent(blackPlayer);
		blackPlayer.setOpponent(whitePlayer);
		
		pieceId = board.putPiece(new Location(2, 1), PlayerColor.WHITE);
		piece = board.findPieceById(pieceId);
		
		MockitoAnnotations.initMocks(this);
		
		board.addListener(boardObserver);
		piece.addListener(pieceObserver);
	}

	@Test
	public void testRemovePieceTakesPiece() {
		board.removePiece(pieceId);
		
		verify(pieceObserver).taken(piece);
		verify(boardObserver).pieceTaken(piece);
		verifyNoMoreInteractions(pieceObserver);
	}

	@Test
	public void testRemovedPieceIsRemoved() {
		board.removePiece(pieceId);
		
		Piece pieceAfter = board.findPieceById(pieceId);
		
		assertNull(pieceAfter);
	}
}
