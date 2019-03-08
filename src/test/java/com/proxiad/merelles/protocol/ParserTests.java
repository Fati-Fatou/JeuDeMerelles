package com.proxiad.merelles.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.UnknownPieceException;

public class ParserTests {

	private static final String INVALID_INPUT = "Invalid input";
	private static final String SHOULD_THROW_AN_EXCEPTION = "Should throw an exception";

	@Mock
	private Board board;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testMovePiece1() throws ParsingException, UnknownPieceException {
		// MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "1 3 2 0 Foobar";
		Parser parser = new Parser();
		when(board.findPieceById(1)).thenReturn(new Piece(1));
		Command command = parser.parse(textFromPlayer, board);
		assertEquals(1, command.getMovedPiece().getId());
	}
	
	@Test
	public void testTargetLocation() throws ParsingException, UnknownPieceException{
		String textFromPlayer = "1 3 2 0 Foobar";
		Parser parser = new Parser();
		when(board.findPieceById(1)).thenReturn(new Piece(1));
		Command command = parser.parse(textFromPlayer, board);
		Location expectedLocation = new Location(3, 2);
		assertEquals(expectedLocation, command.getTargetLocation());
	}

	@Test
	public void testOtherTargetLocation() throws ParsingException, UnknownPieceException{
		String textFromPlayer = "1 7 1 0 Foobar";
		Parser parser = new Parser();
		when(board.findPieceById(1)).thenReturn(new Piece(1));
		Command command = parser.parse(textFromPlayer, board);
		Location expectedLocation = new Location(7, 1);
		assertEquals(expectedLocation, command.getTargetLocation());
	}

	@Test
	public void testMovePiece2() throws ParsingException, UnknownPieceException {
		// MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "2 3 2 0 Foobar";
		Parser parser = new Parser();
		when(board.findPieceById(2)).thenReturn(new Piece(2));
		Command command = parser.parse(textFromPlayer, board);
		assertEquals(2, command.getMovedPiece().getId());
	}

	@Test
	public void testSillyCommand() {
		String textFromPlayer = "You won't understand that";
		Parser parser = new Parser();
		ParsingException exception = null;
		try {
			parser.parse(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals(INVALID_INPUT, exception.getMessage());
	}

	@Test
	public void testEmptyCommand() {
		String textFromPlayer = "";
		Parser parser = new Parser();
		ParsingException exception = null;
		try {
			parser.parse(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals(INVALID_INPUT, exception.getMessage());
	}

	@Test
	public void testNullCommand() {
		String textFromPlayer = null;
		Parser parser = new Parser();
		ParsingException exception = null;
		try {
			parser.parse(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals(INVALID_INPUT, exception.getMessage());
	}
	
	@Test
	public void testMoveUnknownPiece() throws ParsingException, UnknownPieceException {
		// MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "25 3 2 0 Foobar";
		Parser parser = new Parser();
		when(board.findPieceById(25)).thenThrow(new UnknownPieceException());
		ParsingException exception = null;
		try {
			parser.parse(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals(INVALID_INPUT, exception.getMessage());
	}

}
