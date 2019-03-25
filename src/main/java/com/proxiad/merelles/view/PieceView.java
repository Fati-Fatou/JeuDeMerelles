package com.proxiad.merelles.view;

import java.util.HashMap;
import java.util.Map;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.Piece.PieceObserver;
import com.proxiad.merelles.game.PlayerColor;

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
			return centerX + (radius + 1)* dx;
		}

		int computeY(int radius) {
			return centerY + (radius +1)* dy;
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

	private GraphicEntityModule entityModule;
	private Sprite sprite;
	private int x;
	private int y;
	private boolean isVisible = false;
	private Location newLocation = null;
	private boolean wasTaken = false;
	private Piece piece;

	public PieceView(GraphicEntityModule entityModule, Piece piece) {
		this.entityModule = entityModule;
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
		if (sprite == null) {
			createSprite();
		}

		if (newLocation != null && !wasTaken) {
			showAt(newLocation);
			newLocation = null;
			isVisible = true;
		}
		else if (wasTaken && isVisible) {
			hide();
		}
	}

	public void createSprite() {
		boolean isBlack = piece.getColor() == PlayerColor.BLACK;
		sprite = entityModule.createSprite()
				.setImage(isBlack ? "black.png" : "white.png")
				.setX(x)
				.setY(y)
				.setBaseWidth(48)
				.setBaseHeight(48)
				.setAnchor(0.5)
				.setZIndex(0)
				.setVisible(false);
	}

	public void showAt(Location location) {
		if (isVisible) {
			entityModule.commitEntityState(0, sprite);
		}

		updateCoords(location);
		if (sprite != null) {
			sprite.setVisible(true)
			.setAlpha(1.0)
			.setX(x)
			.setY(y);
		}
		isVisible = true;
		if (sprite != null) {
			entityModule.commitEntityState(1.0, sprite);
		}
	}

	public void hide() {
		isVisible = false;

		if (sprite != null) {
			sprite.setAlpha(0.0)
			.setVisible(false);
			entityModule.commitEntityState(1.0, sprite);
		}
	}
}
