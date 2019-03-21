package com.proxiad.merelles.game;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.proxiad.merelles.protocol.ParserMoveCommand;

public class MovementPhaseTests {

	public static class MockedMovementPhase extends MovementPhase {
		public Class<?> parserType() {
			return parser().getClass();
		}
	}

	@Test
	public void testParserIsForMoveOnly() {
		MockedMovementPhase phase = new MockedMovementPhase();
		assertTrue(phase.parserType().isAssignableFrom(ParserMoveCommand.class));
	}
}
