import com.proxiad.merelles.game.MoveCommand;

public class FirstMoveAgent extends ParsingAgent {

	@Override
	protected MoveCommand play(GameDesc game) {
		return game.getSuggestedMoves().get(0);
	}
}
