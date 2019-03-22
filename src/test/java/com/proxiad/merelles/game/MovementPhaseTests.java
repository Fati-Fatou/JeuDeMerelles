package com.proxiad.merelles.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.proxiad.merelles.protocol.ParserMoveCommand;

public class MovementPhaseTests {

	public static class MockedMovementPhase extends MovementPhase {
		
		public MockedMovementPhase() {
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
	public void testParserIsForMoveOnly() {
		MockedMovementPhase phase = new MockedMovementPhase();
		assertTrue(phase.parserType().isAssignableFrom(ParserMoveCommand.class));
	}
	
	@Test
	public void testSuggestedMovesOnePiece() {
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
		board.putPiece(new Location(0,1), PlayerColor.BLACK);
		PlayerData player = new PlayerData(board, PlayerColor.BLACK, 9);
		MovementPhase movementPhase = new MovementPhase(player);
		
		movementPhase.suggest(board, formatter).collect(Collectors.toList());

		assertEquals(2, commands.size());
		assertTrue(commands.stream().anyMatch(move -> move.getTargetLocation().equals(new Location(7,1))));
		assertTrue(commands.stream().anyMatch(move -> move.getTargetLocation().equals(new Location(1,1))));
		verify(formatter, never()).formatPut(ArgumentMatchers.any());
	}

	@Test
	public void testSuggestedMovesTwoPieces() {
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
		board.putPiece(new Location(0,1), PlayerColor.BLACK);
		board.putPiece(new Location(4,2), PlayerColor.BLACK);
		PlayerData player = new PlayerData(board, PlayerColor.BLACK, 9);
		MovementPhase movementPhase = new MovementPhase(player);
		
		movementPhase.suggest(board, formatter).collect(Collectors.toList());

		assertEquals(4, commands.size());
		assertTrue(commands.stream().anyMatch(move -> move.getTargetLocation().equals(new Location(7,1))));
		assertTrue(commands.stream().anyMatch(move -> move.getTargetLocation().equals(new Location(1,1))));
		assertTrue(commands.stream().anyMatch(move -> move.getTargetLocation().equals(new Location(3,2))));
		assertTrue(commands.stream().anyMatch(move -> move.getTargetLocation().equals(new Location(5,2))));
		verify(formatter, never()).formatPut(ArgumentMatchers.any());
	}
	
	@Test
	public void testSuggestedMovesTwoPiecesWithConstraints() {
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
		board.putPiece(new Location(0,1), PlayerColor.BLACK);
		board.putPiece(new Location(4,2), PlayerColor.BLACK);
		board.putPiece(new Location(1,1), PlayerColor.WHITE);
		board.putPiece(new Location(3,2), PlayerColor.BLACK);
		PlayerData player = new PlayerData(board, PlayerColor.BLACK, 9);
		MovementPhase movementPhase = new MovementPhase(player);
		
		movementPhase.suggest(board, formatter).collect(Collectors.toList());

		assertEquals(4, commands.size());
		assertTrue(commands.stream().anyMatch(move -> move.getTargetLocation().equals(new Location(7,1))));
		assertTrue(commands.stream().anyMatch(move -> move.getTargetLocation().equals(new Location(5,2))));
		assertTrue(commands.stream().anyMatch(move -> move.getTargetLocation().equals(new Location(2,2))));
		assertTrue(commands.stream().anyMatch(move -> move.getTargetLocation().equals(new Location(5,2))));
		verify(formatter, never()).formatPut(ArgumentMatchers.any());
	}
}
