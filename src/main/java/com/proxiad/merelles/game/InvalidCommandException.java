package com.proxiad.merelles.game;

public class InvalidCommandException extends Exception {

	private static final long serialVersionUID = 1L;

	protected InvalidCommandException() {
		super("Invalid command");
	}
	
	public InvalidCommandException(Command command, String reason) {
		super(formatMessage(command, reason));
	}
	
	private static String formatMessage(Command command, String reason) {
		StringBuilder builder = new StringBuilder();
		builder.append(command.toString());
		builder.append(": ");
		builder.append(reason);
		return builder.toString();
	}
}
