package com.proxiad.merelles.view;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.proxiad.merelles.game.PlayerColor;

public class SpriteManager {
	
	private GraphicEntityModule entityModule;
	
	public SpriteManager(GraphicEntityModule entityModule) {
		this.entityModule = entityModule;
	}
	
	public void createBackground() {
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
	

	public EntitySprite createPieceSprite(PlayerColor color) {
		Sprite sprite = entityModule.createSprite()
				.setImage(color == PlayerColor.BLACK ? "black.png" : "white.png")
				.setBaseWidth(48)
				.setBaseHeight(48)
				.setAnchor(0.5)
				.setZIndex(0);
		EntitySprite entity = new EntitySprite(entityModule, sprite);
		entity.setState(0, 0, SpriteVisibility.HIDDEN, 0.0);
		return entity;
	}
}
