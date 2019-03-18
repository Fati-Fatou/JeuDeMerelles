package com.proxiad.merelles.game;

import java.util.ArrayList;
import java.util.List;

public class Piece {

	public interface PieceObserver {
		void moved(Piece piece);
		void taken(Piece piece);
	}
	
	private int id;
	private PlayerColor color;
	private Location location;
	private List<PieceObserver> observers = new ArrayList<PieceObserver>();
	
	public Piece(int id, PlayerColor color, Location initialLocation) {
		super();
		this.id = id;
		this.color = color;
		location = initialLocation;
	}

	public int getId() {
		return id;
	}
	
	public PlayerColor getColor() {
		return color;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void addListener(PieceObserver observer) {
		if (observer != null) {
			observers.add(observer);
		}
	}
	
	public void move(Location newLocation) {
		location = newLocation;
		observers.forEach(obs -> obs.moved(this));
	}

	public void take() {
		observers.forEach(obs -> obs.taken(this));
	}
}
