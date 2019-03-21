package com.proxiad.merelles.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Board {
	
	public interface BoardObserver {
		void pieceAdded(Piece piece);
		void pieceTaken(Piece piece);
	}
	
	private int nextId = 1;
	private Map<Integer, Piece> knownPieces = new HashMap<>();
	
	private int turnsLeft = 200;
	
	private List<BoardObserver> observers = new ArrayList<>();
	
	/**
	 * All known pieces.
	 * @return All known pieces, as a stream.
	 */
	public Stream<Piece> pieces() {
		return knownPieces.values().stream();
	}
	
	public Piece findPieceById(int id) {
		if (knownPieces.containsKey(id)) {
			return knownPieces.get(id);
		}
		return null;
	}

	public int putPiece(Location location, PlayerColor color) {
		Piece addedPiece = new Piece(nextId++, color, location);
		knownPieces.put(addedPiece.getId(), addedPiece);
		
		observers.forEach(observer -> observer.pieceAdded(addedPiece));
		
		return addedPiece.getId();
	}

	public int getTurnsLeft() {
		return turnsLeft;
	}
	
	public boolean isLocationFree(Location location) {
		return !(pieces().anyMatch(piece -> location.equals(piece.getLocation())));
	}
	
	public void addListener(BoardObserver observer) {
		observers.add(observer);
	}
}
