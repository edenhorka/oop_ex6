package oop.ex6.main.variables;

import oop.ex6.main.exceptions.ParseException;
import oop.ex6.main.scopes.Scope;

/**
 * an Interface for a Variable, held by a certain scope.
 */
public interface Variable {

    /**
     * @return the variable type
     */
    String getType();

    /**
     * @return the variable value
     */
    String getValue();

    /**
     * @return the variable name
     */
    String getName();

    /**
     * @return the scope that holds the variable
     */
    Scope getScope();

    /**
     * sets a new value
     * @param newValue the new value to set
     */
    void setValue(String newValue);

    /**
     * @return true if the variable is final, else false;
     */
    boolean isFinal();

    /**
     * @return true if this variable needs a second validity check
     */
    boolean needsSecondCheck();

    /**
     * @return true if this value is a parameter of a method
     */
    boolean isParameter();

    /**
     * sets this variable to be a parameter
     */
    void setAsParameter();

    /**
     * sets the previous value to be the current value
     */
    void setPrevValue();

    /**
     * @return the line number this variable got an assignment
     */
    int getAssignmentLineNumber();

    /**
     * sets the assignment line number
     * @param assignmentLineNumber the line number to set
     */
    void setAssignmentLineNumber(int assignmentLineNumber);

    /**
     * sets the need for second checking
     * @param needsSecondCheck -  a boolean value to assign
     */
    void setNeedsSecondCheck(boolean needsSecondCheck);

    /**
     * when another variable is assigned to this variable, check if it matches.
     * @param lineNumber - line number of this assignment
     * @param scope - scope of this assignment
     * @throws ParseException - if the assignment wasn't legal
     */
    void checkAssignedVar(int lineNumber, Scope scope) throws ParseException;

    /**
     * when a value is assigned to this variable, check if it matches.
     * @return true if the value matches this variable type
     */
	boolean checkValueType();

    /**
     * when there's a re-assignment, check if it matches.
     * @param newValue - a new value to assign
     * @param lineNumber - line number of this assignment
     * @param scope - scope of this assignment
     * @throws ParseException - if re-assignment wasn't legal.
     */
	void checkNewAssignment(String newValue, int lineNumber, Scope scope) throws ParseException;

}
