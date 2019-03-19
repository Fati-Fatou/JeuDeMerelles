import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;
import com.proxiad.merelles.game.PlayerColor;

public abstract class ParsingAgent {

	private static final Pattern infoParser =
			Pattern.compile("(BLACK|WHITE) ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)");

	private static final Pattern piecesParser = 
			Pattern.compile("([0-9]+) ([0-9]+) ([0-9]+) ([0-9]+)");

	private static final Pattern commandParser = 
			Pattern.compile("([0-9]+) ([0-9]+) ([0-9]+)");

	private static PlayerColor parseColor(String text) {
		return "BLACK".equals(text) ? PlayerColor.BLACK : PlayerColor.WHITE;
	}

	public void mainLoop(Scanner scanner, PrintStream out) {
		while (true) {
			GameDesc game = parseInfos(scanner);
			Command command = play(game);
			sendCommand(command, out);
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

		List<Command> moves = new ArrayList<>();

		int nbMoves = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < nbMoves; ++i) {
			String moveLine = scanner.nextLine();
			Matcher matcher = commandParser.matcher(moveLine);
			matcher.find();

			int id = Integer.parseInt(matcher.group(1));
			int direction = Integer.parseInt(matcher.group(2));
			int radius = Integer.parseInt(matcher.group(3));
			Predicate<Piece> isThisPiece = p -> p.getId() == id; 
			Piece piece =
					pieces.stream()
					.filter(isThisPiece)
					.findAny()
					.orElse(new Piece(id, myColor, new Location(0, 0)));
			moves.add(new Command(piece, new Location(direction, radius)));
		}

		GameDesc.PlayerDesc me = new GameDesc.PlayerDesc(myColor, myPieces, myStock);
		GameDesc.PlayerDesc opponent = new GameDesc.PlayerDesc(
				myColor == PlayerColor.BLACK ? PlayerColor.WHITE : PlayerColor.BLACK,
						opponentsPieces, opponentsStock);
		GameDesc game = new GameDesc(pieces, moves, turnsLeft, me, opponent);
		return game;
	}

	protected abstract Command play(GameDesc game);

	protected void sendCommand(Command command, PrintStream out) {
		Location location = command.getTargetLocation();
		String output = String.format(
				"%d %d %d", 
				command.getMovedPiece().getId(), 
				location.getDirection(), 
				location.getRadius());
		out.println(output);
	}
}
