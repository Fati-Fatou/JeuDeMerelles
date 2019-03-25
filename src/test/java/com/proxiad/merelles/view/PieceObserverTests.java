package com.proxiad.merelles.view;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;

public class PieceObserverTests {

	private Location initialLocation = new Location(2, 1);
	private Location newLocation = new Location(1, 1);
	private static final int INITIAL_X = 964;
	private static final int INITIAL_Y = 236;
	private static final int NEW_X = 600;
	private static final int NEW_Y = 236;
	private Piece piece;
	private PieceView view;
	
	@Mock
	private EntitySprite sprite;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		piece = new Piece(2, PlayerColor.BLACK, initialLocation);
		view = new PieceView(piece, sprite);		
	}

	@Test
	public void testNoActivityNoUpdatesOnView() {
		view.updateView();
		
		verify(sprite).setState(INITIAL_X, INITIAL_Y, SpriteVisibility.VISIBLE, 1.0);

		verifyNoMoreInteractions(sprite);
	}

	@Test
	public void testMovedPieceUpdatesView() {
		view.updateView();
		
		piece.move(newLocation);
		assertEquals(newLocation, piece.getLocation());
		
		view.updateView();
		
		// before move
		verify(sprite).setState(INITIAL_X, INITIAL_Y, SpriteVisibility.VISIBLE, 1.0);

		// after move
		verify(sprite).setState(INITIAL_X, INITIAL_Y, SpriteVisibility.VISIBLE, 0.0);
		verify(sprite).setState(NEW_X, NEW_Y, SpriteVisibility.VISIBLE, 1.0);
		
		verifyNoMoreInteractions(sprite);
	}

	@Test
	public void testTakenPieceUpdatesView() {
		view.updateView();
		
		piece.take();
		
		view.updateView();

		// before move
		verify(sprite).setState(INITIAL_X, INITIAL_Y, SpriteVisibility.VISIBLE, 1.0);

		// after move
		verify(sprite).setState(INITIAL_X, INITIAL_Y, SpriteVisibility.HIDDEN, 1.0);
		verifyNoMoreInteractions(sprite);
	}
}
