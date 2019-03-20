package com.proxiad.merelles.protocol;

public class ParsingException extends Exception {

	private static final long serialVersionUID = 1L;

	protected ParsingException() {
		super("Invalid input");
	}

	public ParsingException(String message) {
		super(message);
	}
}
