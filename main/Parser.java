package oop.ex6.main;

import oop.ex6.main.commands.*;
import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.scopes.*;
import oop.ex6.main.variables.Variable;
import oop.ex6.main.variables.VariableFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A class for parser of sJava files
 */
public class Parser {

    // current line number in the current scope
    private static int currentLineNumber;

    // the global scope
    private static Scope globalScope;

    // current parsed scope
    private Scope currentScope;

    // the scanner of this parser
    private Scanner scanner;

    /**
     * Creates a new parser for a given sJava file
     * @param sJava_file - a file to parse
     * @throws FileNotFoundException - if the given file wasn't found
     */
    Parser(File sJava_file) throws FileNotFoundException {
        scanner = new Scanner(sJava_file);
        // creates the global scope
        globalScope = new Scope(null);
        //initialize:
        currentLineNumber = 0;
        currentScope = globalScope;
    }

    /**
     * parses the sJava file
     * @throws ParseException - if the file contains errors.
     */
    public void parse() throws ParseException {
        String currentLine;
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            currentLineNumber++;
            // processing each line
            processLine(currentLine);
        }
        if (!(currentScope == globalScope)){
            // scope closure is missing
            throw new ParseException(currentLineNumber,ParseException.SCOPE_NOT_CLOSED);
        }

        // second check to make sure all commands refer to existing methods / variables :
        for (Scope scope : globalScope.getInnerScopes().values()) {

            // check commands
            for (Command command : scope.getCommands()) {
                command.checkCommand(false);
            }

            // check variables
            for (Variable variable : scope.getVariables().values()) {
                if (variable.needsSecondCheck()) {
                    variable.checkAssignedVar(currentLineNumber, scope);
                }
            }

            // check if/while conditions
            for (IfWhile ifwhile : scope.getIfWhiles()) {
                ifwhile.checkCondition();
            }

            // check return statements
            if ((scope.isMethod())) {
                if ((scope.getCommands().size()) > 0) {
                    Command lastCommand = scope.getCommands().get(scope.getCommands().size() - 1);
                    if (!lastCommand.isReturn || (scope.getLastLine() != lastCommand.getLineNumber()))
                        throw new ParseException(lastCommand.getLineNumber(),
                                ParseException.MISSING_RETURN_STATEMENT);
                } else {
                    throw new ParseException(scope.getLineNumber(), ParseException.MISSING_RETURN_STATEMENT);
                }
            }
        }
    }

    /**
     * process the given line of the sJava file
     * @param currentLine - a line to process
     * @throws ParseException - if there was an error in this line
     */
    private void processLine(String currentLine) throws ParseException {
        // current line is a comment
        if ((Regex.isMatch(Regex.COMMENT, currentLine)) || (Regex.isMatch(Regex.EMPTY_SPACE, currentLine))) {
            return;
        }
        // current line opens a method definition:
        if (Regex.isMatch(Regex.METHOD_START_LINE, currentLine)) {
            // add the new method inner scope
            currentScope = new Method(currentScope, currentLine);
        }
        // current line opens an if statement or a while loop:
        else if (Regex.isMatch(Regex.IF_WHILE_START_LINE, currentLine)) {
            // add the new if/while inner scope
            currentScope = new IfWhile(currentScope, currentLine);
        }
        // current line is a variable declaration/s:
        else if (Regex.isMatch(Regex.VAR_START_LINE, currentLine)) {
            // add the new variables
            VariableFactory.createVariables(currentScope, currentLine);
        }
        // current line is scope close:
        else if (Regex.isMatch(Regex.SCOPE_CLOSE, currentLine)) {
            if (currentScope != globalScope) {
                resetVariables();
                // update the current scope:
                currentScope = currentScope.getOuterScope();
            } else {
                throw new ParseException(currentLineNumber, ParseException.INVALID_SYNTAX);
            }
        }
        // current line is a variable assignment/s:
        else if (Regex.isMatch(Regex.VARIABLE_ASSIGNMENTS, currentLine)) {
            // add the new assignment commands
            currentScope.addCommand(new VariableCommand(currentScope, currentLine));
        }
        // current line is a method call:
        else if (Regex.isMatch(Regex.METHOD_CALL, currentLine)) {
            // add the new method call command
            currentScope.addCommand(new MethodCommand(currentScope, currentLine));
        }
        // current line is a return statement:
        else if (Regex.isMatch(Regex.RETURN_STATEMENT, currentLine)) {
            // add the new return command
            currentScope.addCommand(new ReturnCommand(currentScope, currentLine));
        } else throw new ParseException(currentLineNumber, ParseException.INVALID_SYNTAX);
    }

    // when a scope is close, the assignments that were in it are now irrelevant.
    private void resetVariables() {
        for (Variable variable : currentScope.getVariables().values()) {
            if (!variable.isParameter()) variable.setPrevValue();
        }
    }

    /**
     * @return the current parsed line number of the file
     */
    public static int getCurrentLineNumber() {
        return currentLineNumber;
    }

    /**
     * @return the global scope of the sJava file
     */
    public static Scope getGlobalScope() {
        return globalScope;
    }

}
