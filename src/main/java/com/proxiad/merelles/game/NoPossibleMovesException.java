package com.proxiad.merelles.game;

public class NoPossibleMovesException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoPossibleMovesException() {
		super("No possible moves");
	}
}
