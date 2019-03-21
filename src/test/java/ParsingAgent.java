import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;
import com.proxiad.merelles.game.PutCommand;

public abstract class ParsingAgent {

	private static final Pattern infoParser =
			Pattern.compile("(BLACK|WHITE) ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)");

	private static final Pattern piecesParser = 
			Pattern.compile("([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)");

	private static final Pattern putCommandParser = 
			Pattern.compile("PUT ([0-9]+) ([0-9]+) ([0-9]+)");

	private static final Pattern moveCommandParser = 
			Pattern.compile("MOVE ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)");

	private static PlayerColor parseColor(String text) {
		return "BLACK".equals(text) ? PlayerColor.BLACK : PlayerColor.WHITE;
	}

	public void mainLoop(Scanner scanner, PrintStream out) {
		while (true) {
			GameDesc game = parseInfos(scanner);
			MoveCommand moveCommand = playMove(game);
			if (moveCommand != null) {
				sendMoveCommand(moveCommand, out);
			}
			else {
				PutCommand putCommand = playPut(game);
				sendPutCommand(putCommand, out);
			}
		}		
	}

	protected GameDesc parseInfos(Scanner scanner) {
		String generalInfo = scanner.nextLine();
		Matcher infoMatcher = infoParser.matcher(generalInfo);
		infoMatcher.find();

		PlayerColor myColor = parseColor(infoMatcher.group(1));
		int turnsLeft = Integer.parseInt(infoMatcher.group(2));
		int myPieces = Integer.parseInt(infoMatcher.group(3));
		int opponentsPieces = Integer.parseInt(infoMatcher.group(4));
		int myStock = Integer.parseInt(infoMatcher.group(5));
		int opponentsStock = Integer.parseInt(infoMatcher.group(6));

		List<Piece> pieces = new ArrayList<>();

		int nbPieces = Integer.parseInt(scanner.nextLine());
		for(int i = 0; i < nbPieces; ++i) {
			String pieceLine = scanner.nextLine();
			Matcher matcher = piecesParser.matcher(pieceLine);
			matcher.find();

			int id = Integer.parseInt(matcher.group(1));
			PlayerColor color = Integer.parseInt(matcher.group(2)) == 0 ? myColor : (myColor == PlayerColor.BLACK ? PlayerColor.WHITE : PlayerColor.BLACK);
			int direction = Integer.parseInt(matcher.group(3));
			int radius = Integer.parseInt(matcher.group(4));

			pieces.add(new Piece(id, color, new Location(direction, radius)));
		}

		List<PutCommand> puts= new ArrayList<>();
		List<MoveCommand> moves = new ArrayList<>();

		int nbMoves = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < nbMoves; ++i) {
			String moveLine = scanner.nextLine();
			if (moveLine.startsWith("MOVE")) {
				Matcher matcher = moveCommandParser.matcher(moveLine);
				matcher.find();

				int id = Integer.parseInt(matcher.group(1));
				int direction = Integer.parseInt(matcher.group(2));
				int radius = Integer.parseInt(matcher.group(3));
				int removePieceId = Integer.parseInt(matcher.group(4));
				Predicate<Piece> isThisPiece = p -> p.getId() == id; 
				Piece piece =
						pieces.stream()
						.filter(isThisPiece)
						.findAny()
						.orElse(new Piece(id, myColor, new Location(0, 0)));
				Predicate<Piece> isThisRemovePiece = p -> p.getId() == removePieceId; 
				Piece removePiece =
						pieces.stream()
						.filter(isThisRemovePiece)
						.findAny()
						.orElse(null);
				moves.add(new MoveCommand(piece, new Location(direction, radius), removePiece, null));
			}
			else {
				// PUT
				Matcher matcher = putCommandParser.matcher(moveLine);
				matcher.find();

				int direction = Integer.parseInt(matcher.group(1));
				int radius = Integer.parseInt(matcher.group(2));
				int removePieceId = Integer.parseInt(matcher.group(3));
				Predicate<Piece> isThisRemovePiece = p -> p.getId() == removePieceId; 
				Piece removePiece =
						pieces.stream()
						.filter(isThisRemovePiece)
						.findAny()
						.orElse(null);
				puts.add(new PutCommand(new Location(direction, radius), removePiece, null));
			}
		}

		GameDesc.PlayerDesc me = new GameDesc.PlayerDesc(myColor, myPieces, myStock);
		GameDesc.PlayerDesc opponent = new GameDesc.PlayerDesc(
				myColor == PlayerColor.BLACK ? PlayerColor.WHITE : PlayerColor.BLACK,
						opponentsPieces, opponentsStock);
		GameDesc game = new GameDesc(pieces, puts, moves, turnsLeft, me, opponent);
		return game;
	}

	protected abstract PutCommand playPut(GameDesc game);

	protected abstract MoveCommand playMove(GameDesc game);

	protected void sendMoveCommand(MoveCommand command, PrintStream out) {
		Location location = command.getTargetLocation();
		String output = String.format(
				"MOVE %d %d %d 0", 
				command.getMovedPiece().getId(), 
				location.getDirection(), 
				location.getRadius());
		out.println(output);
	}

	protected void sendPutCommand(PutCommand command, PrintStream out) {
		Location location = command.getTargetLocation();
		String output = String.format(
				"PUT %d %d 0", 
				location.getDirection(), 
				location.getRadius());
		out.println(output);
	}
}
