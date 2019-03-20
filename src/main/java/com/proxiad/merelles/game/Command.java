package com.proxiad.merelles.game;

public class Command {

	protected Piece removePiece;
	protected String message;
	protected Location targetLocation;

	protected Command(Location targetLocation) {
		this(targetLocation, null, null);
	}

	protected Command(Location targetLocation, Piece removePiece) {
		this(targetLocation, removePiece, null);
	}

	protected Command(Location targetLocation, Piece removePiece, String message) {
		this.targetLocation = targetLocation;
		this.removePiece = removePiece;
		this.message = message != null ? message.trim() : "";
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

	/**
	 * Where the piece should be put or moved to.
	 * @return A location. Not null.
	 */
	public Location getTargetLocation() {
		return targetLocation;
	}

}
