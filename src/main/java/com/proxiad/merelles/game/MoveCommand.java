package com.proxiad.merelles.game;

public class MoveCommand extends Command {

	// The piece to be moved
	private Piece piece;

	public MoveCommand(Piece piece, Location targetLocation, Piece removePiece, String message) {
		super(targetLocation, removePiece, message);
		this.piece = piece;
	}
	
	/**
	 * The piece to be moved. Null for initial placement.
	 * @return A piece, or null.
	 */
	public Piece getMovedPiece() {
		return piece;
	}
}