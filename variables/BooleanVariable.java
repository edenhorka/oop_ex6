package oop.ex6.main.variables;

import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.Regex;
import oop.ex6.main.scopes.Scope;

/**
 * A class for a boolean variable that held by a certain scope.
 */
public class BooleanVariable extends GeneralVariable {

    /** the name of this variable type */
    static final String BOOLEAN = "boolean";

    /**
     * Creates a new boolean variable with the given attributes
     * @param scope - the scope that holds this variable
     * @param name - the name to assign
     * @param varValue - the value to assign
     * @param isFinal - if this variable is final or not
     * @throws ParseException - the given attributes aren't valid
     */
    BooleanVariable(Scope scope, String name, String varValue, boolean isFinal) throws ParseException {
        super(scope, name, varValue, isFinal);
        varType = BOOLEAN;
    }

    /**
     * @param assignedType type of the assigned value/variable
     * @return true if the assignment matches this variable type
     */
    @Override
    boolean isAssignedTypeValid(String assignedType) {
        switch (assignedType){
            case BOOLEAN: case IntVariable.INT: case DoubleVariable.DOUBLE :
                return true;
            default:
                return false;
        }
    }

    /**
     * when a value is assigned to this variable, check if it matches.
     * @return true if the value matches this variable type
     */
    @Override
    public  boolean checkValueType(){
        return Regex.checkIsBoolean(value);
    }
}
