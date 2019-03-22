package com.proxiad.merelles.game;

import java.util.stream.Stream;

import com.proxiad.merelles.protocol.Parser;
import com.proxiad.merelles.protocol.ParsingException;

public abstract class Phase {

	public void parseAndRunCommand(String commandText, Board board, PlayerData player) throws ParsingException, InvalidCommandException {
		Command command = parser().parseCommand(commandText, board);
		command.run(board, player);		
	}
	
	protected abstract Parser<? extends Command> parser(); 
	
	public abstract Stream<String> suggest(Board board, CommandFormatter formatter);
}
