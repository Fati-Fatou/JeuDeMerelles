package com.proxiad.merelles.game;

public class PlayerData {
	
	private static final Phase placementPhase = new PlacementPhase();
	private static final Phase movementPhase = new MovementPhase();
	
	private Board board;
	private PlayerColor color;
	private PlayerData opponent;
	private int piecesOnBoard;
	private int piecesInStock;
	
	public PlayerData(Board board, PlayerColor color, int numberOfStockPieces) {
		this.board = board;
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

	public void updateCountsAfterPut() {
		++piecesOnBoard;
		--piecesInStock;		
	}

	public Phase getPhase() {
		return getPiecesInStock() > 0 ? placementPhase : movementPhase;
	}
}
