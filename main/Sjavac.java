package oop.ex6.main;


import oop.ex6.main.exceptions.IOException;
import oop.ex6.main.exceptions.ParseException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The main class that runs the program and parses sJava files.
 */
public class Sjavac {

    /** number that represents a legal code */
    private static final int LEGAL = 0;

    /** message that should be printed in case of wrong arguments */
    private static final String INVALID_ARGUMENTS = "Wrong number of args.";

    /**
     * Main
     * @param args - sJava file path
     * @throws IOException - if the file wasn't found.
     */
    public static void main(String[] args) throws IOException {
        // validate arguments
        if (args.length != 1){
            System.err.println(INVALID_ARGUMENTS);
            System.exit(1);
        }

        String sJava_file_string = args[0];
        try {
            File sJava_file = new File(sJava_file_string);
            Parser parser = new Parser(sJava_file);
            parser.parse();

            System.out.println(LEGAL);

        } catch (NullPointerException | FileNotFoundException e) {
            throw new IOException();
        } catch (ParseException e) {
            //do nothing
        }
    }
}
