package com.proxiad.merelles.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.InvalidCommandException;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.game.PlayerData;

public class ParserTests {

	private static final String NO_COMMAND = "No command";

	private static final String SHOULD_THROW_AN_EXCEPTION = "Should throw an exception";

	public static class CommandForTests extends Command {
		public CommandForTests(Collection<Piece> removePieces) {
			super(new Location(2, 1), removePieces, null);
		}

		@Override
		public void run(Board board, PlayerData player) throws InvalidCommandException {
			// empty when testing			
		}
	}

	public static class ParserForTests extends Parser<CommandForTests> {
		
		public ParserForTests(String keyword, int numberOfSpecificArguments) {
			super(keyword, numberOfSpecificArguments);
		}

		@Override
		protected CommandForTests parseCommandArguments(Board board, String message, String[] tokens,
				Location targetLocation, Collection<Piece> removePieces) throws ParsingException {
			return new CommandForTests(removePieces);
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
	
	@Captor
	private ArgumentCaptor<Collection<Piece>> removePiecesCaptor;
	
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
		assertNotNull(command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), eq(targetLocation), removePiecesCaptor.capture());
		assertEquals(0, removePiecesCaptor.getValue().size());
		
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
	public void testPassArguments2Zeroes() throws ParsingException {
		String textFromPlayer = "TESTING specific1 specific2 3 2 0 0 ; Foobar";
		Location targetLocation = new Location(3, 2);
		when(board.findPieceById(0)).thenReturn(null);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertNotNull(command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), eq(targetLocation), removePiecesCaptor.capture());
		assertEquals(0, removePiecesCaptor.getValue().size());
		
		String[] tokens = tokensCaptor.getValue();
		assertEquals(7, tokens.length);
		assertEquals("TESTING", tokens[0]);
		assertEquals("specific1", tokens[1]);
		assertEquals("specific2", tokens[2]);
		assertEquals("3", tokens[3]);
		assertEquals("2", tokens[4]);
		assertEquals("0", tokens[5]);
		assertEquals("0", tokens[6]);

		List<Piece> removePieces = command.getRemovePieces();

		assertEquals(0, removePieces.size());
	}

	@Test
	public void testPassArguments2With1Value() throws ParsingException {
		String textFromPlayer = "TESTING specific1 specific2 3 2 0 9 ; Foobar";
		Location targetLocation = new Location(3, 2);
		when(board.findPieceById(0)).thenReturn(null);
		Piece removePiece2 = new Piece(9, PlayerColor.BLACK, new Location(2,2));
		when(board.findPieceById(9)).thenReturn(removePiece2);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertNotNull(command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), eq(targetLocation), removePiecesCaptor.capture());
		
		String[] tokens = tokensCaptor.getValue();
		assertEquals(7, tokens.length);
		
		List<Piece> removePieces = command.getRemovePieces();

		assertEquals(1, removePieces.size());
		assertEquals(9, removePieces.get(0).getId());
	}

	@Test
	public void testPassArguments2With2Values() throws ParsingException {
		String textFromPlayer = "TESTING specific1 specific2 3 2 8 9 ; Foobar";
		Location targetLocation = new Location(3, 2);
		Piece removePiece1 = new Piece(8, PlayerColor.BLACK, new Location(0,1));
		Piece removePiece2 = new Piece(9, PlayerColor.BLACK, new Location(2,2));
		when(board.findPieceById(8)).thenReturn(removePiece1);
		when(board.findPieceById(9)).thenReturn(removePiece2);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertNotNull(command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), eq(targetLocation), removePiecesCaptor.capture());
		
		String[] tokens = tokensCaptor.getValue();
		assertEquals(7, tokens.length);
		
		List<Piece> removePieces = command.getRemovePieces();

		assertEquals(2, removePieces.size());
		assertEquals(8, removePieces.get(0).getId());
		assertEquals(9, removePieces.get(1).getId());
	}

	@Test
	public void testOtherTargetLocation() throws ParsingException {
		String textFromPlayer = "TESTING A B 7 1 0 ; Foobar";
		Location targetLocation = new Location(7, 1);
		when(board.findPieceById(0)).thenReturn(null);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertNotNull(command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), eq(targetLocation), removePiecesCaptor.capture());
		assertEquals(0, removePiecesCaptor.getValue().size());
	}

	@Test
	public void testOtherSpecificArguments() throws ParsingException {
		String textFromPlayer = "TESTING A B 3 2 0 ; Foobar";
		Location targetLocation = new Location(3, 2);
		when(board.findPieceById(0)).thenReturn(null);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertNotNull(command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), eq(targetLocation), removePiecesCaptor.capture());
		assertEquals(0, removePiecesCaptor.getValue().size());

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
		assertNotNull(command);
		verify(parser).parseCommandArguments(same(board), eq("Foobar"), tokensCaptor.capture(), 
				eq(targetLocation), removePiecesCaptor.capture());
		
		assertEquals(1, removePiecesCaptor.getValue().size());
		assertSame(removePiece, removePiecesCaptor.getValue().iterator().next());
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
		String textFromPlayer = "TESTING A B 3";
		ParsingException exception = null;
		try {
			parser.parseCommand(textFromPlayer, board);
			fail(SHOULD_THROW_AN_EXCEPTION);
		} catch(ParsingException exc) {
			exception = exc;
		}
		assertEquals("Expecting RADIUS but found end of line", exception.getMessage());
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
		assertNotNull(command);
		verify(parser).parseCommandArguments(same(board), eq(""), tokensCaptor.capture(), eq(targetLocation), removePiecesCaptor.capture());
		assertEquals(0, removePiecesCaptor.getValue().size());
	}

	@Test
	public void testVoid() throws ParsingException {
		String textFromPlayer = "TESTING A B 3 2 0";
		Location targetLocation = new Location(3, 2);
		when(board.findPieceById(0)).thenReturn(null);
		CommandForTests command = parser.parseCommand(textFromPlayer, board);
		assertNotNull(command);
		verify(parser).parseCommandArguments(same(board), eq(""), tokensCaptor.capture(), eq(targetLocation), removePiecesCaptor.capture());
		assertEquals(0, removePiecesCaptor.getValue().size());
	}
}
