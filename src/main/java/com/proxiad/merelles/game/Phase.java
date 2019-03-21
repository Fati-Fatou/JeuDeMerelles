package com.proxiad.merelles.game;

import com.proxiad.merelles.protocol.Parser;
import com.proxiad.merelles.protocol.ParsingException;

public abstract class Phase {

	public void parseAndRunCommand(String commandText, Board board, PlayerData player) throws ParsingException, InvalidCommandException {
		Command command = parser().parseCommand(commandText, board);
		command.run(board, player);		
	}
	
	protected abstract Parser<? extends Command> parser(); 
}
