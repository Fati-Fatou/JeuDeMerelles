package com.proxiad.merelles.view;

import java.util.HashMap;
import java.util.Map;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Board.BoardObserver;
import com.proxiad.merelles.game.Piece;

public class ViewController implements BoardObserver {

	private SpriteManager spriteManager;
	private Map<Integer, PieceView> pieces = new HashMap<>();
	private Board board;
	
	public ViewController(GraphicEntityModule entityModule, Board board) {
		spriteManager = new SpriteManager(entityModule);
		this.board = board;
		this.board.addListener(this);
		spriteManager.createBackground();
	}

	public void update() {
		pieces.values().forEach(PieceView::updateView);
	}
	
	public void registerPieceView(Piece piece) {
		pieces.put(piece.getId(), new PieceView(piece, spriteManager.createPieceSprite(piece.getColor())));
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
