package com.codingame.game;
import java.util.List;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.google.inject.Inject;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.InvalidCommandException;
import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.Phase;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.game.PlayerData;
import com.proxiad.merelles.game.PutCommand;
import com.proxiad.merelles.protocol.InfoGenerator;
import com.proxiad.merelles.protocol.ParserMoveCommand;
import com.proxiad.merelles.protocol.ParserPutCommand;
import com.proxiad.merelles.protocol.ParsingException;
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
		view = new ViewController(graphicEntityModule, board);
		
		PlayerData blackPlayer = new PlayerData(board, PlayerColor.BLACK, numberOfPiecesPerPlayer);
		PlayerData whitePlayer = new PlayerData(board, PlayerColor.WHITE, numberOfPiecesPerPlayer);
		blackPlayer.setOpponent(whitePlayer);
		whitePlayer.setOpponent(blackPlayer);

		boolean firstPlayer = true;
		for (Player player : gameManager.getActivePlayers()) {
			if (firstPlayer) {
				player.setData(blackPlayer);
				firstPlayer = false;
			}
			else {
				player.setData(whitePlayer);
			}
		}
		view.update();
	}

	@Override
	public void gameTurn(int turn) {
		Player player = gameManager.getPlayer(turn % gameManager.getPlayerCount());

		gameTurnForPlayer(turn, player);

		view.update();
	}

	private void gameTurnForPlayer(int turn, Player player) {
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

		// TODO end of game
		//if (player.getData().getPiecesInStock() == 0 && player.getData().getOpponent().getPiecesInStock() == 0) {
		if (turn > 300) {
			gameManager.endGame();
		}
	}
	
	private String prependNickname(String message, Player player) {
		StringBuilder builder = new StringBuilder();
		builder.append(player.getNicknameToken());
		builder.append(' ');
		builder.append(message);
		return builder.toString();
	}
}
