package com.proxiad.merelles.view;

import java.util.HashMap;
import java.util.Map;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.proxiad.merelles.game.Piece;

public class ViewController {

	private GraphicEntityModule entityModule;
	private Map<Integer, PieceView> pieces = new HashMap<>();
	
	public ViewController(GraphicEntityModule entityModule) {
		this.entityModule = entityModule;
		
		createBackground();
	}

	public void update() {
		pieces.values().forEach(PieceView::updateView);
	}
	
	private void createBackground() {
		entityModule.createSprite()
		.setImage("board.png")
		.setBaseWidth(1200)
		.setBaseHeight(1200)
		.setX(0)
		.setY(0)
		.setScale(1)
		.setAnchor(0)
		.setZIndex(-1);
	}
	
	public void registerPieceView(Piece piece) {
		pieces.put(piece.getId(), new PieceView(entityModule, piece));
	}
}
