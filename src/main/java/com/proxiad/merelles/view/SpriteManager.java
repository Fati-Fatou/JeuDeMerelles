package com.proxiad.merelles.view;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.proxiad.merelles.game.PlayerColor;

public class SpriteManager {

	public static final int PIECE_SIZE = 48;
	
	private GraphicEntityModule entityModule;

	public SpriteManager(GraphicEntityModule entityModule) {
		this.entityModule = entityModule;
		createLogo();
	}

	// Uses for the demo.
	public void createLogo() {
		entityModule.createSprite()
		.setBaseWidth(1920)
		.setBaseHeight(1080)
		.setX(0)
		.setY(0)
		.setImage("logo.png")
		.setVisible(false);
	}
	
	public void createBackground() {
		/*entityModule.createRectangle()
		.setFillColor(0xFFFFCC)
		.setWidth(1920)
		.setHeight(1080)
		.setX(0)
		.setY(0)
		.setScale(1)
		.setZIndex(-2);
		entityModule.createSprite()
		.setImage("board.png")
		.setBaseWidth(1080)
		.setBaseHeight(1080)
		.setX(420)
		.setY(0)
		.setScale(1)
		.setAnchor(0)
		.setZIndex(-1);*/
		entityModule.createSprite()
		.setImage("background.png")
		.setBaseWidth(1920)
		.setBaseHeight(1080)
		.setX(0)
		.setY(0)
		.setScale(1)
		.setAnchor(0)
		.setZIndex(-1);
	}

	public EntitySprite createPieceSprite(PlayerColor color) {
		Sprite sprite = entityModule.createSprite()
				.setImage(color == PlayerColor.BLACK ? "black.png" : "white.png")
				.setBaseWidth(PIECE_SIZE)
				.setBaseHeight(PIECE_SIZE)
				.setAnchor(0.5)
				.setZIndex(0);
		EntitySprite entity = new EntitySprite(entityModule, sprite);
		entity.setState(0, 0, SpriteVisibility.HIDDEN, 0.0);
		return entity;
	}
}
