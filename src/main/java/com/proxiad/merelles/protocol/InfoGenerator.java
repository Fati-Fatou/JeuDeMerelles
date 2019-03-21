package com.proxiad.merelles.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.codingame.game.Player;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.game.PutCommand;

public class InfoGenerator {

	public Stream<String> gameInfoForPlayer(Board board, Player player) {
		List<String> infos = new ArrayList<String>(30);

		// General info
		infos.add(infoLine(board, player));
		
		// Pieces on the board
		List<String> piecesInfos =
				board.pieces()
				.map(toPieceString(player))
				.collect(Collectors.toList());

		infos.add(Integer.toString(piecesInfos.size()));
		infos.addAll(piecesInfos);

		// Suggested moves
		List<String> movesInfo;
		
		// TODO characterize
		boolean setupPhase = true;
		if (setupPhase) {
			movesInfo =
					suggestedPuts(board, player).stream()
					.map(this::toPutCommandString)
					.collect(Collectors.toList());
		}
		else {
			movesInfo =
				suggestedMoves(board, player).stream()
				.map(this::toMoveCommandString)
				.collect(Collectors.toList());
		}
		
		infos.add(Integer.toString(movesInfo.size()));
		infos.addAll(movesInfo);
		
		return infos.stream();
	}
	
	public String infoLine(Board board, Player player) {
		int myPieces = player.getData().getPiecesOnBoard();
		int opponentsPieces = player.getData().getOpponent().getPiecesOnBoard();
		int myStock = player.getData().getPiecesInStock();
		int opponentsStock = player.getData().getOpponent().getPiecesInStock();
		
		return String.format("%s %d %d %d %d %d",
					player.getData().getColor() == PlayerColor.BLACK ? "BLACK" : "WHITE",
					board.getTurnsLeft(),
					myPieces, opponentsPieces, myStock, opponentsStock);
	}
	
	public Function<Piece, String> toPieceString(Player player) {
		return piece ->
			String.format(
				"%d %d %d %d", 
				piece.getId(),
				piece.getColor() == player.getData().getColor() ? 0 : 1,
				piece.getLocation().getDirection(), 
				piece.getLocation().getRadius());
	}

	public List<PutCommand> suggestedPuts(Board board, Player player) {
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

	public List<MoveCommand> suggestedMoves(Board board, Player player) {
		List<MoveCommand> commands = new ArrayList<>(30);
		
		// TODO implement
		return commands;
	}

	public String toPutCommandString(PutCommand command) {
		Location location = command.getTargetLocation();
		// Suggest to move this piece.
		// No suggestion for the piece to remove in case of mill, hence 0 as last argument.
		return String.format(
				"PUT %d %d 0", 
				location.getDirection(), 
				location.getRadius());
	}

	public String toMoveCommandString(MoveCommand command) {
		Location location = command.getTargetLocation();
		// Suggest to move this piece.
		// No suggestion for the piece to remove in case of mill, hence 0 as last argument.
		return String.format(
				"MOVE %d %d %d 0", 
				command.getMovedPiece().getId(), 
				location.getDirection(), 
				location.getRadius());
	}
}
