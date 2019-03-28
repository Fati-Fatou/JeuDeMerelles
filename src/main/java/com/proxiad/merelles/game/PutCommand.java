package com.proxiad.merelles.game;

import java.util.Collection;

public class PutCommand extends Command {

	public PutCommand(Location targetLocation) {
		this(targetLocation, null, null);
	}

	public PutCommand(Location targetLocation, Collection<Piece> removePieces) {
		this(targetLocation, removePieces, null);
	}
	
	public PutCommand(Location targetLocation, Collection<Piece> removePieces, String message) {
		super(targetLocation, removePieces, message);
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
