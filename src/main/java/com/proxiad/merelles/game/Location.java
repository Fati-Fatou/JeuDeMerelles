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

}
