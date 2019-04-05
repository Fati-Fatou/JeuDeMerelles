package com.proxiad.merelles.view;

import java.util.HashMap;
import java.util.Map;

import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.Piece.PieceObserver;

public class PieceView implements PieceObserver {

	static class CoordConverter {
		private int dx;
		private int dy;
		private static final int centerX = 960;
		private static final int centerY = 540;

		CoordConverter(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		int computeX(int radius) {
			return centerX + (radius + 1) * dx;
		}

		int computeY(int radius) {
			return centerY + (radius + 1) * dy;
		}
	}

	private static Map<Integer, CoordConverter> converters;

	static {
		final int step = 164;

		converters = new HashMap<>();
		converters.put(0, new CoordConverter(-step, -step));
		converters.put(1, new CoordConverter(0, -step));
		converters.put(2, new CoordConverter(step, -step));
		converters.put(3, new CoordConverter(step, 0));
		converters.put(4, new CoordConverter(step, step));
		converters.put(5, new CoordConverter(0, step));
		converters.put(6, new CoordConverter(-step, step));
		converters.put(7, new CoordConverter(-step, 0));
	}

	private static CoordConverter converter(int direction) {
		return converters.get(direction);
	}

	private EntitySprite sprite;
	private int x;
	private int y;
	private boolean isVisible = false;
	private Location newLocation = null;
	private boolean wasTaken = false;
	private Piece piece;

	public PieceView(Piece piece, EntitySprite sprite) {
		this.sprite = sprite;
		this.piece = piece;
		this.piece.addListener(this);
		newLocation = piece.getLocation();
	}

	private void updateCoords(Location location) {
		CoordConverter conv = converter(location.getDirection());
		x = conv.computeX(location.getRadius());
		y = conv.computeY(location.getRadius());
	}

	@Override
	public void moved(Piece piece) {
		this.newLocation = piece.getLocation();
	}

	@Override
	public void taken(Piece piece) {
		wasTaken = true;		
	}

	public void updateView() {
		if (newLocation != null && !wasTaken) {
			showAt(newLocation);
			newLocation = null;
			isVisible = true;
		}
		else if (wasTaken && isVisible) {
			hide();
		}
	}

	public void showAt(Location location) {		
		if (isVisible) {
			sprite.setState(x, y, SpriteVisibility.VISIBLE, 0.0);
		}

		updateCoords(location);
		sprite.setState(x, y, SpriteVisibility.VISIBLE, 1.0);
		isVisible = true;
	}

	public void hide() {
		isVisible = false;

		sprite.setState(x, y, SpriteVisibility.HIDDEN, 1.0);
	}
}
