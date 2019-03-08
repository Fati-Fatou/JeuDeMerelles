package com.proxiad.merelles.game;

public class Command {

	// The piece to be moved
	private Piece piece;

	public Command(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * The piece to be moved
	 * @return A piece. Not null.
	 */
	public Piece getMovedPiece() {
		return piece;
	}

	public Location getTargetLocation() {
		return new Location(3,2);
	}
}
