import java.util.Random;

import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.PutCommand;

public class RandomAgent extends ParsingAgent {

	private Random random = new Random();
	
	@Override
	protected MoveCommand playMove(GameDesc game) {
		if (game.getSuggestedMoves().isEmpty()) {
			return null;
		}
		return game.getSuggestedMoves().get(random.nextInt(game.getSuggestedMoves().size()));
	}

	@Override
	protected PutCommand playPut(GameDesc game) {
		return game.getSuggestedPuts().get(random.nextInt(game.getSuggestedPuts().size()));
	}
}
