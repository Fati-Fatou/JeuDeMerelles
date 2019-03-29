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
		List<Piece> opponentPieces = opponent.pieces();	
		//THEN
		assertEquals(3, opponentPieces.size());		
	}

	@Test
	public void pieceNotInMill() {
		//GIVEN
		Board board = new Board();
		board.putPiece(new Location(7,2), PlayerColor.BLACK);
		board.putPiece(new Location(7,1), PlayerColor.BLACK);
		board.putPiece(new Location(7,0), PlayerColor.BLACK);
		board.putPiece(new Location(6,2), PlayerColor.BLACK);
		board.putPiece(new Location(4,1), PlayerColor.BLACK);
		
		Mill mill = board.findMills().get(0);
		
		List<Piece> pieces = board.pieces().collect(Collectors.toList());	
		
		//WHEN
		pieces.removeAll(mill.pieces());
		
		//THEN
		assertEquals(2, pieces.size());
	}
}
