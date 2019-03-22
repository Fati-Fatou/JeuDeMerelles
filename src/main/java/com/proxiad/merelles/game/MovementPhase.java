package com.proxiad.merelles.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.codingame.game.Player;
import com.proxiad.merelles.protocol.Parser;
import com.proxiad.merelles.protocol.ParserMoveCommand;

public class MovementPhase extends Phase {

	@Override
	protected Parser<? extends Command> parser() {
		return new ParserMoveCommand();
	}
	
	@Override
	public Stream<String> suggest(Board board, CommandFormatter formatter) {
		return suggestedMoves(board).stream()
				.map(formatter::formatMove);
	}
	
	public List<MoveCommand> suggestedMoves(Board board) {
		List<MoveCommand> commands = new ArrayList<>(30);
		
		// TODO implement
		return commands;
	}
}