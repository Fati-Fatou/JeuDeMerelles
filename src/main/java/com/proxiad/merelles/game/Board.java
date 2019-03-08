package com.proxiad.merelles.game;

public class Board {
	
	public Piece findPieceById(int id) throws UnknownPieceException {
		return new Piece(id);
	}

}
