package com.codingame.game;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.google.inject.Inject;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.game.PlayerData;
import com.proxiad.merelles.game.Scores;
import com.proxiad.merelles.view.PlayerView;
import com.proxiad.merelles.view.ViewController;

public class Referee extends AbstractReferee {

	public static final int NUMBER_OF_TURNS_PER_PLAYER = 200;

	private static final int numberOfPiecesPerPlayer = 9;
	
	// Uncomment the line below and comment the line under it to create a Solo Game
	// @Inject private SoloGameManager<Player> gameManager;
	@Inject private MultiplayerGameManager<Player> gameManager;
	@Inject private GraphicEntityModule graphicEntityModule;

	private ViewController view;
	private Board board;

	private int turnsLeft = 2 * NUMBER_OF_TURNS_PER_PLAYER;
	
	/**
	 * Number of game turns left. This is the sum of the number of turns left for each player.
	 * @return Number of game turns left.
	 */
	public int getTurnsLeft() {
		return turnsLeft;
	}

	/**
	 * Number of game turns a player still has to play.
	 * @return Number of turns left for the player.
	 */
	public int getTurnsLeftForPlayer() {
		// rounds up. Turns left 2 and 1 = last turn for both players (hence return 1). 
		return (1 + turnsLeft) / 2;
	}
	
	@Override
	public void init() {
		// Initialize your game here.
		board = new Board();

		PlayerData blackPlayer = new PlayerData(board, PlayerColor.BLACK, numberOfPiecesPerPlayer);
		PlayerData whitePlayer = new PlayerData(board, PlayerColor.WHITE, numberOfPiecesPerPlayer);
		blackPlayer.setOpponent(whitePlayer);
		whitePlayer.setOpponent(blackPlayer);

		PlayerView blackPlayerView = null;
		PlayerView whitePlayerView = null;

		boolean firstPlayer = true;
		for (Player player : gameManager.getActivePlayers()) {
			if (firstPlayer) {
				player.setData(blackPlayer);
				blackPlayerView = new PlayerView(graphicEntityModule, player);
				firstPlayer = false;
			}
			else {
				player.setData(whitePlayer);
				whitePlayerView = new PlayerView(graphicEntityModule, player);
			}
		}

		view = new ViewController(graphicEntityModule, board, blackPlayerView, whitePlayerView);
		view.update(new Scores(PlayerColor.BLACK, numberOfPiecesPerPlayer, numberOfPiecesPerPlayer, false));
	}

	@Override
	public void gameTurn(int turn) {
		Player player = gameManager.getPlayer(turn % gameManager.getPlayerCount());

		player.play(getTurnsLeftForPlayer(), board);

		--turnsLeft;
		
		Scores scores = computeScores(turn, player.getData());

		for (Player scoredPlayer : gameManager.getPlayers()) {
			scoredPlayer.setScore(scores.scoreForPlayer(scoredPlayer.getData().getColor()));
		}

		if (scores.gameOver()) {
			gameManager.endGame();
		}

		view.update(scores);
	}

	public Scores computeScores(int turn, PlayerData player) {
		// TODO end of game
		boolean isGameOver = turnsLeft <= 0 || 
				player.getPiecesInStock() == 0 && player.getPiecesOnBoard() <= 2 || 
				player.getOpponent().getPiecesInStock() == 0 && player.getPiecesOnBoard() <= 2;
		return new Scores(player.getColor(), player.score(), player.getOpponent().score(), isGameOver);
	}
}
