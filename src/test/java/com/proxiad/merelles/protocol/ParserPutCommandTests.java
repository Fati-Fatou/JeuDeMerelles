package com.proxiad.merelles.protocol;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.PutCommand;

public class ParserPutCommandTests {

	@Mock
	private Board board;

	private ParserPutCommand parser = new ParserPutCommand();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testPutTargetLocation() throws ParsingException {
		String textFromPlayer = "PUT 3 2 0 ; Foobar";
		when(board.findPieceById(0)).thenReturn(null);
		PutCommand command = parser.parseCommand(textFromPlayer, board);
		Location expectedLocation = new Location(3, 2);
		assertEquals(expectedLocation, command.getTargetLocation());
	}
}
