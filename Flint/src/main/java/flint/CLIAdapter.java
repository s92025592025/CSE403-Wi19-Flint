package flint;

import java.io.File;
import java.util.Collection;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.regex.Pattern;

import flint.FlintConfiguration;
import flint.LintFailure;

public class CLIAdapter {
    /**
     * The function that should be triggered by the command line, this will trigger the
     * linter to check code style based on the user-defined flint config file.
     *
     * @param args - should contain the options and parameters from the command line,
     *               see Overview for details.
     **/
    public static void main(String[] args) throws Exception {
        throw new Exception("Not Yet Implemented");
    }

    /**
     * This function initializes and returns the FlintConfiguration class.
     *
     * @param configPath - The path that leads to the FlintConfiguration subclass file.
     * @return Returns the initialized FlintConfiguration subclass if passed in a valid path.
     * @throws FileNotFoundException - If configPath did not lead to a Java file.
     * @throws IllegalArgumentException - If the passed in configPath is not a FlintConfiguration subclass.
     * */
    private static FlintConfiguration configInit(String configPath) throws Exception {
        if (!isJava(configPath)) {
            throw new FileNotFoundException();
        }
    }

    /**
     * This function checks if filePath leads to an existing java file.
     *
     * @param filePath - The file path to check.
     * @return Returns the true if the file exists and is a java file. Otherwise false.
     * */
    private static boolean isJava(String filePath) {
        File file = new File(filePath);

        return Pattern.matches(".*\\.java$", filePath) && file.exists();
    }

    /**
     * Starts the Driver to run Flint on fileToLint with config.
     *
     * @param fileToLint - The file path that leads to a valid and existing Java file.
     * @param config - The configuration to run on flieToLint.
     * // Otherexception that will be returned from run
     * */
    private static Collection<LintFailure> run(String fileToLint, FlintConfiguration config) throws Exception{
        return FlintDriver.run(fileToLint, config);
    }

    /**
     * Outputs the result of the Flint check.
     *
     * @require result should not be null
     * @param result - The collection of failure results from Driver
     * @throws NullPointerException - When result is null
     * */
    private static void resultOutput(Collection<LintFailure> result) throws NullPointerException{
        if (result == null) {
            throw new NullPointerException();
        }

        Iterator<LintFailure> failureIt = result.iterator();

        while (failureIt.hasNext()) {
            LintFailure failure = failureIt.next();

            System.out.println("Line " + failure.getLineStart() + ":" + failure.getLineEnd() + ", "
                                + "Col " + failure.getColStart() + ":" + failure.getColEnd() + "\n"
                                + "Message: " + failure.getMessage());
        }
    }

}
