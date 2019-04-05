package com.proxiad.merelles.game;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerData {

	private Board board;
	private PlayerColor color;
	private PlayerData opponent;
	private int piecesOnBoard;
	private int piecesInStock;

	public PlayerData(Board board, PlayerColor color, int numberOfStockPieces) {
		this.board = board;
		this.color = color;
		piecesInStock = numberOfStockPieces;
	}

	public PlayerColor getColor() {
		return color;
	}

	public PlayerData getOpponent() {
		return this.opponent;
	}

	public void setOpponent(PlayerData opponent) {
		this.opponent = opponent;
	}

	public int getPiecesOnBoard() {
		return piecesOnBoard;
	}

	public int getPiecesInStock() {
		return piecesInStock;
	}

	public void updateCountsAfterPut() {
		++piecesOnBoard;
		--piecesInStock;		
	}
	
	public void updateCountsAfterPieceTaken() {
		--piecesOnBoard;
	}

	public Phase getPhase() {
		if (getPiecesInStock() > 0) {
			return new PlacementPhase();
		}
		
		if (getPiecesOnBoard() <= 3) {
			return new JumpPhase(this);
		}
		
		return new MovementPhase(this);
	}

	public List<Piece> pieces() {
		return board.pieces()
				.filter(piece -> color.equals(piece.getColor()))
				.collect(Collectors.toList());
	}
	
	public int score() {
		return getPiecesOnBoard() + getPiecesInStock();
	}
}
