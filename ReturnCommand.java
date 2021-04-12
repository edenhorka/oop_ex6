package oop.ex6.main.commands;

import oop.ex6.main.Parser;
import oop.ex6.main.Regex;
import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.scopes.Scope;


/**
 * Class for return commands.
 */
public class ReturnCommand extends Command{

	/**
	 * Constructor for return command.
	 * @param outerScope the outer scope.
	 * @param currentLine the current line.
	 * @throws ParseException if code is illegal.
	 */
	public ReturnCommand(Scope outerScope, String currentLine) throws ParseException {
		super(outerScope, currentLine);
		isReturn = true;
		if (this.outerScope == Parser.getGlobalScope())
			throw new ParseException(lineNumber, ParseException.SCOPE_NOT_CLOSED);
	}

	/**
	 * Checks if return command is legal.
	 * @throws ParseException if code is illegal.
	 */
	@Override
	public void checkCommand(boolean firstCheck) throws ParseException {
		Regex.checkIsReturn(currentLine);
	}
}
