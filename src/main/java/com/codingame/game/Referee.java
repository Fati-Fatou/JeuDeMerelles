package com.codingame.game;
import java.util.List;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.google.inject.Inject;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.InvalidCommandException;
import com.proxiad.merelles.game.Phase;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.game.PlayerData;
import com.proxiad.merelles.game.Scores;
import com.proxiad.merelles.protocol.InfoGenerator;
import com.proxiad.merelles.protocol.ParsingException;
import com.proxiad.merelles.view.PlayerView;
import com.proxiad.merelles.view.ViewController;

public class Referee extends AbstractReferee {

	private static final int numberOfPiecesPerPlayer = 9;

	// Uncomment the line below and comment the line under it to create a Solo Game
	// @Inject private SoloGameManager<Player> gameManager;
	@Inject private MultiplayerGameManager<Player> gameManager;
	@Inject private GraphicEntityModule graphicEntityModule;

	private ViewController view;
	private Board board;

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

		Scores scores = gameTurnForPlayer(turn, player);

		for (Player scoredPlayer : gameManager.getPlayers()) {
			scoredPlayer.setScore(scores.scoreForPlayer(scoredPlayer.getData().getColor()));
		}

		if (scores.gameOver()) {
			gameManager.endGame();
		}

		view.update(scores);
	}

	private Scores gameTurnForPlayer(int turn, Player player) {
		InfoGenerator generator = new InfoGenerator();
		generator.gameInfoForPlayer(board, player).forEach(player::sendInputLine);
		player.execute();

		try {
			List<String> outputs = player.getOutputs();
			// Check validity of the player output and compute the new game state

			Phase phase = player.getData().getPhase();
			phase.parseAndRunCommand(outputs.get(0), board, player.getData());

		} catch (TimeoutException e) {
			player.deactivate(prependNickname("timeout!", player));
		} catch (ParsingException e) {
			player.deactivate(prependNickname("unrecognized command!", player));
			e.printStackTrace();
		} catch (InvalidCommandException e) {
			player.deactivate(prependNickname(e.getMessage(), player));
		} catch (Exception e) {
			player.deactivate(prependNickname(e.getMessage(), player));
			e.printStackTrace();
		}

		return computeScores(turn, player.getData());
	}

	public Scores computeScores(int turn, PlayerData player) {
		// TODO end of game
		boolean isGameOver = turn > 300 || 
				player.getPiecesInStock() == 0 && player.getPiecesOnBoard() <= 2 || 
				player.getOpponent().getPiecesInStock() == 0 && player.getPiecesOnBoard() <= 2;
		return new Scores(player.getColor(), player.score(), player.getOpponent().score(), isGameOver);
	}

	private String prependNickname(String message, Player player) {
		StringBuilder builder = new StringBuilder();
		builder.append(player.getNicknameToken());
		builder.append(' ');
		builder.append(message);
		return builder.toString();
	}
}
