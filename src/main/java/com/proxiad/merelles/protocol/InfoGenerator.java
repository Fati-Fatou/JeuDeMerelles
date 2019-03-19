package com.proxiad.merelles.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.codingame.game.Player;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;

public class InfoGenerator {

	public Stream<String> gameInfoForPlayer(Board board, Player player) {
		List<String> infos = new ArrayList<String>(30);

		// General info
		infos.add(infoLine(board, player));
		
		// Pieces on the board
		List<String> piecesInfos =
				board.pieces()
				.map(this::toPieceString)
				.collect(Collectors.toList());

		infos.add(Integer.toString(piecesInfos.size()));
		infos.addAll(piecesInfos);

		// Suggested moves
		List<String> movesInfo =
				suggestedMoves(board, player).stream()
				.map(this::toCommandString)
				.collect(Collectors.toList());
		
		infos.add(Integer.toString(movesInfo.size()));
		infos.addAll(movesInfo);
		
		return infos.stream();
	}
	
	public String infoLine(Board board, Player player) {
		return "SOME INFO";
	}
	
	public String toPieceString(Piece piece) {
		return String.format(
				"%d %s %d %d", 
				piece.getId(),
				piece.getColor() == PlayerColor.BLACK ? "BLACK" : "WHITE",
				piece.getLocation().getDirection(), 
				piece .getLocation().getRadius());
	}

	public List<Command> suggestedMoves(Board board, Player player) {
		List<Command> commands = new ArrayList<Command>(30);
		
		// TODO improve perfs
		for (int direction = 0; direction < 8; ++direction) {
			for (int radius = 0; radius < 3; ++radius) {
				Location candidate = new Location(direction, radius);
				if (board.pieces().allMatch(piece -> !piece.getLocation().equals(candidate))) {
					commands.add(new Command(new Piece(0, player.getColor(), candidate), candidate));
				}
			}
		}
		return commands;
	}
	
	public String toCommandString(Command command) {
		Location location = command.getTargetLocation();
		return String.format(
				"%d %d %d", 
				command.getMovedPiece().getId(), 
				location.getDirection(), 
				location.getRadius());
	}
}
