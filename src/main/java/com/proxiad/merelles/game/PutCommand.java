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
}
