package com.proxiad.merelles.game;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SetupPhaseTests {

	private static final Location target = new Location(1, 2);

	private Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@Test
	public void testPlacingPieceIsOkDuringSetup() throws InvalidCommandException {
		Command command = new Command(null, target, null, null);
		board.runCommand(PlayerColor.BLACK, command);
		// success
	}

	private static Command move(int pieceId) {
		Piece pieceToMove = new Piece(pieceId, PlayerColor.BLACK, target);
		return new Command(pieceToMove, target, null, null);
	}
	
	@Test(expected = InvalidCommandException.class)
	public void testMovingMyExistingPieceIsForbiddenDuringSetup() throws InvalidCommandException {
		int existingPieceId = board.putPiece(target, PlayerColor.BLACK);
		int newPieceId = existingPieceId;

		Command command = move(newPieceId);
		board.runCommand(PlayerColor.BLACK, command);
	}

	@Test(expected = InvalidCommandException.class)
	public void testMovingOpponentsExistingPieceIsForbiddenDuringSetup() throws InvalidCommandException {
		int existingPieceId = board.putPiece(target, PlayerColor.WHITE);
		int newPieceId = existingPieceId;

		Command command = move(newPieceId);
		board.runCommand(PlayerColor.BLACK, command);
	}
}
