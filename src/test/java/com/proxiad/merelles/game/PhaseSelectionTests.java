package com.proxiad.merelles.game;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PhaseSelectionTests {

	private final static int PIECES_IN_INITIAL_STOCK = 9;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOneStockPieceMeansPlacementPhase() {
		PlayerData player = setUpPlayer(1, 0);
		checkPhase(player, PlacementPhase.class);
	}

	private PlayerData setUpPlayer(int piecesInStock, int piecesOnBoard) {
		int piecesInInitialStock = PIECES_IN_INITIAL_STOCK;
		int putPieces = piecesInInitialStock - piecesInStock;
		int takenPieces = putPieces - piecesOnBoard;
		
		Board board = new Board();
		PlayerData player = new PlayerData(board, PlayerColor.BLACK, piecesInInitialStock);
		
		// Simulate puts
		for (int putTurn = 0; putTurn < putPieces; ++putTurn) {
			player.updateCountsAfterPut();
		}

		// Simulate taken pieces
		for (int taken = 0; taken < takenPieces; ++taken) {
			player.updateCountsAfterPieceTaken();
		}
		return player;
	}
	
	private void checkPhase(PlayerData player, Class<? extends Phase> phaseClass) {
		Phase phase = player.getPhase();
		assertTrue(phase.getClass().isAssignableFrom(phaseClass));		
	}

	@Test
	public void testNoStockPieceAndFourPiecesOnBoardMeansMovementPhase() {
		PlayerData player = setUpPlayer(0, 4);
		checkPhase(player, MovementPhase.class);
	}

	@Test
	public void testNoStockPieceAndThreePiecesOnBoardMeansJumpPhase() {
		PlayerData player = setUpPlayer(0, 3);
		checkPhase(player, JumpPhase.class);
	}

	@Test
	public void testOneStockPieceAndThreePiecesOnBoardMeansPlacementPhase() {
		PlayerData player = setUpPlayer(1, 3);
		checkPhase(player, PlacementPhase.class);
	}
}
