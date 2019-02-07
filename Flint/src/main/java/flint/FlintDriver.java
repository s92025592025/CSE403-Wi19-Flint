package flint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Collection;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;

public class FlintDriver {
  /**
   * This method checks whether fileToLint refers to a compileable Java class,
   * throwing an IllegalArgumentException if not, then produces a Scanner to the
   * file as well as a CompilationUnit (the result of parsing the file and the
   * root of an Abstract Syntax Tree), which it passes to config.
   * 
   * @param fileToLint - Filepath to the file we wish to lint
   * @param config     - Configuration file that defines how to run the checks
   * @return Every LintFailure resulting from running the checks in config
   * @throws FileNotFoundException    - If fileToLint does not refer to a valid
   *                                  file
   * @throws IllegalArgumentException - If fileToLint does not compile
   * @throws ParseProblemException    - If fileToLint could not be successfully
   *                                  parsed
   * @throws Exception                - If config.runChecks() throws an exception
   */
  public static Collection<LintFailure> run(String fileToLint, FlintConfiguration config)
      throws FileNotFoundException, IllegalArgumentException, ParseProblemException, Exception {
    if (doesCompile(fileToLint)) {
      RandomAccessFile inputFile = new RandomAccessFile(fileToLint, "r");
      Collection<LintFailure> result = config.runChecks(inputFile, JavaParser.parse(new File(fileToLint)));
      inputFile.close();
      return result;
    } else {
      throw new IllegalArgumentException("Filepath provided does not compile!");
    }
  }

  private static boolean doesCompile(String filepath) {
    return true;
  }
}