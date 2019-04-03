package com.proxiad.merelles.game;

import java.util.ArrayList;
import java.util.List;

public class JumpPhase extends MovementPhase {

	public JumpPhase(PlayerData player) {
		super(player);
	}
	
	@Override
	protected List<MoveCommand> suggestedMoves(Board board) {
		List<MoveCommand> regularMoves = super.suggestedMoves(board);
		
		if (regularMoves.size() > 0) {
			return regularMoves;
		}
		
		// no moves: suggest jumps
		List<MoveCommand> jumps = new ArrayList<>();
		
		for (int direction = 0; direction < 8; ++direction) {
			for (int radius = 0; radius < 3; ++radius) {
				Location target = new Location(direction, radius);
				if (board.isLocationFree(target)) {
					board.pieces()
					.filter(this::isPieceMine)
					.forEach(piece -> jumps.add(new MoveCommand(piece, target)));
				}
			}
		}
		return jumps;
	}
}
