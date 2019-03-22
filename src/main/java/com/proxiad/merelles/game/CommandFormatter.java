package com.proxiad.merelles.game;

public interface CommandFormatter {
	
	String formatPut(PutCommand command);
	String formatMove(MoveCommand command);

}
