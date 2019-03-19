import java.util.List;

import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;

public class GameDesc {

	public static class PlayerDesc {
		private PlayerColor color;
		private int piecesOnBoard;
		private int piecesInStock;
		
		public PlayerDesc(PlayerColor color, int piecesOnBoard, int piecesInStock) {
			this.color = color;
			this.piecesOnBoard = piecesOnBoard;
			this.piecesInStock = piecesInStock;
		}
		
		public PlayerColor getColor() {
			return color;
		}
		
		public int getPiecesOnBoard() {
			return piecesOnBoard;
		}
		
		public int getPiecesInStock() {
			return piecesInStock;
		}
	}

	private List<Piece> pieces;
	private List<Command> suggestedMoves;
	private int turnsLeft;
	private PlayerDesc me;
	private PlayerDesc opponent;
	
	public GameDesc(List<Piece> pieces, List<Command> suggestedMoves,
			int turnsLeft, PlayerDesc me, PlayerDesc opponent) {
		this.pieces = pieces;
		this.suggestedMoves = suggestedMoves;
		this.turnsLeft = turnsLeft;
		this.me = me;
		this.opponent = opponent;
	}
	
	public List<Piece> getPieces() {
		return pieces;
	}
	
	public List<Command> getSuggestedMoves() {
		return suggestedMoves;
	}
	
	public int getTurnsLeft() {
		return turnsLeft;
	}
	
	public PlayerDesc getMe() {
		return me;
	}

	public PlayerDesc getOpponent() {
		return opponent;
	}
}
