package oop.ex6.main.scopes;

import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.Parser;
import oop.ex6.main.variables.Variable;
import oop.ex6.main.variables.VariableFactory;
import java.util.ArrayList;

/**
 * Class for a method scope.
 */
public class Method extends Scope {

    /** Array lit of params for this method. **/
    private ArrayList<Variable> paramsListArray = new ArrayList<>();

    /**
     * Constructor for method.
     * @param outerScope the outer scope.
     * @param currentLine the current line.
     * @throws ParseException if code is invalid.
     */
    public Method(Scope outerScope, String currentLine) throws ParseException {
        super(outerScope);
        if (outerScope != Parser.getGlobalScope()) {
            // defining a method is allowed only in the global scope.
            throw new ParseException(lineNumber, ParseException.ILLEGAL_METHOD_DECLARATION);
        }
        isMethod = true;
        // set name & parameters
        initAttributes(currentLine);
        // update scopes
        addScopes();
        // add this method to its outer scope
        this.outerScope.addMethod(this);
    }

    /** sets the name and parameters of this method according to the giving opening line. */
    private void initAttributes(String currentLine) throws ParseException {
        String[] lineArray = currentLine.split("\\s*\\("); // split at parentheses
        name = lineArray[0].split("\\s")[1];  // remove "void "
        String[] params = lineArray[1].split("\\s*\\)\\s*\\{");
        if (params.length > 0) {
            String[] paramsArray = params[0].split("\\s*,\\s*", -1);
            for (String param : paramsArray) {
                VariableFactory.createVariables(this, param);
                paramsListArray.addAll(VariableFactory.getVariableList());
            }
            for( Variable parameter: paramsListArray){
                parameter.setAsParameter();
            }
        }
    }

    /**
     * Gets the parameter list.
     * @return the parameter array list.
     */
    public ArrayList<Variable> getParameters() {
        return paramsListArray;
    }

}
