package com.proxiad.merelles.game;

public class Command {

	// The piece to be moved
	private Piece piece;

	// Where the piece should be moved
	private Location targetLocation;
	
	// The piece to be removed, in case of mill
	private Piece removePiece;
	
	public Command(Piece piece, Location targetLocation, Piece removePiece) {
		this.piece = piece;
		this.targetLocation = targetLocation;
		this.removePiece = removePiece;
	}
	
	/**
	 * The piece to be moved. Null for initial placement.
	 * @return A piece, or null.
	 */
	public Piece getMovedPiece() {
		return piece;
	}

	/**
	 * Where the piece should be put or moved to.
	 * @return A location. Not null.
	 */
	public Location getTargetLocation() {
		return targetLocation;
	}
	
	/**
	 * The piece to be removed, in case of a mill. Null if not specified.
	 * @return A piece, or null.
	 */
	public Piece getRemovePiece() {
		return removePiece;
	}
}
