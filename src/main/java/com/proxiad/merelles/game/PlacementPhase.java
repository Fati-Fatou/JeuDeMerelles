package com.proxiad.merelles.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.proxiad.merelles.protocol.Parser;
import com.proxiad.merelles.protocol.ParserPutCommand;

public class PlacementPhase extends Phase {

	@Override
	protected Parser<? extends Command> parser() {
		return new ParserPutCommand();
	}	

	@Override
	public Stream<String> suggest(Board board, CommandFormatter formatter) {
		return suggestedPuts(board).stream()
				.map(formatter::formatPut);
	}

	private List<PutCommand> suggestedPuts(Board board) {
		List<PutCommand> commands = new ArrayList<>(30);

		// TODO improve perfs
		for (int direction = 0; direction < 8; ++direction) {
			for (int radius = 0; radius < 3; ++radius) {
				Location candidate = new Location(direction, radius);
				if (board.pieces().allMatch(piece -> !piece.getLocation().equals(candidate))) {
					commands.add(new PutCommand(candidate, null, null));
				}
			}
		}
		return commands;
	}
}