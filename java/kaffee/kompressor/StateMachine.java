package kaffee.kompressor;

import java.util.List;

/**
 *
 * @author vszakonyi
 */
public class StateMachine {

    List<String> lines;
    boolean inComment;
    boolean inLiteral;

    public boolean isInLiteral() {
        return inLiteral;
    }

    public void setInLiteral(boolean inLiteral) {
        this.inLiteral = inLiteral;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public void setInComment(boolean inComment) {
        this.inComment = inComment;
    }

    public List<String> getLines() {
        return lines;
    }

    public boolean isInComment() {
        return inComment;
    }
}
