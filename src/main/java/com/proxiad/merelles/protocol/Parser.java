package com.proxiad.merelles.protocol;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.Location;

public class Parser {

	private static final String NO_COMMAND = "No command";

	public Command parse(String commandText, Board board) throws ParsingException {
		if (commandText == null || commandText.length() == 0) {
			throw new ParsingException(NO_COMMAND);
		}
		String[] messageParts = commandText.split(";");
		String message = messageParts.length > 1 ? messageParts[1] : null;
		
		String[] tokens = messageParts[0].split(" ");

		int pieceId = parseInt(tokens, 0, "PIECE_ID");
		int direction = parseInt(tokens, 1, "DIRECTION");
		int radius = parseInt(tokens, 2, "RADIUS");
		int removePieceId = parseInt(tokens, 3, "REMOVE_PIECE_ID");
		Location targetLocation = new Location(direction, radius);
		return new Command(board.findPieceById(pieceId), targetLocation, board.findPieceById(removePieceId), message);
	}

	private static int parseInt(String[] tokens, int tokenIndex, String fieldName) throws ParsingException {
		String token = retrieveToken(tokens, tokenIndex, fieldName);

		try {
			return Integer.parseInt(token);
		} catch (NumberFormatException exc) {
			throw new ParsingException(formatMessageForNumber(token, fieldName));
		}
	}

	private static String retrieveToken(String[] tokens, int tokenIndex, String fieldName) throws ParsingException {
		if (tokens == null || tokens.length == 0) {
			throw new ParsingException(NO_COMMAND);
		}

		if (tokenIndex >= tokens.length) {
			throw new ParsingException(formatInsufficientArguments(fieldName));
		}

		return tokens[tokenIndex];
	}

	private static String formatInsufficientArguments(String fieldName) {
		StringBuilder builder = new StringBuilder();
		builder.append("Expecting ");
		builder.append(fieldName);
		builder.append(" but found end of line");
		return builder.toString();
	}

	private static String formatMessageForNumber(String token, String fieldName) {
		return formatMessage("number", token, fieldName);
	}

	private static String formatMessage(String expectation, String token, String fieldName) {
		StringBuilder builder = new StringBuilder();
		builder.append("Expecting ");
		builder.append(expectation);
		builder.append(" for ");
		builder.append(fieldName);
		builder.append(" but found '");
		builder.append(token != null ? token : "");
		builder.append('\'');
		return builder.toString();
	}
}
