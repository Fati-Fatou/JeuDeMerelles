package com.proxiad.merelles.game;

public class Command {

	// The piece to be moved
	private Piece piece;

	// Where the piece should be moved
	private Location targetLocation;
	
	public Command(Piece piece, Location targetLocation) {
		this.piece = piece;
		this.targetLocation = targetLocation;
	}
	
	/**
	 * The piece to be moved
	 * @return A piece. Not null.
	 */
	public Piece getMovedPiece() {
		return piece;
	}

	public Location getTargetLocation() {
		return targetLocation;
	}
}
