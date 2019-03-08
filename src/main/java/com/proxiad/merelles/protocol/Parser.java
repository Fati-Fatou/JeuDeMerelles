package com.proxiad.merelles.protocol;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.UnknownPieceException;

public class Parser {

	public Command parse(String commandText, Board board) throws ParsingException {
		if (commandText == null) {
			throw new ParsingException();
		}
		
		String[] tokens = commandText.split(" ");
		try {
			int pieceId = Integer.parseInt(tokens[0]);
			return new Command(board.findPieceById(pieceId));
		} catch(NumberFormatException | UnknownPieceException exc) {
			throw new ParsingException();
		}
	}
}
