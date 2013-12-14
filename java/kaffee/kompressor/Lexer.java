package kaffee.kompressor;

import java.util.List;

/**
 *
 * @author vszakonyi
 */
public class Lexer {

	StateMachine sm = StateMachine.NON_PHP;
	private static String operators = "'\"{}()[]=+-%!*.:<>;&|,";

	public void compress(List<String> lines) {
		for (String line : lines) {
		}
	}

	boolean isOperator(Character c) {
		return operators.indexOf(c.charValue()) != -1;
	}

	String compressLine(final String lineToCompress) {
		StringBuilder line = new StringBuilder(lineToCompress);
		String ret = "";
		for (int i = 0; i < line.length(); i++) {
			switch (sm) {
				case NON_PHP:
					if (line.length() > i + 4) {
						if (line.subSequence(i, i + 5).equals("<?php")) {
							sm = StateMachine.CODE;
							i += 4;
							continue;
						}
					}
					ret+=line.charAt(i);
					break;
				case CODE:
					if (line.length() > i + 1 && line.charAt(i) == '/' && line.charAt(i + 1) == '/') {
						i=line.length();
						break;
					}
					if (isWhiteSpace(line.charAt(i))) {
						if (ret.isEmpty()) {
							continue;
						} else if (isAlphaNum(ret.charAt(ret.length() - 1))) {
							if (line.length() > i) {
								if (isAlphaNum(line.charAt(i + 1))) {
									ret += line.charAt(i);
								}
							}
						} else if (!isOperator(ret.charAt(ret.length() - 1))) {
							ret += line.charAt(i);
						}
						continue;
					} else if (line.charAt(i) == '"') {
						do {
							if (line.length() > i + 1) {
								ret += line.charAt(i);
								i++;
							} else {
								break;
							}
						} while (line.charAt(i) != '"');
					} else if (line.charAt(i) == '/' && line.length() > i + 1) {
						if (line.charAt(i + 1) == '*') {
							sm = StateMachine.IN_COMMENT;
							i++;
							break;
						}
					}
					ret += line.charAt(i);
					break;
				case IN_COMMENT:
					boolean commentEnded = false;
					do {
						if (line.length() > i + 1) {
							if (line.charAt(i) == '*' && line.charAt(i + 1) == '/') {
								commentEnded = true;
							}
							i += 2;
						} else {
							continue;//not good
						}
					} while (!commentEnded);
					if (line.length() > i) {
						line.setCharAt(i, ' ');
					}
					break;
					
			}
		}
		return ret;
	}

	private boolean isAlphaNum(char c) {
		return Character.isAlphabetic(c) | Character.isDigit(c) | c == '$';
	}

	private boolean isWhiteSpace(char c) {
		if (c == ' ' || c == '\t') {
			return true;
		}
		return false;
	}
}
