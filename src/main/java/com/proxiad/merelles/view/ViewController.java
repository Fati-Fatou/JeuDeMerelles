package com.proxiad.merelles.view;

import java.util.HashMap;
import java.util.Map;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Board.BoardObserver;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.Scores;

public class ViewController implements BoardObserver {

	private SpriteManager spriteManager;
	private Map<Integer, PieceView> pieces = new HashMap<>();
	private Board board;
	private PlayerView blackPlayer;
	private PlayerView whitePlayer;
	
	public ViewController(GraphicEntityModule entityModule, Board board, PlayerView blackPlayer, PlayerView whitePlayer) {
		spriteManager = new SpriteManager(entityModule);
		this.board = board;
		this.board.addListener(this);
		this.blackPlayer = blackPlayer;
		this.whitePlayer = whitePlayer;
		spriteManager.createBackground();
	}

	public void update(Scores scores) {
		pieces.values().forEach(PieceView::updateView);
		blackPlayer.updateView(scores.scoreOfBlackPlayer());
		whitePlayer.updateView(scores.scoreOfWhitePlayer());
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