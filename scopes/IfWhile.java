package oop.ex6.main.scopes;

import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.Parser;
import oop.ex6.main.Regex;

/**
 * Class for if/while scopes.
 */
public class IfWhile extends Scope {

    /** String array of conditions for this scope. **/
    private String[] conditions;

    /**
     * Constructor for if/while scope.
     * @param outerScope the outer scope.
     * @param currentLine the current line.
     * @throws ParseException if code is illegal.
     */
    public IfWhile(Scope outerScope, String currentLine) throws ParseException {
        super(outerScope);
        isIfWhile = true;

        if (outerScope == Parser.getGlobalScope())
            throw new ParseException(lineNumber, ParseException.INVALID_SYNTAX);

        String[] line = currentLine.split("\\(");
        name = line[0].replaceAll("\\s*", "");

        addScopes();
        this.outerScope.addIfWhile(this);
        String conditionLine = line[1].split("\\s*\\)\\s*\\{")[0];
        conditions = conditionLine.split("\\s*(?:&&|\\|\\|)\\s*");
        // todo: the following line in second check only
        for (int i = 0; i < conditions.length; i++)
            conditions[i] = conditions[i].trim();
    }

    /**
     * Checks that boolean condition is valid.
     * @throws ParseException if condition is invalid
     */
    public void checkCondition() throws ParseException {
        // check each condition
        for (String condition : conditions) {
             // check if condition is a boolean value or variable
            if (!Regex.checkIsBoolean(condition)) {
                String varValue = null;
                if (outerScope.getVariables().containsKey(condition) && variables.containsKey(condition)) {
                    varValue = outerScope.getVariables().get(condition).getValue();
                    outerScope.getVariables().get(condition).setNeedsSecondCheck(true);
                } else if (Parser.getGlobalScope().getVariables().containsKey(condition)) {
                    varValue = Parser.getGlobalScope().getVariables().get(condition).getValue();
                }
                // if variable isn't initialized or its value doesn't match a boolean
                if (varValue == null || !Regex.checkIsBoolean(varValue)) {
                    throw new ParseException(lineNumber, ParseException.ILLEGAL_CONDITION);
                }

            }
        }
    }
}



