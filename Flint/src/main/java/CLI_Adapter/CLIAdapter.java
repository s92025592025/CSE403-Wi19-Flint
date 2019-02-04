package CLI_Adapter;

import java.util.Collection;
import java.io.FileNotFoundException;

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
     * @throws FileNotFoundException - If configPath did not lead to a file.
     * @throws IllegalArgumentException - If the passed in configPath is not a FlintConfiguration subclass.
     * */
    private static FlintConfiguration configInit(String configPath) throws Exception {
        throw new Exception("Not Yet Implemented");
    }

    /**
     * This function checks if filePath leads to an existing java file.
     *
     * @param filePath - The file path to check.
     * @return Returns the true if the file exists and is a java file. Otherwise false.
     * */
    private static boolean isJava(String filePath) throws Exception {
        throw new Exception("Not Yet Implemented");
    }

    /**
     * Starts the Driver to run Flint on fileToLint with config.
     *
     * @param fileToLint - The file path that leads to a valid and existing Java file.
     * @param config - The configuration to run on flieToLint.
     * */
    private static Collection<LintFailure> run(String fileToLint, FlintConfiguration config) throws Exception{
        throw new Exception("Not Yet Implemented");
    }

    /**
     * Outputs the result of the Flint check.
     *
     * @require result should not be null
     * @param result - The collection of failure results from Driver
     * @return Returns the output that the user should see on the command line, see the overview
     *         for detailed output format.
     * @throws NullPointerException - When result is null
     * // Otherexception that will be returned from run
     * */
    private static String resultOutput(Collection<LintFailure> result) throws Exception{
        throw new Exception("Not Yet Implemented");
    }

}
