package oop.ex6.main;

import oop.ex6.main.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for regex patterns, for checking sJava files.
 */
public class Regex {

    private static final String AND = "[&]{2}";

    private static final String OR = "[|]{2}";

    private static final Pattern CHAR = Pattern.compile("'.?'");

    private static final Pattern INT = Pattern.compile("-?\\d+");

    private static final Pattern DOUBLE = Pattern.compile("(?:-?\\d+(?:\\.\\d+)?)");

    private static final Pattern BOOLEAN = Pattern.compile("(?:false|true)|"+ DOUBLE);

    private static final Pattern STRING = Pattern.compile("\"\\s*.*\\s*\"");

    private static final Pattern TYPE_NAME = Pattern.compile("(?:int|double|String|boolean|char)");

    private static final Pattern METHOD_NAME = Pattern.compile("[a-zA-Z][\\w]*");

    private static final Pattern VAR_NAME = Pattern.compile("(?:[a-zA-Z][\\w]*|[_][\\w]+)");

    private static final Pattern LINE_CLOSE = Pattern.compile("\\s*;\\s*$");

    private static final Pattern FINAL = Pattern.compile("\\s*(?:final)");

    private static final Pattern VARIABLE_DECLARATION = Pattern.compile("\\s*(?:" + FINAL + "\\s+)?\\s*(?:"+
            TYPE_NAME + "\\s+"+ VAR_NAME + "+(?:\\s*,\\s*"+VAR_NAME+"\\s*)*)");

    private static final Pattern PARAMETER = Pattern.compile("\\s*(?:" + VAR_NAME + "|" + DOUBLE + "|" + STRING +
            "|" + CHAR + ")\\s*");

    private static final Pattern VARIABLE_ASSIGNMENT = Pattern.compile("\\s*" + VAR_NAME + "\\s*=\\s*" +
            PARAMETER+"\\s*");

    static final Pattern METHOD_START_LINE = Pattern.compile("^\\s*void\\s+" +METHOD_NAME +"\\s*\\(\\s*" +
            "(?:\\s*" + VARIABLE_DECLARATION + "\\s*(?:,\\s*" + VARIABLE_DECLARATION +  "\\s*)*)*\\s*\\)" +
            "\\s*\\{\\s*$");

    static final Pattern METHOD_CALL = Pattern.compile("\\s*" + METHOD_NAME + "\\s*\\((?:" + PARAMETER +
                    "(?:," + PARAMETER + ")*)*\\)" + LINE_CLOSE);


    static final Pattern VARIABLE_ASSIGNMENTS = Pattern.compile( VARIABLE_ASSIGNMENT +"(?:\\s*,\\s*"+
            VARIABLE_ASSIGNMENT + ")*\\s*;\\s*$");

    static final Pattern VAR_START_LINE = Pattern.compile("^\\s*(?:" + FINAL + "\\s+)?" + TYPE_NAME +
            "\\s+(?:" + VAR_NAME + "|" + VARIABLE_ASSIGNMENT + ")\\s*(?:,\\s*(?:" + VAR_NAME+ "|" +
                    VARIABLE_ASSIGNMENT + ")\\s*)*\\s*;\\s*$");

    static final Pattern RETURN_STATEMENT = Pattern.compile("\\s*return\\s*;\\s*");

    static final Pattern COMMENT = Pattern.compile("^//.*");

    static final Pattern EMPTY_SPACE = Pattern.compile("\\s*");

    static final Pattern SCOPE_CLOSE = Pattern.compile("\\s*}\\s*$");

    static final Pattern IF_WHILE_START_LINE = Pattern.compile("^\\s*(?:if|while)\\s*\\" +
            "(\\s*(?:"+BOOLEAN+"|\\w+)\\s*" + "(?:(?:" + OR + "|"+ AND + ")\\s*(?:"  +BOOLEAN+ "|\\w+)\\s*)" +
            "*\\)\\s*\\{\\s*$");


    /**
     * Check if a given string matches a given pattern
     * @param regex - a pattern to check according to.
     * @param toMatch - a string to match the pattern.
     * @throws ParseException - if the string doesn't match the pattern
     */
    private static void requireMatch(Pattern regex, String toMatch) throws ParseException {
        Matcher match = regex.matcher(toMatch);
        if (!match.matches()) throw new ParseException(Parser.getCurrentLineNumber(),
                ParseException.INVALID_SYNTAX);
    }

    /**
     * @param regex - a pattern to check according to.
     * @param toMatch - a string to match the pattern.
     * @return true if the string matches the pattern, false otherwise.
     */
    public static boolean isMatch(Pattern regex, String toMatch) {
        Matcher match = regex.matcher(toMatch);
        return match.matches();
    }

    /**
     * @param value - a value to check
     * @return - true if the value is an int, false otherwise.
     */
    public static boolean checkIsInt(String value) {
        return isMatch(INT, value);
    }

    /**
     * @param value - a value to check
     * @return - true if the value is a double, false otherwise.
     */
    public static boolean checkIsDouble(String value) {
       return isMatch(DOUBLE, value);
    }

    /**
     * @param value - a value to check
     * @return - true if the value is a character, false otherwise.
     */
    public static boolean checkIsChar(String value) {
        return isMatch(CHAR, value);
    }

    /**
     * @param value - a value to check
     * @return - true if the value is a string, false otherwise.
     */
    public static boolean checkIsString(String value) {
        return isMatch(STRING, value);
    }

    /**
     * @param value - a value to check
     * @return - true if the value is a boolean, false otherwise.
     */
    public static boolean checkIsBoolean(String value) {
        return isMatch(BOOLEAN, value);
    }

    /**
     * checks if the given value matches a variable name pattern
     * @param value - a value to check
     * @throws ParseException - if the value doesn't match a variable name
     */
    public static void checkIsVariable(String value) throws ParseException{
        requireMatch(VAR_NAME, value);
    }

    /**
     * checks if the given value matches a return statement pattern
     * @param value - a value to check
     * @throws ParseException - if the value doesn't match a return statement.
     */
    public static void checkIsReturn(String value) throws ParseException{
        requireMatch(RETURN_STATEMENT, value);
    }
}
