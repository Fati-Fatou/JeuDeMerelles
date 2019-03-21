package com.proxiad.merelles.game;

import com.proxiad.merelles.protocol.Parser;
import com.proxiad.merelles.protocol.ParserPutCommand;

public class PlacementPhase extends Phase {

	@Override
	protected Parser<? extends Command> parser() {
		return new ParserPutCommand();
	}
}