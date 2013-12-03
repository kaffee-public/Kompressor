package kaffee.kompressor;

import java.util.List;

/**
 *
 * @author vszakonyi
 */
public class Lexer {

	StateMachine sm = new StateMachine();
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
			if (!sm.isInComment() && isWhiteSpace(line.charAt(i))) {
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
			} else if (sm.isInComment() | (line.charAt(i) == '/' && line.length() > i + 1)) {
				if (!sm.isInComment() && line.charAt(i + 1) == '/') {
					break;
				} else if (sm.isInComment() | line.charAt(i + 1) == '*') {
					sm.setInComment(true);
					do {
						if (line.length() > i + 1) {
							i++;
						} else {
							break;
						}
					} while (!(line.charAt(i) == '*' && line.charAt(i + 1) == '/'));
					sm.setInComment(false);
					if (line.length() > i + 1) {
						line.setCharAt(i + 1, ' ');
					}
					continue;
				}
			} else if (line.charAt(i) == '"') {
				sm.setInLiteral(true);
				do {
					if (line.length() > i + 1) {
						ret += line.charAt(i);
						i++;
					} else {
						break;
					}
				} while (line.charAt(i) != '"');
				sm.setInLiteral(false);
			}
			ret += line.charAt(i);
		}//endof for
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
