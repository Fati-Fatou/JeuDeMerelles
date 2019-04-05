package com.proxiad.merelles.game;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.proxiad.merelles.protocol.Parser;
import com.proxiad.merelles.protocol.ParserMoveCommand;

public class MovementPhase extends Phase {

	private PlayerData player;

	public MovementPhase(PlayerData player) {
		this.player = player;
	}

	@Override
	protected Parser<? extends Command> parser() {
		return new ParserMoveCommand();
	}

	@Override
	public Stream<String> suggest(Board board, CommandFormatter formatter) {
		return suggestedMoves(board).stream()
				.map(formatter::formatMove);
	}

	protected List<MoveCommand> suggestedMoves(Board board) {
		return board.pieces()
				.filter(this::isPieceMine)
				.flatMap(piece ->
				piece
				.getLocation()
				.getAdjacentLocations()
				.stream()
				.filter(location -> board.isLocationFree(location))
				.map(location -> new MoveCommand(piece, location)))
				.collect(Collectors.toList());
	}
	
	protected boolean isPieceMine(Piece piece) {
		return piece.getColor() == player.getColor();
	}
}