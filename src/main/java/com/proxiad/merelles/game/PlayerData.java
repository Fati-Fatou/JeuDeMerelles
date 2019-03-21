package com.proxiad.merelles.game;

public class PlayerData {
	private PlayerColor color;
	private PlayerData opponent;
	private int piecesOnBoard;
	private int piecesInStock;
	
	public PlayerData(PlayerColor color, int numberOfStockPieces) {
		this.color = color;
		piecesInStock = numberOfStockPieces;
	}
	
    public PlayerColor getColor() {
    	return color;
    }
    
    public PlayerData getOpponent() {
    	return this.opponent;
    }
    
    public void setOpponent(PlayerData opponent) {
    	this.opponent = opponent;
    }

	public int getPiecesOnBoard() {
		return piecesOnBoard;
	}

	public int getPiecesInStock() {
		return piecesInStock;
	}
}
