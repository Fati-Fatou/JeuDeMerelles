package com.proxiad.merelles.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.proxiad.merelles.protocol.ParserMoveOrJumpCommand;

public class JumpPhaseTests {

	public static class MockedJumpPhase extends JumpPhase {
		
		public MockedJumpPhase() {
			super(null);
		}
		
		public Class<?> parserType() {
			return parser().getClass();
		}
	}
	
	@Mock
	private CommandFormatter formatter;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testParserIsForMoveOrJump() {
		MockedJumpPhase phase = new MockedJumpPhase();
		assertTrue(phase.parserType().isAssignableFrom(ParserMoveOrJumpCommand.class));
	}
	
	@Test
	public void testSuggestedMovesTwoPiecesNoJumps() {
		List<MoveCommand> commands = new ArrayList<>();
		when(formatter.formatMove(ArgumentMatchers.any())).thenAnswer(new Answer<String>() {
		    @Override
		    public String answer(InvocationOnMock invocation) throws Throwable {
		      Object[] args = invocation.getArguments();
		      commands.add((MoveCommand) args[0]);
		      return "";
		    }
		});

		Board board = new Board();
		int id1 = board.putPiece(new Location(0,1), PlayerColor.BLACK);
		int id2 = board.putPiece(new Location(4,2), PlayerColor.BLACK);
		PlayerData player = new PlayerData(board, PlayerColor.BLACK, 9);
		JumpPhase jumpPhase = new JumpPhase(player);
		
		jumpPhase.suggest(board, formatter).collect(Collectors.toList());

		assertEquals(4, commands.size());
		checkMove(commands, id1, 7, 1);
		checkMove(commands, id1, 1, 1);
		checkMove(commands, id2, 3, 2);
		checkMove(commands, id2, 5, 2);
		verify(formatter, never()).formatPut(ArgumentMatchers.any());
	}
	
	@Test
	public void testSuggestedMovesTwoPiecesWithJumps() {
		List<MoveCommand> commands = new ArrayList<>();
		when(formatter.formatMove(ArgumentMatchers.any())).thenAnswer(new Answer<String>() {
		    @Override
		    public String answer(InvocationOnMock invocation) throws Throwable {
		      Object[] args = invocation.getArguments();
		      commands.add((MoveCommand) args[0]);
		      return "";
		    }
		});

		Board board = new Board();
		int id1 = board.putPiece(new Location(0,1), PlayerColor.BLACK);
		int id2 = board.putPiece(new Location(4,2), PlayerColor.BLACK);
		
		// adding constraints to all possible regular moves
		board.putPiece(new Location(7,1), PlayerColor.WHITE);
		board.putPiece(new Location(1,1), PlayerColor.WHITE);
		board.putPiece(new Location(3,2), PlayerColor.WHITE);
		board.putPiece(new Location(5,2), PlayerColor.WHITE);

		// and some jump locations too
		board.putPiece(new Location(1,2), PlayerColor.WHITE);
		board.putPiece(new Location(2,2), PlayerColor.WHITE);
		board.putPiece(new Location(6,1), PlayerColor.WHITE);
		board.putPiece(new Location(6,0), PlayerColor.WHITE);
		

		PlayerData player = new PlayerData(board, PlayerColor.BLACK, 9);
		JumpPhase jumpPhase = new JumpPhase(player);
		
		jumpPhase.suggest(board, formatter).collect(Collectors.toList());

		// expecting all possible jumps
		assertEquals(28, commands.size());
		checkJumpsForPiece(commands, id1);
		checkJumpsForPiece(commands, id2);
		verify(formatter, never()).formatPut(ArgumentMatchers.any());
	}

	private void checkJumpsForPiece(List<MoveCommand> commands, int pieceId) {
		checkMove(commands, pieceId, 0, 0);
		checkMove(commands, pieceId, 0, 2);
		checkMove(commands, pieceId, 1, 0);
		checkMove(commands, pieceId, 2, 0);
		checkMove(commands, pieceId, 2, 1);
		checkMove(commands, pieceId, 3, 0);
		checkMove(commands, pieceId, 3, 1);
		checkMove(commands, pieceId, 4, 0);
		checkMove(commands, pieceId, 4, 1);
		checkMove(commands, pieceId, 5, 0);
		checkMove(commands, pieceId, 5, 1);
		checkMove(commands, pieceId, 6, 2);
		checkMove(commands, pieceId, 7, 0);
		checkMove(commands, pieceId, 7, 2);
	}

	private void checkMove(List<MoveCommand> commands, int pieceId, int direction, int radius) {
		assertTrue(commands.stream().anyMatch(assertCommandsContains(pieceId, direction, radius)));
	}
	
	private static Predicate<? super MoveCommand> assertCommandsContains(int pieceId, int direction, int radius) {
		Location location = new Location(direction, radius);
		return move -> move.getMovedPiece().getId() == pieceId && move.getTargetLocation().equals(location);
	}
}
