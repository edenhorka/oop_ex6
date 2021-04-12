package oop.ex6.main.commands;

import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.Parser;
import oop.ex6.main.scopes.Scope;

/**
 * Abstract class for commands.
 */
public abstract class Command {

    /** The outer scope of this scope **/
    Scope outerScope;

    /** The current line. **/
    String currentLine;

    /** The name of this command (variable or method being called) **/
    String Name;

    /** The line number for this command. **/
    int lineNumber;

    /** Is a return command. **/
    public boolean isReturn = false;

    /**
     * Constructor for commands.
     * @param outerScope the outer scope.
     * @param currentLine the current line being read.
     * @throws ParseException if scope is not closed.
     */
    Command(Scope outerScope, String currentLine) throws ParseException {
        this.outerScope = outerScope;
        this.currentLine = currentLine;
        this.lineNumber = Parser.getCurrentLineNumber();
        outerScope.setLastLine(lineNumber);
//        this.outerScope.addCommand(this);
        if (!currentLine.endsWith(";")) throw new ParseException(Parser.getCurrentLineNumber(),
                ParseException.INVALID_SYNTAX);
    }

    /**
     * Abstract method to check commands after file has been fully parsed.
     * @throws ParseException if command is illegal.
     */
    public abstract void checkCommand(boolean firstCheck) throws ParseException;

    /**
     * Gets the name of the command.
     * @return the name of the command.
     */
    public String getName() {
        return Name;
    }

    /**
     * Gets the line number
     * @return the line number.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Checks if command is a return.
     * @return true if is return, false otherwise.
     */
    public boolean isReturn() {
        return isReturn;
    }
}
