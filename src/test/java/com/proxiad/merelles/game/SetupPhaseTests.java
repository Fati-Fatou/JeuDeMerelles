package com.proxiad.merelles.game;

import org.junit.Before;
import org.junit.Test;

public class SetupPhaseTests {

	private static final Location target = new Location(1, 2);

	private Board board;
	private PlayerData blackPlayer;
	private PlayerData whitePlayer;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		blackPlayer = new PlayerData(board, PlayerColor.BLACK, 9);
		whitePlayer = new PlayerData(board, PlayerColor.WHITE, 9);
		blackPlayer.setOpponent(whitePlayer);
		whitePlayer.setOpponent(blackPlayer);
	}

	@Test
	public void testPlacingPieceIsOkDuringSetup() throws InvalidCommandException {
		PutCommand command = new PutCommand(target);
		command.run(board, blackPlayer);
		// success
	}

	@Test(expected = InvalidCommandException.class)
	public void testPlacingPieceOnExsitingOneIsKoDuringSetup() throws InvalidCommandException {
		board.putPiece(target, PlayerColor.BLACK);
		PutCommand command = new PutCommand(target);
		command.run(board, blackPlayer);
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
		command.run(board, blackPlayer);
	}

	@Test(expected = InvalidCommandException.class)
	public void testMovingOpponentsExistingPieceIsForbiddenDuringSetup() throws InvalidCommandException {
		int existingPieceId = board.putPiece(target, PlayerColor.WHITE);
		int newPieceId = existingPieceId;

		MoveCommand command = move(newPieceId);
		command.run(board, blackPlayer);
	}
}
