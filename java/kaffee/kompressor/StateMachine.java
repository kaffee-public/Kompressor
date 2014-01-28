package kaffee.kompressor;

/**
 * .
 * @author vszakonyi
 */
enum StateMachine {

	NON_PHP,
	CODE,
	IN_COMMENT,
	INTERPRETED_LITERAL,
	LITERAL
}
