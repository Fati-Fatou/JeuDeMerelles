package com.proxiad.merelles.game;

import java.util.Collection;

public class MoveOrJumpCommand extends MoveCommand {

	public MoveOrJumpCommand(Piece piece, Location targetLocation) {
		this(piece, targetLocation, null, null);
	}

	public MoveOrJumpCommand(Piece piece, Location targetLocation, Collection<Piece> removePieces) {
		this(piece, targetLocation, removePieces, null);
	}
	
	public MoveOrJumpCommand(Piece piece, Location targetLocation, Collection<Piece> removePieces, String message) {
		super(piece, targetLocation, removePieces, message);
	}
	
	@Override
	protected boolean isInvalidJump() {
		// allows jumps
		return false;
	}
}
