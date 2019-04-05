package com.proxiad.merelles.protocol;

import java.util.Collection;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.Piece;

public class ParserMoveCommand extends Parser<MoveCommand> {

	public ParserMoveCommand() {
		super("MOVE", 1);
	}
	
	@Override
	protected MoveCommand parseCommandArguments(Board board, String message, String[] tokens,
			Location targetLocation, Collection<Piece> removePieces) throws ParsingException {
		
		int pieceId = parseInt(tokens, 1, "PIECE_ID");
		Piece piece = board.findPieceById(pieceId);
		if (piece == null) {
			throw new ParsingException(formatUnknownPiece(pieceId));
		}
		
		return makeCommand(message, targetLocation, removePieces, piece);
	}

	protected MoveCommand makeCommand(String message, Location targetLocation,
			Collection<Piece> removePieces, Piece piece) {
		return new MoveCommand(piece, targetLocation, removePieces, message);
	}
}
