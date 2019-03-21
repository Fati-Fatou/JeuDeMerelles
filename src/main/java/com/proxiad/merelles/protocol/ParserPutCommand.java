package com.proxiad.merelles.protocol;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PutCommand;

public class ParserPutCommand extends Parser<PutCommand> {
	
	public ParserPutCommand() {
		super("PUT", 0);
	}
	
	@Override
	protected PutCommand parseCommandArguments(Board board, String message, String[] tokens,
			Location targetLocation, Piece removePiece) throws ParsingException {
		
		return new PutCommand(targetLocation, removePiece, message);
	}
}
