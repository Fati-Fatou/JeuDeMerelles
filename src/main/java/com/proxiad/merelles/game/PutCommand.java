package com.proxiad.merelles.game;

public class PutCommand extends Command {

	public PutCommand(Location targetLocation) {
		this(targetLocation, null, null);
	}

	public PutCommand(Location targetLocation, Piece removePiece) {
		this(targetLocation, removePiece, null);
	}
	
	public PutCommand(Location targetLocation, Piece removePiece, String message) {
		super(targetLocation, removePiece, message);
	}
	
	@Override
	public void run(Board board, PlayerData player) throws InvalidCommandException {
		Location targetLocation = getTargetLocation();
		if (board.isLocationFree(targetLocation)) {
			board.putPiece(targetLocation, player.getColor());
			player.updateCountsAfterPut();
		}
		else throw new InvalidCommandException();
	}
}
