package flint;

import java.io.File;
import java.util.Collection;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
  public static void main(String[] args) {
    if (args.length <= 0) {
    // if no flags were passed in
      System.out.println("Missing required flags, run \"flint -usage\" for details.");

      System.exit(0);
    }

    FlintConfiguration config = null;
    Collection<LintFailure> result = null;
    Map<String, String> flags = new HashMap<String, String>();

    // setup flags
    flags.put("-config", null);
    flags.put("-file-path", null);

    int flag_index = 0;

    while (flag_index < args.length) {
      if (flags.containsKey(args[flag_index])) {
        flags.put(args[flag_index], args[flag_index + 1]);

        flag_index += 2;
      } else {
        if (args[flag_index].equals("-usage")) {
          System.out.println("Flint -config <Config File Path> -file-path <List of File Paths>");
        } else {
          System.out.println("Invalid flag \"" + args[flag_index] +
                  "\", check doc or run “flint -usage” for details.");
        }

        return; // end function if user called -usage or invalid flag
      }
    }

    // check if the all the required flags are entered.
    for (String essentialFlags : flags.keySet()) {
      if (flags.get(essentialFlags) == null) {
        System.out.println("Missing required flags, run “flint -usage” for details.");

        return; // end function if user did not fulfill required flags
      }
    }

    // check if the flag input is given correctly
    try {
      // check config file first
      config = configInit(flags.get("-config"));
    } catch (FileNotFoundException e) {
      System.out.println("config file not exist");
    } catch (Exception e) {
      System.out.println("Died from unexpected");
    } finally {
      System.exit(0);
    }

    // check if the file to be lint is a java file
    if (!isJava(flags.get("-file-path"))) {
      System.out.println(flags.get("-file-path") + " is not a valid Java file.");

      System.exit(0);
    }

    // if all input matches requirement, start linting
    try {
      result = run(flags.get("-file-path"), config);
    } catch (Exception e) {
      // not sure what kind of exceptions will happen
    } finally {
      System.exit(0);
    }

    resultOutput(result);
  }

  /**
  * This function initializes and returns the FlintConfiguration class.
  *
  * @param configPath - The path that leads to the FlintConfiguration subclass file.
  * @return Returns the initialized FlintConfiguration subclass if passed in a valid path.
  * @throws FileNotFoundException - If configPath did not lead to a Java file.
  * @throws IllegalArgumentException - If the passed in configPath is not a FlintConfiguration subclass.
  * */
  public static FlintConfiguration configInit(String configPath) throws Exception {
    if (!isJava(configPath)) {
      throw new FileNotFoundException();
    }

    return null;
  }

  /**
  * This function checks if filePath leads to an existing java file.
  *
  * @param filePath - The file path to check.
  * @return Returns the true if the file exists and is a java file. Otherwise false.
  * */
  public static boolean isJava(String filePath) {
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
  public static Collection<LintFailure> run(String fileToLint, FlintConfiguration config) throws Exception{
    return FlintDriver.run(fileToLint, config);
  }

  /**
  * Outputs the result of the Flint check.
  *
  * @require result should not be null
  * @param result - The collection of failure results from Driver
  * @throws NullPointerException - When result is null
  * */
  public static void resultOutput(Collection<LintFailure> result) throws NullPointerException{
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
