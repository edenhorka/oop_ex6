package oop.ex6.main.exceptions;

/**
 * Class for i/o exceptions.
 */
public class IOException extends Exception {

    /* Don't know what this does, but slideshow said to add it. */
    private static final long serialVersionUID = 1L;

    /** The int value for IO exception **/
    private static final int IO_ERROR_INT = 2;

    /** The message for this exception. **/
    private static final String IO_ERROR_MESSAGE = "Problem reading file.";

    /**
     * Constructor for exception.
     */
    public IOException() {
        System.out.println(IO_ERROR_INT);
        System.err.println(IO_ERROR_MESSAGE);
    }
}
