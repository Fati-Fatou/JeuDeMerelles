package com.proxiad.merelles.game;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.proxiad.merelles.protocol.ParserPutCommand;

public class PlacementPhaseTests {

	public static class MockedPlacementPhase extends PlacementPhase {
		public Class<?> parserType() {
			return parser().getClass();
		}
	}

	@Test
	public void testParserIsForPutOnly() {
		MockedPlacementPhase phase = new MockedPlacementPhase();
		assertTrue(phase.parserType().isAssignableFrom(ParserPutCommand.class));
	}
}
