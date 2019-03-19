package com.proxiad.merelles.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.codingame.game.Player;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.PlayerColor;

public class InfoGeneratorTests {

	static class ControlledColorPlayer extends Player {
		private PlayerColor color;
		
		ControlledColorPlayer(PlayerColor color) {
			this.color = color;
		}

		@Override
		public PlayerColor getColor() {
			return color;
		}
	}
	
	private Board board;
	private Player whitePlayer;
	private Player blackPlayer;
	private InfoGenerator generator;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		whitePlayer = new ControlledColorPlayer(PlayerColor.WHITE);
		blackPlayer = new ControlledColorPlayer(PlayerColor.BLACK);
		generator = new InfoGenerator();
	}

	@Test
	public void testEmptyBoardInfo() {
		List<String> blackInfos = generator.gameInfoForPlayer(board, whitePlayer).collect(Collectors.toList());

		// Info line,
		// nb pieces = 0 (empty board)
		// (0 lines for pieces)
		// nb moves = 24 (24 free slots on the board)
		// one line per slot
		assertEquals(3 + 24, blackInfos.size());
		for (int direction = 0; direction < 8; ++direction) {
			for (int radius = 0; radius < 3; ++radius) {				
				assertTrue(blackInfos.stream().anyMatch(moveTester(direction, radius)));
			}
		}
	}

	protected Predicate<String> moveTester(int direction, int radius) {
		String coordinates = String.format(" %d %d", direction, radius);
		return input -> input.endsWith(coordinates);
	}
	
	@Test
	public void testFirstWhiteInfo() {
		// black player plays
		board.putPiece(new Location(2, 1), PlayerColor.BLACK);
		
		// what does white player receive?
		List<String> whiteInfos = generator.gameInfoForPlayer(board, whitePlayer).collect(Collectors.toList());

		// Info line,
		// nb pieces = 1
		// x BLACK 2 1 = the first black piece
		// nb moves = 23 (23 free slots on the board)
		// one line per slot
		assertEquals(4 + 23, whiteInfos.size());

		// 1 piece on the board
		assertEquals(whiteInfos.get(1), "1");
		assertTrue(whiteInfos.get(2).endsWith(" BLACK 2 1"));
		
		for (int direction = 0; direction < 8; ++direction) {
			for (int radius = 0; radius < 3; ++radius) {
				if (direction != 2 || radius != 1) {
					assertTrue(whiteInfos.stream().anyMatch(moveTester(direction, radius)));
				}
			}
		}
	}
}
