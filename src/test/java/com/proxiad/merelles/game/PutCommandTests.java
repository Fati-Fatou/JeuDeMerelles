package com.proxiad.merelles.game;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class PutCommandTests {

	private static final Location target = new Location(1, 2);

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
	public void testPlacingPieceOnEmptySlotIsOk() throws InvalidCommandException {

		when(board.isLocationFree(target)).thenReturn(true);

		PutCommand command = new PutCommand(target);
		command.run(board, blackPlayer);

		verify(board).putPiece(target, PlayerColor.BLACK);
		verify(blackPlayer).updateCountsAfterPut();
		verify(board, never()).movePiece(ArgumentMatchers.any(), ArgumentMatchers.any());
	}

	@Test(expected = InvalidCommandException.class)
	public void testPlacingPieceOnExsitingOneIsKo() throws InvalidCommandException {

		when(board.isLocationFree(target)).thenReturn(false);
		
		PutCommand command = new PutCommand(target);
		InvalidCommandException exc = null;
		try {
			command.run(board, blackPlayer);
		} catch(InvalidCommandException e) {
			exc = e;
		}

		verify(board, never()).putPiece(ArgumentMatchers.any(), ArgumentMatchers.any());
		verify(blackPlayer, never()).updateCountsAfterPut();
		verify(board, never()).movePiece(ArgumentMatchers.any(), ArgumentMatchers.any());

		if (exc != null) {
			throw exc;
		}
	}
}
