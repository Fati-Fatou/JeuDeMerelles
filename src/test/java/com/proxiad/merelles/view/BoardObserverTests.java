package com.proxiad.merelles.view;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Board.BoardObserver;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;

public class BoardObserverTests {

	private Board board;
	private BoardObserver observer;

	@Before
	public void setUp() throws Exception {
		board = new Board();
		observer = mock(BoardObserver.class);
		board.addListener(observer);
	}

	@Test
	public void testAddPieceIsObserved() {
		ArgumentCaptor<Piece> pieceCaptor = ArgumentCaptor.forClass(Piece.class);
		
		board.putPiece(new Location(2, 1), PlayerColor.WHITE);

		verify(observer).pieceAdded(pieceCaptor.capture());
		Piece piece = pieceCaptor.getValue();
		assertEquals(PlayerColor.WHITE, piece.getColor());
		assertEquals(new Location(2, 1), piece.getLocation());
	}
}
