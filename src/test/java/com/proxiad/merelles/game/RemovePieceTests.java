package com.proxiad.merelles.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class RemovePieceTests {
	
	@Test
	public void isOpponentPiece() {
		//GIVEN
		Board board = new Board();
		PlayerData opponent = new PlayerData(board, PlayerColor.BLACK, 3);
		board.putPiece(new Location(0,0), PlayerColor.BLACK);
		board.putPiece(new Location(0,1), PlayerColor.BLACK);
		board.putPiece(new Location(1,2), PlayerColor.BLACK);
		board.putPiece(new Location(7,2), PlayerColor.WHITE);
		board.putPiece(new Location(0,2), PlayerColor.WHITE);
		board.putPiece(new Location(6,2), PlayerColor.WHITE);
		board.putPiece(new Location(5,2), PlayerColor.WHITE);
		//WHEN
		List<Piece> pieces = board.pieces().collect(Collectors.toList());
		List<Piece> opponentPieces = new ArrayList<>();
		for (Piece pieceOpponent : pieces) {
			if (opponent.getColor().equals(pieceOpponent.getColor())) {
				opponentPieces.add(pieceOpponent);
			}		
		}	
		//THEN
		assertEquals(3, opponentPieces.size());		
	}
	

	@Test
	public void pieceNotInMill() {
		//GIVEN
		Board board = new Board();
		PlayerData opponent = new PlayerData(board, PlayerColor.WHITE, 4);
		int id1 = board.putPiece(new Location(7,2), PlayerColor.BLACK);
		int id2 = board.putPiece(new Location(7,1), PlayerColor.BLACK);
		int id3 = board.putPiece(new Location(7,0), PlayerColor.BLACK);
		board.putPiece(new Location(6,2), PlayerColor.BLACK);
		board.putPiece(new Location(4,1), PlayerColor.BLACK);
		
		List<Piece> pieces = board.pieces().collect(Collectors.toList());	
		
		//WHEN
		List<Piece> pieceInMill = new ArrayList<>();
		pieceInMill.add(board.findPieceById(id1));
		pieceInMill.add(board.findPieceById(id2));
		pieceInMill.add(board.findPieceById(id3));
	
		pieces.removeAll(pieceInMill);
		
		//THEN
		assertEquals(2, pieces.size());
		
	}

}
