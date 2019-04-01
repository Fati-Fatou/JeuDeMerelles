package com.proxiad.merelles.game;

public class Scores {

	private int whiteScore;
	private int blackScore;
	private boolean isGameOver;
	
	public Scores(PlayerColor myColor, int myScore, int opponentScore, boolean isGameOver) {
		if (myColor == PlayerColor.BLACK) {
			blackScore = myScore;
			whiteScore = opponentScore;
		}
		else {
			blackScore = opponentScore;
			whiteScore = myScore;
		}
		
		this.isGameOver = isGameOver;
	}
	
	public int scoreForPlayer(PlayerColor color) {
		return color == PlayerColor.BLACK ? scoreOfBlackPlayer() : scoreOfWhitePlayer();
	}
	
	public int scoreOfBlackPlayer() {
		return blackScore;
	}
	
	public int scoreOfWhitePlayer() {
		return whiteScore;
	}
	
	public boolean gameOver() {
		return isGameOver;
	}
}
