package kaffee.kompressor;

import java.util.List;

/**
 * .
 * @author vszakonyi
 */
public class Lexer {

	StateMachine sm = StateMachine.NON_PHP;
	private static final String operators = "'\"{}()[]=+-%!*.:<>;&|,";

	public void compress(List<String> lines) {
		for (String line : lines) {
		}
	}

	boolean isOperator(Character c) {
		return operators.indexOf(c.charValue()) != -1;
	}

	String compressLine(final String lineToCompress) {
		StringBuilder line = new StringBuilder(lineToCompress);
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < line.length(); i++) {
			switch (sm) {
				case NON_PHP:
					if (line.length() > i + 4 && line.subSequence(i, i + 5).equals("<?php")) {
						sm = StateMachine.CODE;
						i += 4;
						continue;
					}
					ret.append(line.charAt(i));
					break;
				case CODE:
					if (line.length() > i + 1 && line.charAt(i) == '/' && line.charAt(i + 1) == '/') {
						i = line.length();
						break;
					}
					if (isWhiteSpace(line.charAt(i))) {
						if (ret.length() == 0) {
							continue;
						} else if (isAlphaNum(ret.charAt(ret.length() - 1))) {
							if (line.length() > i) {
								if (isAlphaNum(line.charAt(i + 1))) {
									ret.append(line.charAt(i));
								}
							}
						} else if (!isOperator(ret.charAt(ret.length() - 1))) {
							ret.append(line.charAt(i));
						}
						continue;
					} else if (line.charAt(i) == '"') {
						sm = StateMachine.INTERPRETED_LITERAL;
					} else if (line.charAt(i) == '\'') {
						sm = StateMachine.LITERAL;
					} else if (line.charAt(i) == '/' && line.length() > i + 1) {
						if (line.charAt(i + 1) == '*') {
							sm = StateMachine.IN_COMMENT;
							i++;
							break;
						}
					}
					ret.append(line.charAt(i));
					break;
				case IN_COMMENT:
					boolean commentEnded = false;
					do {
						if (line.length() > i + 1) {
							if (line.charAt(i) == '*' && line.charAt(i + 1) == '/') {
								commentEnded = true;
								sm = StateMachine.CODE;
							}
							i += 1;
						} else {
							break;
						}
					} while (!commentEnded);
					if (line.length() > i) {
						line.setCharAt(i, ' ');
					}
					break;
				case INTERPRETED_LITERAL:
					do {
						ret.append(line.charAt(i));
						if (line.charAt(i) == '"') {
							if (i == 0 || line.charAt(i - 1) != '\\') {
								sm = StateMachine.CODE;
								break;
							}
						}
						i++;
					} while (line.length() > i);
					break;
				case LITERAL:
					do {
						ret.append(line.charAt(i));
						if (line.charAt(i) == '\'') {
							if (i == 0 || line.charAt(i - 1) != '\\') {
								sm = StateMachine.CODE;
								break;
							}
						}
						i++;
					} while (line.length() > i);
					break;
			}
		}
		return ret.toString();
	}

	private boolean isAlphaNum(char c) {
		return Character.isAlphabetic(c) || Character.isDigit(c) || c == '$';
	}

	private boolean isWhiteSpace(char c) {
		return c == ' ' || c == '\t';
	}
}
