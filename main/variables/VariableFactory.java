package oop.ex6.main.variables;

import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.Parser;
import oop.ex6.main.scopes.Scope;

import java.util.ArrayList;

/**
 * A variable factory, creates variable according to given attributes.
 */
public class VariableFactory {
    private static final String FINAL = "final";
    private static final String EQUALS = "=";
    private static String varType;
    private static Scope scope;
    private static boolean isFinal;
    private static ArrayList<Variable> variableList;

    public static void createVariables(Scope currentScope, String currentLine) throws ParseException {
        varType = null;
        scope = currentScope;
        isFinal = false;
        variableList = new ArrayList<>();
        //remove last character (;):
        String line = currentLine.replaceFirst(";\\s*$", "").trim();
        // check if the variable is final
        int typeLimit = 1;
        if (line.startsWith(FINAL)) {
            isFinal = true;
            typeLimit++;
        }
        String[] lineArray = line.split("\\s+", typeLimit+1);
        varType = lineArray[typeLimit - 1];
        if (lineArray.length <= typeLimit){
            throw new ParseException(Parser.getCurrentLineNumber(),ParseException
                    .ILLEGAL_VARIABLE_ASSIGNMENT);
        }
        String[] variableArray = lineArray[typeLimit].split(",");

        for (String varString : variableArray) {
            variableList.add(getVariableAttributes(varString));
        }


    }

    private static Variable variableTypeChooser(String varName, String varValue) throws ParseException {
        // check the variable type
        switch (varType) {
            case (IntVariable.INT):
                return new IntVariable(scope, varName, varValue, isFinal);
            case (DoubleVariable.DOUBLE):
                return new DoubleVariable(scope, varName, varValue, isFinal);
            case (BooleanVariable.BOOLEAN):
                return new BooleanVariable(scope, varName, varValue, isFinal);
            case (CharVariable.CHAR):
                return new CharVariable(scope, varName, varValue, isFinal);
            case (StringVariable.STRING):
                return new StringVariable(scope, varName, varValue, isFinal);
            default: // if it's an illegal type
                throw new ParseException(Parser.getCurrentLineNumber(), ParseException.ILLEGAL_VARIABLE_TYPE);
        }
    }

    private static Variable getVariableAttributes(String varString) throws ParseException {
        // check if the variable name is legal
        String varName, varValue = null;
        // check if there is an assignment
        if (varString.contains(EQUALS)) {
            String[] varAttributes = varString.split(EQUALS);
            varName = varAttributes[0].trim();
            varValue = varAttributes[1].trim();
        } else {
            varName = varString.trim();
        }
        return variableTypeChooser(varName, varValue);
    }

    public static ArrayList<Variable> getVariableList() {
        return variableList;
    }
}
