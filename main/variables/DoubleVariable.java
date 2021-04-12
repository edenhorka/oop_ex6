package oop.ex6.main.variables;

import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.Regex;
import oop.ex6.main.scopes.Scope;

/**
 * A class for a double variable that held by a certain scope.
 */
public class DoubleVariable extends GeneralVariable {

    /** the name of this variable type */
    static final String DOUBLE = "double";

    /**
     * Creates a new double variable with the given attributes
     * @param scope - the scope that holds this variable
     * @param name - the name to assign
     * @param varValue - the value to assign
     * @param isFinal - if this variable is final or not
     * @throws ParseException - the given attributes aren't valid
     */
    DoubleVariable(Scope scope, String name, String varValue, boolean isFinal) throws ParseException {
        super(scope, name, varValue, isFinal);
        varType = DOUBLE;
    }

    /**
     * @param assignedType type of the assigned value/variable
     * @return true if the assignment matches this variable type
     */
    @Override
    boolean isAssignedTypeValid(String assignedType) {
        return assignedType.equals(DOUBLE) || assignedType.equals(IntVariable.INT);
    }

    /**
     * when a value is assigned to this variable, check if it matches.
     * @return true if the value matches this variable type
     */
    @Override
    public boolean checkValueType() {
        return Regex.checkIsDouble(value);
    }
}
