package com.proxiad.merelles.protocol;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.Piece;

public class ParserMoveCommand extends Parser<MoveCommand> {

	public ParserMoveCommand() {
		super("MOVE", 1);
	}
	
	protected MoveCommand parseCommandArguments(Board board, String message, String[] tokens,
			Location targetLocation, Piece removePiece) throws ParsingException {
		
		int pieceId = parseInt(tokens, 1, "PIECE_ID");
		Piece piece = board.findPieceById(pieceId);
		if (piece == null) {
			throw new ParsingException(formatUnknownPiece(pieceId));
		}
		
		return new MoveCommand(piece, targetLocation, removePiece, message);
	}
}
