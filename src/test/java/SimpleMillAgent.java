import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Mill;
import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.game.PutCommand;

public class SimpleMillAgent extends ParsingAgent {

	public static class Detector {
		private List<Location> locations;
		private List<Piece> pieces;

		public Detector(List<Location> locations, List<Piece> pieces) {
			this.locations = locations;
			this.pieces = pieces;
		}
		
		public Mill detect() {
			Map<Integer, Piece> constituents = extractContents();
			
			if (constituents == null) {
				return null;
			}
			
			return new Mill(constituents.values());
		}

		private Map<Integer, Piece> extractContents() {
			Optional<PlayerColor> color = Optional.empty();
			Map<Integer, Piece> constituents = new HashMap<>();
			
			for (int i = 0; i < locations.size(); ++i) {
				Optional<Piece> piece = findByLocation(locations.get(i));
				
				if (!piece.isPresent()) {
					// empty slot
					return null;
				}
				
				Piece actualPiece = piece.get();
				
				if (color.isPresent()) {
					if (actualPiece.getColor() != color.get()) {
						// wrong color
						return null;
					}
				}
				else {
					// first slot: save the color of the mill
					color = Optional.of(actualPiece.getColor());
				}
				constituents.put(actualPiece.getId(), actualPiece);
			}
			return constituents;
		}
		
		public Optional<Piece> findByLocation(Location location) {
			for(Piece piece : pieces) {
				if (location.equals(piece.getLocation())) {
					return Optional.of(piece);
				}
			}
			return Optional.empty();
		}
	}

	public static class MoveCommandScore {
		private MoveCommand move;
		private int score;

		public MoveCommandScore(MoveCommand move, int score) {
			super();
			this.move = move;
			this.score = score;
		}

		public MoveCommand getMove() {
			return move;
		}

		public int getScore() {
			return score;
		}	

		public static Optional<MoveCommand> toCommand(MoveCommandScore score) {
			return Optional.of(score.getMove());
		}
	}

	@FunctionalInterface
	public interface MoveScorer {
		MoveCommandScore score(MoveCommand move);
	}

	private final static Comparator<MoveCommandScore> moveComparator = (score1, score2) ->
	Integer.compare(score1.getScore(), score2.getScore());

	private Random random = new Random();

	@Override
	protected MoveCommand playMove(GameDesc game) {
		if (game.getSuggestedMoves().isEmpty()) {
			return null;
		}
		MoveScorer scorer = scorerForMove(game);

		return game.getSuggestedMoves().stream()
				.map(scorer::score)
				.max(moveComparator)
				.flatMap(MoveCommandScore::toCommand).orElse(null);
	}

	private static MoveScorer scorerForMove(GameDesc game) {
		List<Piece> pieces = game.getPieces();

		return command -> {

			List<Detector> millsDetectors = new ArrayList<>();

			// Radial mill
			List<Location> locations1 = new ArrayList<>();

			if ((command.getTargetLocation().getDirection() % 2) == 1) {
				for (int radius = 0; radius < 3; ++radius) {

					locations1.add(new Location(command.getTargetLocation().getDirection(), radius));
				}
				millsDetectors.add(new Detector(locations1, pieces));
			}

			// Transverse mill
			for (int direction = 0; direction < 8; direction += 2) {
				List<Location> locations2 = new ArrayList<>();

				for (int d = direction; d < direction + 3; ++d) {
					locations2.add(new Location(d, command.getTargetLocation().getRadius()));
				}
				millsDetectors.add(new Detector(locations2, pieces));
			}
			
			
			Board board = new Board();

			for (Piece piece : game.getPieces()) {
				if (piece.getId() == command.getMovedPiece().getId()) {
					board.putPiece(command.getTargetLocation(), piece.getColor());
				}
				else {
					board.putPiece(piece.getLocation(), piece.getColor());
				}
			}
			List<Mill> mills = board.findMills();
			
			for (Detector detector : millsDetectors) {
				Mill mill = detector.detect();
				if (mill != null) {
					mills.add(mill);
				}
			}
			
			int score = mills.size();
			return new MoveCommandScore(command, score);		
		};
	}

	@Override
	protected PutCommand playPut(GameDesc game) {
		return game.getSuggestedPuts().get(random.nextInt(game.getSuggestedPuts().size()));
	}		
}
