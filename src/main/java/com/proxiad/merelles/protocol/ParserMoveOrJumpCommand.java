package com.proxiad.merelles.protocol;

import java.util.Collection;

import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.MoveOrJumpCommand;
import com.proxiad.merelles.game.Piece;

public class ParserMoveOrJumpCommand extends ParserMoveCommand {

	@Override
	protected MoveCommand makeCommand(String message, Location targetLocation,
			Collection<Piece> removePieces, Piece piece) {
		return new MoveOrJumpCommand(piece, targetLocation, removePieces, message);
	}
}
