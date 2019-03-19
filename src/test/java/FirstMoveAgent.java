import com.proxiad.merelles.game.Command;

public class FirstMoveAgent extends ParsingAgent {

	@Override
	protected Command play(GameDesc game) {
		return game.getSuggestedMoves().get(0);
	}
}
