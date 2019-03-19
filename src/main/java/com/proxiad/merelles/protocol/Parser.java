package com.proxiad.merelles.protocol;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.Location;

public class Parser {

	public Command parse(String commandText, Board board) throws ParsingException {
		if (commandText == null) {
			throw new ParsingException();
		}
		String[] tokens = commandText.split(" ");
		try {
			int pieceId = Integer.parseInt(tokens[0]);
			int direction = Integer.parseInt(tokens[1]);
			int radius = Integer.parseInt(tokens[2]);
			Location targetLocation = new Location(direction, radius);
			return new Command(board.findPieceById(pieceId), targetLocation);
		} catch(NumberFormatException exc) {
			throw new ParsingException();
		}
	}
}
