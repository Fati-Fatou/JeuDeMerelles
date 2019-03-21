package com.proxiad.merelles.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
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

public class ParserMoveCommandTests {

	private static final String NO_COMMAND = "No command";

	private static final String SHOULD_THROW_AN_EXCEPTION = "Should throw an exception";

	@Mock
	private Board board;

	private ParserMoveCommand parser = new ParserMoveCommand();

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
	public void testOtherTargetLocation() throws ParsingException {
		String textFromPlayer = "MOVE 1 7 1 0 ; Foobar";
		Location previousLocation = new Location(7, 0);
		when(board.findPieceById(1)).thenReturn(new Piece(1, PlayerColor.BLACK, previousLocation));
		when(board.findPieceById(0)).thenReturn(null);
		MoveCommand command = parser.parseCommand(textFromPlayer, board);
		Location expectedLocation = new Location(7, 1);
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
	public void testRemovePiece() throws ParsingException {
		// MOVE MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID ; TEXT
		String textFromPlayer = "MOVE 2 3 2 1 ; Foobar";
		Piece removePiece = new Piece(1, PlayerColor.BLACK, new Location(3, 1));
		when(board.findPieceById(2)).thenReturn(new Piece(2, PlayerColor.BLACK, new Location(3, 2)));
		when(board.findPieceById(1)).thenReturn(removePiece);
		MoveCommand command = parser.parseCommand(textFromPlayer, board);
		assertSame(removePiece, command.getRemovePiece());
	}

	@Test
	public void testNoRemovePiece() throws ParsingException {
		// MOVE MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID ; TEXT
		String textFromPlayer = "MOVE 2 3 2 0 ; Foobar";
		when(board.findPieceById(2)).thenReturn(new Piece(2, PlayerColor.BLACK, new Location(3, 2)));
		when(board.findPieceById(0)).thenReturn(null);
		MoveCommand command = parser.parseCommand(textFromPlayer, board);
		assertNull(command.getRemovePiece());
	}

	@Test
	public void testSillyMoveCommand() {
		String textFromPlayer = "You won't understand that";
		ParsingException exception = null;
		try {
			parser.parseCommand(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals("Expecting MOVE but found 'You'", exception.getMessage());
	}

	@Test
	public void testEmptyMoveCommand() {
		String textFromPlayer = "";
		ParsingException exception = null;
		try {
			parser.parseCommand(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals(NO_COMMAND, exception.getMessage());
	}

	@Test
	public void testPartialMoveCommand() {
		String textFromPlayer = "MOVE 2 3 2";
		when(board.findPieceById(2)).thenReturn(new Piece(2, PlayerColor.BLACK, new Location(3, 2)));
		ParsingException exception = null;
		try {
			parser.parseCommand(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals("Expecting REMOVE_PIECE_ID but found end of line", exception.getMessage());
	}

	@Test
	public void testNullMoveCommand() {
		String textFromPlayer = null;
		ParsingException exception = null;
		try {
			parser.parseCommand(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals(NO_COMMAND, exception.getMessage());
	}

	@Test
	public void testMoveUnknownPiece() {
		// MOVE MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "MOVE 25 3 2 0 Foobar";
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

	@Test
	public void testMovePieceYielding() throws ParsingException {
		// MOVE MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "MOVE 1 3 2 0 ; Foobar";
		Location previousLocation = new Location(2, 2);
		when(board.findPieceById(1)).thenReturn(new Piece(1, PlayerColor.BLACK, previousLocation));
		when(board.findPieceById(0)).thenReturn(null);
		MoveCommand command = parser.parseCommand(textFromPlayer, board);
		assertEquals("Foobar", command.getMessage());
	}

	@Test
	public void testMovePieceSilent() throws ParsingException {
		// MOVE MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "MOVE 1 3 2 0 ; ";
		Location previousLocation = new Location(2, 2);
		when(board.findPieceById(1)).thenReturn(new Piece(1, PlayerColor.BLACK, previousLocation));
		when(board.findPieceById(0)).thenReturn(null);
		MoveCommand command = parser.parseCommand(textFromPlayer, board);
		assertEquals("", command.getMessage());
	}

	@Test
	public void testMovePieceVoid() throws ParsingException {
		// MOVE MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "MOVE 1 3 2 0";
		Location previousLocation = new Location(2, 2);
		when(board.findPieceById(1)).thenReturn(new Piece(1, PlayerColor.BLACK, previousLocation));
		when(board.findPieceById(0)).thenReturn(null);
		MoveCommand command = parser.parseCommand(textFromPlayer, board);
		assertEquals("", command.getMessage());
	}
}
