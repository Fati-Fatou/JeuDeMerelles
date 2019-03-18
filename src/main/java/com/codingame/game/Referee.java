package com.codingame.game;
import java.util.List;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.google.inject.Inject;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.PlayerColor;
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
		view = new ViewController(graphicEntityModule);
		board = new Board();
		view.update();
	}

	@Override
	public void gameTurn(int turn) {
		for (Player player : gameManager.getActivePlayers()) {
			player.sendInputLine("input");
			player.execute();
		}

		for (Player player : gameManager.getActivePlayers()) {
			try {
				List<String> outputs = player.getOutputs();
				// Check validity of the player output and compute the new game state
			} catch (TimeoutException e) {
				player.deactivate(String.format("$%d timeout!", player.getIndex()));
			}
		}        
		
		/* Fake implementation, just to have something on the screen */
		if (turn < 18) {
			PlayerColor color = (turn % 2) == 0 ? PlayerColor.BLACK : PlayerColor.WHITE;
			int radius = turn % 3;
			int direction = turn / 3;
			Location location = new Location(direction, radius);
			board.putPiece(location, color);
		}
		view.update();
	}
}
