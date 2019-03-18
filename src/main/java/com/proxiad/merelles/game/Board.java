package com.proxiad.merelles.game;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Board {
	
	private int nextId = 1;
	private Map<Integer, Piece> knownPieces = new HashMap<>();
	
	/**
	 * All known pieces.
	 * @return All known pieces, as a stream.
	 */
	public Stream<Piece> pieces() {
		return knownPieces.values().stream();
	}
	
	public Piece findPieceById(int id) throws UnknownPieceException {
		if (knownPieces.containsKey(id)) {
			return knownPieces.get(id);
		}
		throw new UnknownPieceException();
	}

	public int putPiece(Location location, PlayerColor color) {
		Piece addedPiece = new Piece(nextId++, color, location);
		knownPieces.put(addedPiece.getId(), addedPiece);
		return addedPiece.getId();
	}
}
