package com.proxiad.merelles.protocol;

import com.proxiad.merelles.game.Command;

public class Parser {

	public Command parse(String commandText) throws ParsingException {
		if (commandText == null) {
			throw new ParsingException();
		}
		
		String[] tokens = commandText.split(" ");
		try {
			int pieceId = Integer.parseInt(tokens[0]);
			return new Command(pieceId);
		} catch(NumberFormatException exc) {
			throw new ParsingException();
		}
	}
}
