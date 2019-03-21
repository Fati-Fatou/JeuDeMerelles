package com.proxiad.merelles.game;

import org.junit.Before;
import org.junit.Test;

public class SetupPhaseTests {

	private static final Location target = new Location(1, 2);

	private Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@Test
	public void testPlacingPieceIsOkDuringSetup() throws InvalidCommandException {
		PutCommand command = new PutCommand(target);
		board.runPutCommand(PlayerColor.BLACK, command);
		// success
	}

	@Test(expected = InvalidCommandException.class)
	public void testPlacingPieceOnExsitingOneIsKoDuringSetup() throws InvalidCommandException {
		board.putPiece(target, PlayerColor.BLACK);
		PutCommand command = new PutCommand(target);
		board.runPutCommand(PlayerColor.BLACK, command);
	}

	private static MoveCommand move(int pieceId) {
		Piece pieceToMove = new Piece(pieceId, PlayerColor.BLACK, target);
		return new MoveCommand(pieceToMove, target, null, null);
	}
	
	@Test(expected = InvalidCommandException.class)
	public void testMovingMyExistingPieceIsForbiddenDuringSetup() throws InvalidCommandException {
		int existingPieceId = board.putPiece(target, PlayerColor.BLACK);
		int newPieceId = existingPieceId;

		MoveCommand command = move(newPieceId);
		board.runMoveCommand(PlayerColor.BLACK, command);
	}

	@Test(expected = InvalidCommandException.class)
	public void testMovingOpponentsExistingPieceIsForbiddenDuringSetup() throws InvalidCommandException {
		int existingPieceId = board.putPiece(target, PlayerColor.WHITE);
		int newPieceId = existingPieceId;

		MoveCommand command = move(newPieceId);
		board.runMoveCommand(PlayerColor.BLACK, command);
	}
}
