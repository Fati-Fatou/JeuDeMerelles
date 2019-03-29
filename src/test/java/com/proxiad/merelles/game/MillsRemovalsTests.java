package com.proxiad.merelles.game;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.proxiad.merelles.protocol.Parser;
import com.proxiad.merelles.protocol.ParsingException;

public class MillsRemovalsTests {

	private static final Location[] opponentsPieces = new Location[] {
		new Location(7, 1),
		new Location(7, 2),
		new Location(6, 0)
	};
	
	public static class CommandForRemovalTests extends Command {

		public CommandForRemovalTests(Collection<Piece> removePieces) {
			super(new Location(1, 2), removePieces);
		}

		@Override
		public void run(Board board, PlayerData player) throws InvalidCommandException {
			// nothing			
		}		
	}

	@Spy
	private Board board = new Board();
	private PlayerData player;
	private PlayerData opponent;
	private String commandText = "HELLO";
	
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
		opponent = new PlayerData(board, PlayerColor.WHITE, 9);
		player.setOpponent(opponent);
	}

	@Test
	public void testNoMillNoRemovals() throws ParsingException, InvalidCommandException {
		CommandForRemovalTests command = makeCommand(0); 
		when(parser.parseCommand(commandText, board)).thenReturn(command);

		when(board.findMills()).thenReturn(makeMills(0));
		when(board.selectRemovablePieces(0, command.getRemovePieces(), PlayerColor.WHITE)).thenReturn(new ArrayList<>());
		
		phase.parseAndRunCommand(commandText, board, player);

		assertEquals(9, opponent.getPiecesInStock() + opponent.getPiecesOnBoard());
		verify(board, never()).removePiece(any(Integer.class));
	}

	private CommandForRemovalTests makeCommand(int nbOfRemovePieces) {
		List<Piece> removePieces = new ArrayList<>();
		
		for (int i = 0; i < nbOfRemovePieces; ++i) {
			int id = board.putPiece(opponentsPieces[i], PlayerColor.WHITE);
			Piece piece = board.findPieceById(id);
			removePieces.add(piece);
		}

		return new CommandForRemovalTests(removePieces);
	}

	private List<Mill> makeMills(int numberOfMills) {
		List<Mill> mills = new ArrayList<>();
		
		for (int i = 0; i < numberOfMills; ++i) {
			mills.add(new Mill(new ArrayList<>()));
		}
		return mills;
	}

	@Test
	public void testOneMillOneRemoval() throws ParsingException, InvalidCommandException {
		CommandForRemovalTests command = makeCommand(1); 
		when(parser.parseCommand(commandText, board)).thenReturn(command);

		List<Integer> removeIds = new ArrayList<>();
		removeIds.add(42);
		
		when(board.findMills()).thenReturn(makeMills(1));
		when(board.selectRemovablePieces(1, command.getRemovePieces(), PlayerColor.WHITE)).thenReturn(removeIds);

		phase.parseAndRunCommand(commandText, board, player);
		
		assertEquals(8, opponent.getPiecesInStock() + opponent.getPiecesOnBoard());
		verify(board).removePiece(42);
	}

	@Test
	public void testTwoMillsTwoRemovals() throws ParsingException, InvalidCommandException {
		CommandForRemovalTests command = makeCommand(2); 
		when(parser.parseCommand(commandText, board)).thenReturn(command);

		List<Integer> removeIds = new ArrayList<>();
		removeIds.add(42);
		removeIds.add(24);
		
		when(board.findMills()).thenReturn(makeMills(2));
		when(board.selectRemovablePieces(2, command.getRemovePieces(), PlayerColor.WHITE)).thenReturn(removeIds);

		phase.parseAndRunCommand(commandText, board, player);

		assertEquals(7, opponent.getPiecesInStock() + opponent.getPiecesOnBoard());
		verify(board).removePiece(42);
		verify(board).removePiece(24);
	}
}
