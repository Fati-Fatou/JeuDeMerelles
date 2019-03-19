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
import com.proxiad.merelles.game.UnknownPieceException;
import com.proxiad.merelles.protocol.InfoGenerator;
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
		InfoGenerator generator = new InfoGenerator();
		for (Player player : gameManager.getActivePlayers()) {
			generator.gameInfoForPlayer(board, player).forEach(player::sendInputLine);
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
			int id = board.putPiece(location, color);
			try {
				view.registerPieceView(board.findPieceById(id));
			} catch (UnknownPieceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(turn == 18) {
			try {
				board.findPieceById(1).move(new Location(7,0));
			} catch (UnknownPieceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		view.update();
	}
}
