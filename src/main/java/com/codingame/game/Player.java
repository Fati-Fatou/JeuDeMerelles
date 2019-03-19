package com.codingame.game;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.proxiad.merelles.game.PlayerColor;

// Uncomment the line below and comment the line under it to create a Solo Game
// public class Player extends AbstractSoloPlayer {
public class Player extends AbstractMultiplayerPlayer {
	
    @Override
    public int getExpectedOutputLines() {
        // Returns the number of expected lines of outputs for a player
        return 1;
    }
    
    public PlayerColor getColor() {
    	return getIndex() == 0 ? PlayerColor.BLACK : PlayerColor.WHITE;
    }
    
    public int colorToOwnerId(PlayerColor aColor) {
    	return getColor() == aColor ? 0 : 1;
    }
}
