package com.codingame.game;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.game.PlayerData;

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
}
