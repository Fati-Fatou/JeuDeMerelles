package com.proxiad.merelles.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;

public class ParserMoveOrJumpCommandTests {

	private static final String SHOULD_THROW_AN_EXCEPTION = "Should throw an exception";

	@Mock
	private Board board;

	private ParserMoveOrJumpCommand parser = new ParserMoveOrJumpCommand();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testMovePiece1() throws ParsingException {
		// MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "MOVE 1 3 2 0 ; Foobar";
		Location previousLocation = new Location(2, 2);
		when(board.findPieceById(1)).thenReturn(new Piece(1, PlayerColor.BLACK, previousLocation));
		when(board.findPieceById(0)).thenReturn(null);
		MoveCommand command = parser.parseCommand(textFromPlayer, board);
		assertEquals(1, command.getMovedPiece().getId());
	}

	@Test
	public void testTargetLocation() throws ParsingException {
		String textFromPlayer = "MOVE 1 3 2 0 ; Foobar";
		Location previousLocation = new Location(2, 2);
		when(board.findPieceById(1)).thenReturn(new Piece(1, PlayerColor.BLACK, previousLocation));
		when(board.findPieceById(0)).thenReturn(null);
		MoveCommand command = parser.parseCommand(textFromPlayer, board);
		Location expectedLocation = new Location(3, 2);
		assertEquals(expectedLocation, command.getTargetLocation());
	}

	@Test
	public void testMovePiece2() throws ParsingException {
		// MOVE MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID ; TEXT
		String textFromPlayer = "MOVE 2 3 2 0 ; Foobar";
		when(board.findPieceById(2)).thenReturn(new Piece(2, PlayerColor.BLACK, new Location(3, 2)));
		when(board.findPieceById(0)).thenReturn(null);
		MoveCommand command = parser.parseCommand(textFromPlayer, board);
		assertEquals(2, command.getMovedPiece().getId());
	}

	@Test
	public void testMoveUnknownPiece() {
		// MOVE MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "MOVE 25 3 2 0 ; Foobar";
		when(board.findPieceById(25)).thenReturn(null);
		ParsingException exception = null;
		try {
			parser.parseCommand(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch (ParsingException exc) {
			exception = exc;
		}
		assertEquals("Unknown piece 25", exception.getMessage());
	}
}
