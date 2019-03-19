package com.codingame.game;
import java.util.List;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.google.inject.Inject;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.protocol.InfoGenerator;
import com.proxiad.merelles.protocol.Parser;
import com.proxiad.merelles.protocol.ParsingException;
import com.proxiad.merelles.view.ViewController;

public class Referee extends AbstractReferee {
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

		PlayerColor nextColor = PlayerColor.BLACK;
		for (Player player : gameManager.getActivePlayers()) {
			player.setColor(nextColor);
			nextColor = PlayerColor.WHITE;
		}
		view.update();
	}

	@Override
	public void gameTurn(int turn) {
		InfoGenerator generator = new InfoGenerator();
		for (Player player : gameManager.getActivePlayers()) {
			generator.gameInfoForPlayer(board, player).forEach(player::sendInputLine);
			player.execute();
		}

		for (Player player : gameManager.getActivePlayers()) {
			if (player.getIndex() % 2 == turn % 2) {
				System.err.println("Player " + player.getColor() + " at turn " + turn);
				try {
					List<String> outputs = player.getOutputs();
					// Check validity of the player output and compute the new game state

					Parser parser = new Parser();
					Command command = parser.parse(outputs.get(0), board);
					board.runCommand(player.getColor(), command);
				} catch (TimeoutException e) {
					player.deactivate(String.format("$%d timeout!", player.getIndex()));
				} catch (ParsingException e) {
					e.printStackTrace();
					player.deactivate(String.format("$%d invalid command!", player.getIndex()));
				}
			}
		}        

		view.update();
	}
}
