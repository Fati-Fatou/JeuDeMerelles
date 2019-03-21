import com.proxiad.merelles.game.MoveCommand;
import com.proxiad.merelles.game.PutCommand;

public class FirstMoveAgent extends ParsingAgent {

	@Override
	protected MoveCommand playMove(GameDesc game) {
		if (game.getSuggestedMoves().isEmpty()) {
			return null;
		}
		return game.getSuggestedMoves().get(0);
	}

	@Override
	protected PutCommand playPut(GameDesc game) {
		return game.getSuggestedPuts().get(0);
	}
}
