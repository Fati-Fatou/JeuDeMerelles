package com.proxiad.merelles.game;

public class MoveCommand extends Command {

	// The piece to be moved
	private Piece piece;

	public MoveCommand(Piece piece, Location targetLocation) {
		this(piece, targetLocation, null, null);
	}

	public MoveCommand(Piece piece, Location targetLocation, Piece removePiece) {
		this(piece, targetLocation, removePiece, null);
	}
	
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
	
	@Override
	public void run(Board board, PlayerData player) throws InvalidCommandException {
		Piece movedPiece = getMovedPiece();
		Location target = getTargetLocation();
		
		if (movedPiece == null 
				|| !movedPiece.getColor().equals(player.getColor())
				|| !board.isLocationFree(target)
				|| !target.isAdjacent(movedPiece.getLocation())) {
			throw new InvalidCommandException();
		}
		
		board.movePiece(movedPiece, target);
	}
}
