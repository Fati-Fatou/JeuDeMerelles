package com.proxiad.merelles.game;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class MoveOrJumpCommandTests {

	private static final int pieceId = 1;
	private static final Location source = new Location(1, 1);
	private static final Location target = new Location(1, 2);
	private static final Location nonAdjacentTarget = new Location(0, 2);

	private static final Piece blackPieceAtSource = new Piece(pieceId, PlayerColor.BLACK, source);
	private static final Piece whitePieceAtSource = new Piece(pieceId, PlayerColor.WHITE, source);

	@Spy
	private Board board = new Board();
	
	@Spy
	private PlayerData blackPlayer;
	
	@Before
	public void setUp() throws Exception {
		blackPlayer = new PlayerData(board, PlayerColor.BLACK, 9);
		PlayerData whitePlayer = new PlayerData(board, PlayerColor.WHITE, 9);
		blackPlayer.setOpponent(whitePlayer);
		whitePlayer.setOpponent(blackPlayer);
		
		MockitoAnnotations.initMocks(this);

		when(blackPlayer.getColor()).thenReturn(PlayerColor.BLACK);
	}

	@Test
	public void testMovingMyPieceIsOk() throws InvalidCommandException {

		when(board.isLocationFree(target)).thenReturn(true);

		MoveCommand command = new MoveOrJumpCommand(blackPieceAtSource, target);
		command.run(board, blackPlayer);

		verify(blackPlayer, never()).updateCountsAfterPut();
		verify(board, never()).putPiece(ArgumentMatchers.any(), ArgumentMatchers.any());
		verify(board).movePiece(blackPieceAtSource, target);
	}

	@Test(expected = InvalidCommandException.class)
	public void testMovingOpponentsExistingPieceIsForbidden() throws InvalidCommandException {

		when(board.isLocationFree(target)).thenReturn(true);

		MoveCommand command = new MoveOrJumpCommand(whitePieceAtSource, target);
		
		InvalidCommandException exc = null;
		try {
			command.run(board, blackPlayer);
		} catch(InvalidCommandException e) {
			exc = e;
		}

		verify(blackPlayer, never()).updateCountsAfterPut();
		verify(board, never()).putPiece(ArgumentMatchers.any(), ArgumentMatchers.any());
		verify(board, never()).movePiece(ArgumentMatchers.any(), ArgumentMatchers.any());
			
		if (exc != null) {
			throw exc;
		}
	}
	
	@Test(expected = InvalidCommandException.class)
	public void testMovingOnExistingPieceIsKo() throws InvalidCommandException {

		when(board.isLocationFree(target)).thenReturn(false);

		MoveCommand command = new MoveOrJumpCommand(blackPieceAtSource, target);
		
		InvalidCommandException exc = null;
		try {
			command.run(board, blackPlayer);
		} catch(InvalidCommandException e) {
			exc = e;
		}

		verify(blackPlayer, never()).updateCountsAfterPut();
		verify(board, never()).putPiece(ArgumentMatchers.any(), ArgumentMatchers.any());
		verify(board, never()).movePiece(ArgumentMatchers.any(), ArgumentMatchers.any());

		if (exc != null) {
			throw exc;
		}
	}

	@Test
	public void testJumpingToNonAdjacentIsOk() throws InvalidCommandException {

		when(board.isLocationFree(nonAdjacentTarget)).thenReturn(true);

		MoveCommand command = new MoveOrJumpCommand(blackPieceAtSource, nonAdjacentTarget);
		
		command.run(board, blackPlayer);

		verify(blackPlayer, never()).updateCountsAfterPut();
		verify(board, never()).putPiece(ArgumentMatchers.any(), ArgumentMatchers.any());
		verify(board).movePiece(blackPieceAtSource, nonAdjacentTarget);
	}
}
