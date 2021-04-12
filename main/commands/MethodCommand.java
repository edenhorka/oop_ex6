package oop.ex6.main.commands;

import oop.ex6.main.*;
import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.scopes.Method;
import oop.ex6.main.scopes.Scope;
import oop.ex6.main.variables.Variable;
import java.util.ArrayList;

/**
 * Class for method commands.
 */
public class MethodCommand extends Command {

    /** An array of parameters for this method.**/
    private String[] parameters = new String[0];

    /**
     * Constructor for method command.
     * @param outerScope the outer scope.
     * @param currentLine the current line.
     * @throws ParseException if code is invalid.
     */
    public MethodCommand(Scope outerScope, String currentLine) throws ParseException {
        super(outerScope, currentLine);
        if (this.outerScope == Parser.getGlobalScope())
            throw new ParseException(lineNumber, ParseException.SCOPE_NOT_CLOSED);
    }

    /**
     * Checks method commands for syntax and legality of parameters.
     * @throws ParseException if code is illegal.
     */
    @Override
    public void checkCommand(boolean firstCheck) throws ParseException {

        String[] commandString = currentLine.split("\\(");
        Name = commandString[0].trim();
        String paramsString = commandString[1].replaceAll("\\);", "").trim();
        if (!paramsString.equals("")) parameters = paramsString.split(",");

        if (Parser.getGlobalScope().getMethods().containsKey(Name)) {
            checkParameters(parameters, Parser.getGlobalScope().getMethods().get(Name));
        } else {
            throw new ParseException(getLineNumber(), ParseException.METHOD_DOES_NOT_EXIST);
        }
    }

    /**
     * Checks the method command that the correct number and types of parameters are used.
     * @param parameters the parameters array.
     * @param methodCalled the method being called.
     * @throws ParseException if parameters are illegal.
     */
    private void checkParameters(String[] parameters, Method methodCalled) throws ParseException {
        // if method call holds valid number of parameters.
        if ((parameters != null) && (parameters.length == methodCalled.getParameters().size())) {
            ArrayList<Variable> paramsList = methodCalled.getParameters();
            for (int i = 0; i < paramsList.size(); i++) {
                Variable currentParam = paramsList.get(i);
                currentParam.setValue(parameters[i].trim());
                if(!currentParam.checkValueType()) {
                    currentParam.checkAssignedVar(getLineNumber(), outerScope);
                }
                if (currentParam.getValue() == null) {
                    throw new ParseException(getLineNumber(), ParseException.ILLEGAL_VARIABLE_ASSIGNMENT);
                }
            }
        } else {
            throw new ParseException(getLineNumber(), ParseException.INVALID_PARAMS);
        }

    }
}
