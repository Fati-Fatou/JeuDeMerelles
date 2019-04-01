package com.proxiad.merelles.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ScoresTests {

	@Test
	public void testWhiteScore() {
		Scores scores = new Scores(PlayerColor.WHITE, 13, 38, false);
		assertEquals(13, scores.scoreOfWhitePlayer());
		assertEquals(13, scores.scoreForPlayer(PlayerColor.WHITE));
		assertEquals(38, scores.scoreOfBlackPlayer());
		assertEquals(38, scores.scoreForPlayer(PlayerColor.BLACK));
		assertFalse(scores.gameOver());
	}

	@Test
	public void testBlackScore() {
		Scores scores = new Scores(PlayerColor.BLACK, 13, 38, true);
		assertEquals(38, scores.scoreOfWhitePlayer());
		assertEquals(13, scores.scoreOfBlackPlayer());
		assertTrue(scores.gameOver());
	}
}
