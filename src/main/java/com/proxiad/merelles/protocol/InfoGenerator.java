package com.proxiad.merelles.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.codingame.game.Player;
import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.CommandFormatter;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.NoPossibleMovesException;
import com.proxiad.merelles.game.Phase;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.game.PutCommand;

public class InfoGenerator implements CommandFormatter {

	public Stream<String> gameInfoForPlayer(Board board, Player player, int turnsLeft) throws NoPossibleMovesException {
		List<String> infos = new ArrayList<>(30);

		// General info
		infos.add(infoLine(board, player, turnsLeft));
		
		// Pieces on the board
		List<String> piecesInfos =
				board.pieces()
				.map(toPieceString(player))
				.collect(Collectors.toList());

		infos.add(Integer.toString(piecesInfos.size()));
		infos.addAll(piecesInfos);

		// Suggested moves	
		Phase phase = player.getData().getPhase();
		List<String> movesInfo = phase.suggest(board, this).collect(Collectors.toList());
		
		if (movesInfo.isEmpty()) {
			throw new NoPossibleMovesException();
		}
		
		infos.add(Integer.toString(movesInfo.size()));
		infos.addAll(movesInfo);
		
		return infos.stream();
	}
	
	public String infoLine(Board board, Player player, int turnsLeft) {
		int myPieces = player.getData().getPiecesOnBoard();
		int opponentsPieces = player.getData().getOpponent().getPiecesOnBoard();
		int myStock = player.getData().getPiecesInStock();
		int opponentsStock = player.getData().getOpponent().getPiecesInStock();
		
		return String.format("%s %d %d %d %d %d",
					player.getData().getColor() == PlayerColor.BLACK ? "BLACK" : "WHITE",
					turnsLeft,
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

	@Override
	public String formatPut(PutCommand command) {
		Location location = command.getTargetLocation();
		// Suggest to move this piece.
		// No suggestion for the piece to remove in case of mill, hence 0 as last argument.
		return String.format(
				"PUT %d %d 0 0", 
				location.getDirection(), 
				location.getRadius());
	}

	@Override
	public String formatMove(MoveCommand command) {
		Location location = command.getTargetLocation();
		// Suggest to move this piece.
		// No suggestion for the piece to remove in case of mill, hence 0 as last argument.
		return String.format(
				"MOVE %d %d %d 0 0", 
				command.getMovedPiece().getId(), 
				location.getDirection(), 
				location.getRadius());
	}
}
