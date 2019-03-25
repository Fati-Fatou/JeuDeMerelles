package com.proxiad.merelles.game;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

	private List<Piece> removePieces;
	private String message;
	private Location targetLocation;

	protected Command(Location targetLocation) {
		this(targetLocation, null, null);
	}

	protected Command(Location targetLocation, Piece removePiece) {
		this(targetLocation, removePiece, null);
	}

	protected Command(Location targetLocation, Piece removePiece, String message) {
		this.targetLocation = targetLocation;
		this.removePieces = new ArrayList<>();
		if (removePiece != null) {
			removePieces.add(removePiece);
		}
		this.message = message != null ? message.trim() : "";
	}
	
	/**
	 * The piece to be removed, in case of a mill. Null if not specified.
	 * @return List of pieces.
	 */
	public List<Piece> getRemovePieces() {
		return removePieces;
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
	
	public abstract void run(Board board, PlayerData player) throws InvalidCommandException;
}
