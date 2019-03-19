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
	 * The piece to be moved. Null for initial placement.
	 * @return A piece.
	 */
	public Piece getMovedPiece() {
		return piece;
	}

	public Location getTargetLocation() {
		return targetLocation;
	}
}
