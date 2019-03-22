package com.proxiad.merelles.game;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.proxiad.merelles.protocol.Parser;
import com.proxiad.merelles.protocol.ParsingException;

public class PhaseTests {
	
	public static class CommandForTests extends Command {

		public CommandForTests() {
			super(new Location(1, 2));
		}

		@Override
		public void run(Board board, PlayerData player) throws InvalidCommandException {
			// nothing			
		}		
	}
	
	private Board board = new Board();
	private PlayerData player;
	private String commandText = "HELLO";
	
	@Spy
	private CommandForTests command;
	
	@Mock
	private Parser<Command> parser;
	
	public class PhaseForTests extends Phase {
		@Override
		protected Parser<? extends Command> parser() {
			return parser;
		}

		@Override
		public Stream<String> suggest(Board board, CommandFormatter formatter) {
			return null;
		}		
	}

	@Spy
	private PhaseForTests phase = new PhaseForTests();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		player = new PlayerData(board, PlayerColor.BLACK, 9);
	}

	@Test
	public void testParseAndRunWhenOk() throws ParsingException, InvalidCommandException {
		when(parser.parseCommand(commandText, board)).thenReturn(command);
		
		phase.parseAndRunCommand(commandText, board, player);
		verify(command).run(board, player);
	}

	@Test(expected = ParsingException.class)
	public void testParseDoesNotRunWhenKo() throws ParsingException, InvalidCommandException {
		when(parser.parseCommand(commandText, board)).thenThrow(new ParsingException("nope"));
		
		ParsingException exc = null;
		try {
			phase.parseAndRunCommand(commandText, board, player);
		} catch(ParsingException e) {
			exc = e;
		}
		
		verify(command, never()).run(board, player);
		
		if (exc != null) {
			throw exc;
		}
	}
}
