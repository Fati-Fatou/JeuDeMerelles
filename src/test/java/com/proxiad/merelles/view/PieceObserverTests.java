package com.proxiad.merelles.view;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;

public class PieceObserverTests {

	private Location initialLocation = new Location(2, 1);
	private Location newLocation = new Location(1, 1);
	private Piece piece;
	private PieceView view;
	private PieceView viewSpy;
	private GraphicEntityModule entityModule;
	
	@Before
	public void setUp() throws Exception {
		entityModule = mock(GraphicEntityModule.class);
		
		piece = new Piece(2, PlayerColor.BLACK, initialLocation);
		view = new PieceView(entityModule, piece);		
		viewSpy = spy(view);
		doNothing().when(viewSpy).createSprite();
		doNothing().when(viewSpy).hide();
		doNothing().when(viewSpy).showAt(newLocation);
	}

	@Test
	public void testNoActivityNoUpdatesOnView() {
		viewSpy.updateView();
		verify(viewSpy).updateView();
		verify(viewSpy).createSprite();
		verify(viewSpy).showAt(initialLocation);
		verifyNoMoreInteractions(viewSpy);
	}

	@Test
	@Ignore
	public void testMovedPieceUpdatesView() {
		viewSpy.updateView();
		piece.move(newLocation);
		assertEquals(newLocation, piece.getLocation());
		
		viewSpy.updateView();
		verify(viewSpy, Mockito.times(2)).updateView();
		verify(viewSpy, Mockito.times(2)).createSprite();
		verify(viewSpy).showAt(initialLocation);
		verify(viewSpy).showAt(newLocation);
		
		verifyNoMoreInteractions(viewSpy);
	}

	@Test
	@Ignore
	public void testTakenPieceUpdatesView() {
		viewSpy.updateView();
		piece.take();
		viewSpy.taken(piece);
		viewSpy.updateView();
		verify(viewSpy).showAt(initialLocation);
		verify(viewSpy).hide();
		verifyNoMoreInteractions(viewSpy);
	}
}
