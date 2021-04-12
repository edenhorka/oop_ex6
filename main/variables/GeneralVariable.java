package oop.ex6.main.variables;

import oop.ex6.main.Parser;
import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.Regex;
import oop.ex6.main.scopes.Scope;

/**
 * An abstract class for a general variable that held by a certain scope.
 */
abstract class GeneralVariable implements Variable {

    /** indicates if this variable is final or not. */
    private boolean isFinal;

    /** the type of this variable. */
    String varType;

    /** the name of this variable*/
    private String name;

    /** the value of this variable*/
    String value;

    /** the previous value of this variable */
    private String prevValue;

    /** the scope that holds this variable */
    private Scope scope;

    /** indicates if this variable needs a second validity check*/
    private boolean needsSecondCheck = false;

    /** indicates if this variable is a parameter of a method*/
    private boolean isParameter = false;

    /** the line number this variable was last assigned */
    private int assignmentLineNumber = -1;


    /**
     * Creates a new variable with the given attributes.
     * @param scope - the scope that holds this variable
     * @param name - the name to assign
     * @param varValue - the value to assign
     * @param isFinal - if this variable is final or not
     * @throws ParseException - if the given attributes aren't valid
     */
    GeneralVariable(Scope scope, String name, String varValue, boolean isFinal) throws ParseException {
        this.scope = scope;
        this.name = name;
        this.value = varValue;
        this.prevValue = value;
        this.isFinal = isFinal;
        scope.addVariable(this);
        scope.setLastLine(Parser.getCurrentLineNumber());
        if( isFinal&& value == null ){
            needsSecondCheck =true;
        }
        if (value != null) {
            // value was assigned at declaration
            assignmentLineNumber = Parser.getCurrentLineNumber();
            if ( !checkValueType() ) {
                // assigned value may be another variable
                needsSecondCheck = true;
            }
        }
    }

    /**
     * when another variable is assigned to this variable, check if it matches.
     * @param lineNumber - line number of this assignment
     * @param assignmentScope - scope of this assignment
     * @throws ParseException - if the assignment wasn't legal
     */
    public void checkAssignedVar(int lineNumber, Scope assignmentScope) throws ParseException {
        // if the current variable is a final parameter
        if ( !isParameter && (( isFinal && value == null ) || (value.equals(name)) )) {
            throw new ParseException(lineNumber, ParseException.ILLEGAL_VARIABLE_ASSIGNMENT);
        }

        Regex.checkIsVariable(value); // check that value matches a variable name pattern
        Variable assignedVariable = null;
        Scope globalScope = Parser.getGlobalScope();

        // if this scope is the global scope:
        if (this.scope == globalScope) {
            // check if the scope of the assigned value is also the global scope.
            if (assignmentScope == globalScope && globalScope.getVariables().containsKey(value)) {
                assignedVariable = globalScope.getVariables().get(value);
                // check if the assigned value hasn't been initialized in the previous lines.
                if ( assignedVariable.getAssignmentLineNumber()==-1|| assignedVariable.getAssignmentLineNumber
                        () > assignmentLineNumber) {
                    throw new ParseException(lineNumber, ParseException.ILLEGAL_VARIABLE_ASSIGNMENT);
                }
                // check assigned variable type;
                else if ((isAssignedTypeValid(assignedVariable.getType()))) {
                        setValue(assignedVariable.getValue());
                        return;
                }
            } else {
                throw new ParseException(lineNumber, ParseException.ILLEGAL_VARIABLE_ASSIGNMENT);
            }
        }
        // if this scope isn't the global scope:
        else {
            if (assignmentScope.getVariables().containsKey(value))
                assignedVariable = assignmentScope.getVariables().get(value);
            else if (globalScope.getVariables().containsKey(value)) {
                assignedVariable = globalScope.getVariables().get(value);
            }
            if (assignedVariable != null) {
                // check if assigned variable is a parameter, so its value may be unknown.
                if (scope == assignmentScope && assignedVariable.isParameter()) {
                    return;
                }
                // if not a parameter, assigned value must have a value
                if (assignedVariable.getValue() != null) {
                    // check assigned value
                    if ((isAssignedTypeValid(assignedVariable.getType()))) {
                        setValue(assignedVariable.getValue());
                        return;
                    }
                }
            }
        }
        throw new ParseException(lineNumber, ParseException.ILLEGAL_VARIABLE_ASSIGNMENT);
    }

    /**
     * @param assignedType type of the assigned value/variable
     * @return true if the assignment matches this variable type
     */
    boolean isAssignedTypeValid(String assignedType) {
        return varType.equals(assignedType);
    }

    /**
     * when a value is assigned to this variable, check if it matches.
     * @return true if the value matches this variable type
     */
    public abstract boolean checkValueType();

    /**
     * when there's a re-assignment, check if it matches.
     * @param newValue - a new value to assign
     * @param lineNumber - line number of this assignment
     * @param assignmentScope- scope of this assignment
     * @throws ParseException - if re-assignment wasn't legal.
     */
    public void checkNewAssignment(String newValue, int lineNumber, Scope assignmentScope) throws
            ParseException {
        if (!isFinal) {
            setValue(newValue);
            if (checkValueType()) {
                return;
            }
        }
        throw new ParseException(lineNumber, ParseException.ILLEGAL_VARIABLE_ASSIGNMENT);
    }

    /**
     * @return true if the variable is final, else false;
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * @return the variable type
     */
    public String getType() {
        return varType;
    }

    /**
     * @return the variable value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return true if this value is a parameter of a method
     */
    public boolean isParameter() {
        return isParameter;
    }

    /**
     * sets this variable to be a parameter
     */
    public void setAsParameter() {
        this.isParameter = true;
    }

    /**
     * @return the variable name
     */
    public String getName() {
        return name;
    }

    /**
     * @return true if this variable needs a second validity check
     */
    public boolean needsSecondCheck() {
        return needsSecondCheck;
    }

    /**
     * @return the line number this variable got an assignment
     */
    public int getAssignmentLineNumber() {
        return assignmentLineNumber;
    }

    /**
     * sets the need for second checking
     * @param needsSecondCheck -  a boolean value to assign
     */
    public void setNeedsSecondCheck(boolean needsSecondCheck) {
        this.needsSecondCheck = needsSecondCheck;
    }

    /**
     * sets a new value
     * @param newValue the new value to set
     */
    public void setValue(String newValue) {
        prevValue = value;
        value = newValue;
    }

    /**
     * sets the previous value to be the current value
     */
    public void setPrevValue(){
        value = prevValue;
    }

    /**
     * sets the assignment line number
     * @param assignmentLineNumber the line number to set
     */
    public void setAssignmentLineNumber(int assignmentLineNumber) {
        this.assignmentLineNumber = assignmentLineNumber;
    }

    /**
     * @return the scope that holds the variable
     */
    public Scope getScope() {
        return scope;
    }
}