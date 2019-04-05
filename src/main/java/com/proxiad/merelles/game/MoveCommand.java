package com.proxiad.merelles.game;

import java.util.Collection;

public class MoveCommand extends Command {

	// The piece to be moved
	private Piece piece;
	
	public MoveCommand(Piece piece, Location targetLocation) {
		this(piece, targetLocation, null, null);
	}

	public MoveCommand(Piece piece, Location targetLocation, Collection<Piece> removePieces) {
		this(piece, targetLocation, removePieces, null);
	}
	
	public MoveCommand(Piece piece, Location targetLocation, Collection<Piece> removePieces, String message) {
		super(targetLocation, removePieces, message);
		this.piece = piece;
	}
	
	/**
	 * The piece to be moved. Null for initial placement.
	 * @return A piece, or null.
	 */
	public Piece getMovedPiece() {
		return piece;
	}
	
	@Override
	public void run(Board board, PlayerData player) throws InvalidCommandException {
		Piece movedPiece = getMovedPiece();
		Location target = getTargetLocation();
		
		if (movedPiece == null 
				|| !movedPiece.getColor().equals(player.getColor())
				|| !board.isLocationFree(target)
				|| isInvalidJump()) {
			throw new InvalidCommandException();
		}
		
		board.movePiece(movedPiece, target);
	}

	protected boolean isInvalidJump() {
		return isJump();
	}
	
	protected boolean isJump() {
		Piece movedPiece = getMovedPiece();
		Location target = getTargetLocation();

		return !target.isAdjacent(movedPiece.getLocation());
	}
}
