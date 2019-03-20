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
	
	private int blackStock = 9;
	private int whiteStock = 9;
	private int blackPieces = 0;
	private int whitePieces = 0;
	
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
		if(color == PlayerColor.BLACK) {
			++blackPieces;
			--blackStock;
		}
		else {
			++whitePieces;
			--whiteStock;
		}
		
		observers.forEach(observer -> observer.pieceAdded(addedPiece));
		
		return addedPiece.getId();
	}

	public int getTurnsLeft() {
		return turnsLeft;
	}
	
	public int getBlackStock() {
		return blackStock;
	}

	public int getWhiteStock() {
		return whiteStock;
	}

	public int getBlackPieces() {
		return blackPieces;
	}

	public int getWhitePieces() {
		return whitePieces;
	}
	
	public void runCommand(PlayerColor playerColor, Command command) throws InvalidCommandException {
		// TODO
		if (command.getMovedPiece() == null) {
			putPiece(command.getTargetLocation(), playerColor);
		}
	}
	
	public void addListener(BoardObserver observer) {
		observers.add(observer);
	}
	
	public boolean isGameEnded() {
		// TODO
		return blackStock == 0 && whiteStock == 0;
	}
}
