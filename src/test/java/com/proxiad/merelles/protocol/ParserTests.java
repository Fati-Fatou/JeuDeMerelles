package com.proxiad.merelles.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;

public class ParserTests {

	private static final String NO_COMMAND = "No command";

	private static final String SHOULD_THROW_AN_EXCEPTION = "Should throw an exception";

	public static class CommandForTests extends Command {
		public CommandForTests() {
			super(new Location(2, 1), null, null);
		}
	}

	private static final CommandForTests expectedCommand = new CommandForTests();

	public static class ParserForTests extends Parser<CommandForTests> {
		
		public ParserForTests(String keyword, int numberOfSpecificArguments) {
			super(keyword, numberOfSpecificArguments);
		}

		@Override
		protected CommandForTests parseCommandArguments(Board board, String message, String[] tokens,
				Location targetLocation, Piece removePiece) throws ParsingException {
			return expectedCommand;
		}
	}
	
	@Mock
	private Board board;

	private static final String keyword = "TESTING";
	private final int numberOfSpecificArguments = 2;
	
	@Spy
	private ParserForTests parser = new ParserForTests(keyword, numberOfSpecificArguments);
	
	@Captor
	private ArgumentCaptor<String[]> tokensCaptor;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testPassArguments1() throws ParsingException {
		String textFromPlayer = "TESTING specific1 specific2 3 2 0 ; Foobar";
		Location targetLocation = new Location(3, 2);
		when(board.findPieceById(0)).thenReturn(null);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertEquals(expectedCommand, command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), eq(targetLocation), Mockito.<Piece>isNull());
		
		String[] tokens = tokensCaptor.getValue();
		assertEquals(6, tokens.length);
		assertEquals("TESTING", tokens[0]);
		assertEquals("specific1", tokens[1]);
		assertEquals("specific2", tokens[2]);
		assertEquals("3", tokens[3]);
		assertEquals("2", tokens[4]);
		assertEquals("0", tokens[5]);
	}

	@Test
	public void testOtherTargetLocation() throws ParsingException {
		String textFromPlayer = "TESTING A B 7 1 0 ; Foobar";
		Location targetLocation = new Location(7, 1);
		when(board.findPieceById(0)).thenReturn(null);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertEquals(expectedCommand, command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), eq(targetLocation), Mockito.<Piece>isNull());
	}

	@Test
	public void testOtherSpecificArguments() throws ParsingException {
		String textFromPlayer = "TESTING A B 3 2 0 ; Foobar";
		Location targetLocation = new Location(3, 2);
		when(board.findPieceById(0)).thenReturn(null);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertEquals(expectedCommand, command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), eq(targetLocation), Mockito.<Piece>isNull());

		String[] tokens = tokensCaptor.getValue();
		assertEquals("A", tokens[1]);
		assertEquals("B", tokens[2]);
	}

	@Test
	public void testRemovePiece() throws ParsingException {
		String textFromPlayer = "TESTING A B 3 2 1 ; Foobar";
		Location targetLocation = new Location(3, 2);
		Piece removePiece = new Piece(1, PlayerColor.BLACK, new Location(3, 1));
		when(board.findPieceById(1)).thenReturn(removePiece);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertEquals(expectedCommand, command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), eq(targetLocation), same(removePiece));
	}

	@Test
	public void testSillyCommand() {
		String textFromPlayer = "You won't understand that";
		ParsingException exception = null;
		try {
			parser.parseCommand(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals("Expecting TESTING but found 'You'", exception.getMessage());
	}

	@Test
	public void testEmptyCommand() {
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
	public void testPartialCommand() {
		String textFromPlayer = "TESTING A B 3 2";
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
	public void testNullCommand() {
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
	public void testSilent() throws ParsingException {
		String textFromPlayer = "TESTING A B 3 2 0 ; ";
		Location targetLocation = new Location(3, 2);
		when(board.findPieceById(0)).thenReturn(null);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertEquals(expectedCommand, command);
		verify(parser).parseCommandArguments(same(board), eq(""), tokensCaptor.capture(), eq(targetLocation), Mockito.<Piece>isNull());
	}

	@Test
	public void testVoid() throws ParsingException {
		String textFromPlayer = "TESTING A B 3 2 0";
		Location targetLocation = new Location(3, 2);
		when(board.findPieceById(0)).thenReturn(null);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertEquals(expectedCommand, command);
		verify(parser).parseCommandArguments(same(board), eq(""), tokensCaptor.capture(), eq(targetLocation), Mockito.<Piece>isNull());
	}
}
