package com.proxiad.merelles.game;

import com.proxiad.merelles.protocol.Parser;
import com.proxiad.merelles.protocol.ParserMoveCommand;

public class MovementPhase extends Phase {

	@Override
	protected Parser<? extends Command> parser() {
		return new ParserMoveCommand();
	}
}