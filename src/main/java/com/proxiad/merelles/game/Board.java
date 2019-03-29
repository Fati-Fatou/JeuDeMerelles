package com.proxiad.merelles.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
	
	public interface BoardObserver {
		void pieceAdded(Piece piece);
		void pieceTaken(Piece piece);
	}
	
	public class MillDetector {
		private List<Location> locations;
		private List<Set<Integer>> alreadySeen;

		public MillDetector(List<Location> locations) {
			this.locations = locations;
			alreadySeen = new ArrayList<>();
		}
		
		public Mill detect() {
			Optional<PlayerColor> color = Optional.empty();
			Map<Integer, Piece> constituents = new HashMap<>();
			
			for (int i = 0; i < locations.size(); ++i) {
				Optional<Piece> piece = findByLocation(locations.get(i));
				
				if (!piece.isPresent()) {
					// empty slot
					return null;
				}
				
				Piece actualPiece = piece.get();
				
				if (color.isPresent()) {
					if (actualPiece.getColor() != color.get()) {
						// wrong color
						return null;
					}
				}
				else {
					// first slot: save the color of the mill
					color = Optional.of(actualPiece.getColor());
				}
				constituents.put(actualPiece.getId(), actualPiece);
			}
			
			Set<Integer> constituentsKeys = constituents.keySet();
			if (isAlreadySeen(constituentsKeys)) {
				return null;
			}
			alreadySeen.add(constituentsKeys);
			return new Mill(constituents.values());
		}

		private boolean isAlreadySeen(Set<Integer> constituents) {
			return alreadySeen.stream().anyMatch(seen -> {
				for (Integer pieceId : constituents) {
					if (!seen.contains(pieceId)) {
						return false;
					}
				}
				return true;
			});
		}
	}
	
	private int nextId = 1;
	private Map<Integer, Piece> knownPieces = new HashMap<>();
	
	private int turnsLeft = 200;
	
	private List<BoardObserver> observers = new ArrayList<>();
	private List<MillDetector> millsDetectors;
	
	public Board() {
		makeMillsDetectors();
	}
	
	private void makeMillsDetectors() {
		millsDetectors = new ArrayList<>();
	
		// One radial mill per straight (odd) directions
		for (int direction = 1; direction < 8; direction += 2) {
			List<Location> locations = new ArrayList<>();
			for (int radius = 0; radius < 3; ++radius) {
				locations.add(new Location(direction, radius));
			}
			millsDetectors.add(new MillDetector(locations));
		}

		// Three transverse mills per even direction and per radius
		for (int direction = 0; direction < 8; direction += 2) {
			for (int radius = 0; radius < 3; ++radius) {
				List<Location> locations = new ArrayList<>();

				for (int d = direction; d < direction + 3; ++d) {
					locations.add(new Location(d, radius));
				}
				millsDetectors.add(new MillDetector(locations));
			}
		}		
	}
	
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
	
	public void movePiece(Piece piece, Location targetLocation) throws InvalidCommandException {
		piece.move(targetLocation);
	}

	public int getTurnsLeft() {
		return turnsLeft;
	}
	
	public Optional<Piece> findByLocation(Location location) {
		return pieces().filter(piece -> location.equals(piece.getLocation())).findFirst();
	}
	
	public boolean isLocationFree(Location location) {
		return !findByLocation(location).isPresent();
	}
	
	public void addListener(BoardObserver observer) {
		observers.add(observer);
	}

	public List<Mill> findMills() {
		return millsDetectors.stream()
				.map(MillDetector::detect)
				.filter(mill -> mill != null)
				.collect(Collectors.toList());
	}
}
