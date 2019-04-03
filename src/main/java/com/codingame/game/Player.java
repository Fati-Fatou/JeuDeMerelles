package com.codingame.game;
import java.util.List;

import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.InvalidCommandException;
import com.proxiad.merelles.game.NoPossibleMovesException;
import com.proxiad.merelles.game.Phase;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.game.PlayerData;
import com.proxiad.merelles.protocol.InfoGenerator;
import com.proxiad.merelles.protocol.ParsingException;

// Uncomment the line below and comment the line under it to create a Solo Game
// public class Player extends AbstractSoloPlayer {
public class Player extends AbstractMultiplayerPlayer {
	
	private PlayerData data;
	
    @Override
    public int getExpectedOutputLines() {
        // Returns the number of expected lines of outputs for a player
        return 1;
    }
    
    public PlayerData getData() {
    	return data;
    }
    
    public void setData(PlayerData data) {
    	this.data = data;
    }
    
    public int colorToOwnerId(PlayerColor aColor) {
    	return data.getColor() == aColor ? 0 : 1;
    }
    
    /**
     * Runs a turn for the player.
     * @param turnsLeft Number of turns left for the player (starts at 200)
     * @param board Situation on the board before the player's turn begins
     */
	public void play(int turnsLeft, Board board) {
		
		if (!isActive()) {
			return;
		}
		
		InfoGenerator generator = new InfoGenerator();
		try {
			generator.gameInfoForPlayer(board, this, turnsLeft).forEach(this::sendInputLine);
		} catch (NoPossibleMovesException e1) {
			deactivate(prependNickname("stuck!"));
			return;
		}
		
		execute();

		try {
			List<String> outputs = getOutputs();
			// Check validity of the player output and compute the new game state

			Phase phase = getData().getPhase();
			phase.parseAndRunCommand(outputs.get(0), board, getData());

		} catch (TimeoutException e) {
			deactivate(prependNickname("timeout!"));
		} catch (ParsingException e) {
			deactivate(prependNickname("unrecognized command!"));
			e.printStackTrace();
		} catch (InvalidCommandException e) {
			deactivate(prependNickname(e.getMessage()));
		} catch (Exception e) {
			deactivate(prependNickname(e.getMessage()));
			e.printStackTrace();
		}
	}
	
	private String prependNickname(String message) {
		StringBuilder builder = new StringBuilder();
		builder.append(getNicknameToken());
		builder.append(' ');
		builder.append(message);
		return builder.toString();
	}
}
