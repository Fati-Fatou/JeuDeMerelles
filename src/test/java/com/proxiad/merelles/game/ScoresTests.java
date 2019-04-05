package com.proxiad.merelles.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.codingame.game.Referee;

public class ScoresTests {

	@Spy
	Referee referee = new Referee();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testWhiteScoreFromValues() {
		Scores scores = new Scores(PlayerColor.WHITE, 13, 38, false);
		assertEquals(13, scores.scoreOfWhitePlayer());
		assertEquals(13, scores.scoreForPlayer(PlayerColor.WHITE));
		assertEquals(38, scores.scoreOfBlackPlayer());
		assertEquals(38, scores.scoreForPlayer(PlayerColor.BLACK));
		assertFalse(scores.gameOver());
	}

	@Test
	public void testBlackScoreFromValues() {
		Scores scores = new Scores(PlayerColor.BLACK, 13, 38, true);
		assertEquals(38, scores.scoreOfWhitePlayer());
		assertEquals(13, scores.scoreOfBlackPlayer());
		assertTrue(scores.gameOver());
	}
	
	@Test
	public void testInitialTurnsLeftValues() {
		assertEquals(2 * Referee.NUMBER_OF_TURNS_PER_PLAYER, referee.getTurnsLeft());
		assertEquals(Referee.NUMBER_OF_TURNS_PER_PLAYER, referee.getTurnsLeftForPlayer());
	}
}
