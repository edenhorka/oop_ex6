package oop.ex6.main.exceptions;

/**
 * Class for general parse exception. Extended by moe specific exception classes.
 */
public class ParseException extends Exception {

    /* Don't know what this does, but slideshow said to add it. */
    private static final long serialVersionUID = 1L;

    /** The int value of an illegal code exception **/
    private static final int ILLEGAL_INT = 1;

    /** Message for line number **/
    private static final String lineMessage = "\nError at line: ";

    /** The message for invalid condition exception. **/
    public static final String ILLEGAL_CONDITION = "Condition must be a boolean, int or double.";

    /** The message for method does not exist exception. **/
    public static final String METHOD_DOES_NOT_EXIST = "No such method exists.";

    /** The message for illegal method declaration exception. **/
    public static final String ILLEGAL_METHOD_DECLARATION = "Methods can only be declared in global scope.";

    /** The message for illegal variable assignment exception. **/
    public static final String ILLEGAL_VARIABLE_ASSIGNMENT = "Variable does not exist or is final.";

    /** The message for illegal variable type exception. **/
    public static final String ILLEGAL_VARIABLE_TYPE = "The variable type is invalid.";

    /** The message for invalid params exception. **/
    public static final String INVALID_PARAMS = "Invalid number of parameters";

    /** The message for invalid syntax exception. **/
    public static final String INVALID_SYNTAX = "The syntax is invalid.";

    /** The message for missing return statement exception. **/
    public static final String MISSING_RETURN_STATEMENT = "Method must end with return statement.";

    /** The message for scope not closed exception. **/
    public static final String SCOPE_NOT_CLOSED = "The scope is not closed.";

    /**
     * Constructor for exception.
     * @param currentLineNumber the current line number.
     */
    public ParseException(int currentLineNumber, String message) {
        System.out.println(ILLEGAL_INT);
        System.err.println(message + lineMessage + currentLineNumber);
    }
}
