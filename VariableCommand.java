package oop.ex6.main.commands;

import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.scopes.Scope;
import oop.ex6.main.variables.Variable;

public class VariableCommand extends Command {

    private Variable variable;

    /**
     * Creates a new variable command in case of variable assignment.
     *
     * @param outerScope  the outer scope.
     * @param currentLine the current line.
     * @throws ParseException if code is illegal.
     */
    public VariableCommand(Scope outerScope, String currentLine) throws ParseException {
        super(outerScope, currentLine);
        checkCommand(true);
    }

    /**
     * Checks if variable is valid and re-assigns value.
     * @param firstCheck - true if this is the first check, false if it is the second check.
     * @throws ParseException if variable assignment is illegal.
     */
    public void checkCommand(boolean firstCheck) throws ParseException {

        String[] commandString = currentLine.split("=");
        Name = commandString[0].trim();
        String value = commandString[1].trim().replace(";", "");

        if(firstCheck && outerScope.getVariables().containsKey(Name)) {
            variable = outerScope.getVariables().get(Name);
            variable.setValue(value);
            variable.setAssignmentLineNumber(lineNumber);
        }

        else if (!firstCheck) {
            variable.checkNewAssignment(variable.getValue(), getLineNumber(), outerScope);
        }
    }
}
