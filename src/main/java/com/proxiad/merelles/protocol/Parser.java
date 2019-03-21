package com.proxiad.merelles.protocol;

import com.proxiad.merelles.game.Board;
import com.proxiad.merelles.game.Command;
import com.proxiad.merelles.game.Location;
import com.proxiad.merelles.game.Piece;

public abstract class Parser<C extends Command> {

	private static final String NO_COMMAND = "No command";

	private String keyword;
	private int numberOfSpecificArguments;
	
	protected Parser(String keyword, int numberOfSpecificArguments) {
		this.keyword = keyword;
		this.numberOfSpecificArguments = numberOfSpecificArguments;
	}
	
	public C parseCommand(String commandText, Board board) throws ParsingException {
		if (commandText == null || commandText.length() == 0) {
			throw new ParsingException(NO_COMMAND);
		}
		String[] messageParts = commandText.split(";");
		String message = messageParts.length > 1 ? messageParts[1] : null;
		
		String[] tokens = messageParts[0].split(" ");

		checkKeyword(tokens, 0, keyword);

		int direction = parseInt(tokens, 1 + numberOfSpecificArguments, "DIRECTION");
		int radius = parseInt(tokens, 2 + numberOfSpecificArguments, "RADIUS");
		int removePieceId = parseInt(tokens, 3 + numberOfSpecificArguments, "REMOVE_PIECE_ID");
		Piece removePiece = board.findPieceById(removePieceId);
		Location targetLocation = new Location(direction, radius);

		return parseCommandArguments(board, message, tokens, targetLocation, removePiece);
	}

	abstract protected C parseCommandArguments(Board board, String message, String[] tokens,
			Location targetLocation, Piece removePiece) throws ParsingException;
	
	private static void checkKeyword(String[] tokens, int tokenIndex, String fieldName) throws ParsingException {
		String token = retrieveToken(tokens, tokenIndex, fieldName);

		if (!fieldName.equals(token)) {
			throw new ParsingException(formatMessageMissingKeyword(token, fieldName));
		}
	}

	protected static int parseInt(String[] tokens, int tokenIndex, String fieldName) throws ParsingException {
		String token = retrieveToken(tokens, tokenIndex, fieldName);

		try {
			return Integer.parseInt(token);
		} catch (NumberFormatException exc) {
			throw new ParsingException(formatMessageForNumber(token, fieldName));
		}
	}

	protected static String retrieveToken(String[] tokens, int tokenIndex, String fieldName) throws ParsingException {
		if (tokens == null || tokens.length == 0) {
			throw new ParsingException(NO_COMMAND);
		}

		if (tokenIndex >= tokens.length) {
			throw new ParsingException(formatInsufficientArguments(fieldName));
		}

		return tokens[tokenIndex];
	}

	protected static String formatInsufficientArguments(String fieldName) {
		StringBuilder builder = new StringBuilder();
		builder.append("Expecting ");
		builder.append(fieldName);
		builder.append(" but found end of line");
		return builder.toString();
	}

	protected static String formatMessageForNumber(String token, String fieldName) {
		return formatMessage("number", token, fieldName);
	}

	protected static String formatMessage(String expectation, String token, String fieldName) {
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
	
	protected static String formatMessageMissingKeyword(String token, String fieldName) {
		StringBuilder builder = new StringBuilder();
		builder.append("Expecting ");
		builder.append(fieldName);
		builder.append(" but found '");
		builder.append(token != null ? token : "");
		builder.append('\'');
		return builder.toString();
	}
	
	protected static String formatUnknownPiece(int pieceId) {
		StringBuilder builder = new StringBuilder();
		builder.append("Unknown piece ");
		builder.append(Integer.toString(pieceId));
		return builder.toString();
	}
}
