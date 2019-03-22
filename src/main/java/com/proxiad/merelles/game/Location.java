package com.proxiad.merelles.game;

public class Location {
	
	private int direction;
	private int radius;
	
	public Location(int direction, int radius) {
		this.direction = direction;
		this.radius = radius;
	}

	public int getDirection() {
		return direction;
	}

	public int getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(direction);
		sb.append(' ');
		sb.append(radius);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + direction;
		result = prime * result + radius;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (direction != other.direction)
			return false;
		if (radius != other.radius)
			return false;
		return true;
	}

	public boolean isAdjacent(Location location) {
		// Same odd direction (1, 3...): radius difference must be 1
		if (location.getDirection() == getDirection() && (getDirection() % 2 == 1)) {
			return isNextTo(location.getRadius(), getRadius());
		}
		
		// Same radius: direction difference must be 1, modulo 8
		if (location.getRadius() == getRadius()) {
			return isNextToModulo8(location.getDirection(), getDirection());
		}
		
		return false;
	}
	
	private static boolean isNextTo(int a, int b) {
		return a == b + 1 || a == b - 1;
	}

	private static boolean isNextToModulo8(int a, int b) {
		return (a == (b + 1) % 8) || (a == (b - 1 + 8) % 8);
	}
}
