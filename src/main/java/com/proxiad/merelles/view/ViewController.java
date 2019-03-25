package com.proxiad.merelles.view;

import java.util.HashMap;
import java.util.Map;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Board.BoardObserver;
import com.proxiad.merelles.game.Piece;

public class ViewController implements BoardObserver {

	private GraphicEntityModule entityModule;
	private Map<Integer, PieceView> pieces = new HashMap<>();
	private Board board;
	
	public ViewController(GraphicEntityModule entityModule, Board board) {
		this.entityModule = entityModule;
		this.board = board;
		this.board.addListener(this);
		createBackground();
	}

	public void update() {
		pieces.values().forEach(PieceView::updateView);
	}
	
	private void createBackground() {
		entityModule.createRectangle()
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
		.setZIndex(-1);
	}
	
	public void registerPieceView(Piece piece) {
		pieces.put(piece.getId(), new PieceView(entityModule, piece));
	}

	@Override
	public void pieceAdded(Piece piece) {
		registerPieceView(piece);		
	}

	@Override
	public void pieceTaken(Piece piece) {
		// TODO Auto-generated method stub
	}
}
