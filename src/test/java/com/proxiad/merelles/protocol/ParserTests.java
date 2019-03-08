package com.proxiad.merelles.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.proxiad.merelles.game.Command;

public class ParserTests {

	private static final String INVALID_INPUT = "Invalid input";
	private static final String SHOULD_THROW_AN_EXCEPTION = "Should throw an exception";

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMovePiece1() throws ParsingException {
		// MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "1 3 2 0 Foobar";
		Parser parser = new Parser();
		Command command = parser.parse(textFromPlayer);
		assertEquals(1, command.getPieceId());
	}

	@Test
	public void testMovePiece2() throws ParsingException {
		// MOVE_PIECE_ID TO_A TO_R REMOVE_PIECE_ID TEXT
		String textFromPlayer = "2 3 2 0 Foobar";
		Parser parser = new Parser();
		Command command = parser.parse(textFromPlayer);
		assertEquals(2, command.getPieceId());
	}

	@Test
	public void testSillyCommand() {
		String textFromPlayer = "You won't understand that";
		Parser parser = new Parser();
		ParsingException exception = null;
		try {
			parser.parse(textFromPlayer);
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
			parser.parse(textFromPlayer);
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
			parser.parse(textFromPlayer);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals(INVALID_INPUT, exception.getMessage());
	}
}
