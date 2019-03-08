package com.proxiad.merelles.game;

public class Command {

	// ID of the piece to be moved
	int pieceId;

	public Command(int pieceId) {
		this.pieceId = pieceId;
	}
	
	/**
	 * ID of the piece to be moved
	 * @return ID of a piece
	 */
	public int getPieceId() {
		return pieceId;
	}
}
