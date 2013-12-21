package kaffee.kompressor;

import java.util.List;

/**
 *
 * @author vszakonyi
 */
enum StateMachine {
	NON_PHP,
	CODE,
	IN_COMMENT,
	INTERPRETED_LITERAL,
	LITERAL
}
