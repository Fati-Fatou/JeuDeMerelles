package com.proxiad.merelles.game;

public class Command {

	// The piece to be moved
	private Piece piece;

	// Where the piece should be moved
	private Location targetLocation;
	
	// The piece to be removed, in case of mill
	private Piece removePiece;
	
	// The extra message on the command line
	private String message;
	
	public Command(Piece piece, Location targetLocation, Piece removePiece, String message) {
		this.piece = piece;
		this.targetLocation = targetLocation;
		this.removePiece = removePiece;
		this.message = message != null ? message.trim() : "";
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
	
	/**
	 * Extra message on the command line. Not null (replaced with empty string)
	 * @return Message
	 */
	public String getMessage() {
		return message;
	}
}
