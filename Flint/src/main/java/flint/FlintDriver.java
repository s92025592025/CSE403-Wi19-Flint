package flint;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Collection;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

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
   * @throws IllegalArgumentException - If fileToLint does not compile
   * @throws ParseProblemException    - If fileToLint could not be successfully
   *                                  parsed
   * @throws Exception                - If config.runChecks() throws an exception
   */
  public static Collection<LintFailure> run(String fileToLint, FlintConfiguration config)
      throws IllegalArgumentException, ParseProblemException, Exception {
    if (doesCompile(fileToLint)) {
      RandomAccessFile inputFile = new RandomAccessFile(fileToLint, "r");
      Collection<LintFailure> result = config.runChecks(JavaParser.parse(new File(fileToLint)), inputFile);
      inputFile.close();
      return result;
    } else {
      throw new IllegalArgumentException("Filepath provided does not compile!");
    }
  }

  /**
   * This function takes a path to a file and checks that it will compile successfully.
   * If the file does compile then it returns true, if not it returns false.
   * Requires the given file path is vaild and is a java file.
   *
   * @param filePath - path to a java file that will be checked for style errors
   * @return true if the given file compiles successfully, false if the given file fails to compile
   */
  public static boolean doesCompile(String filePath) {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    int res = compiler.run(null, null, null, filePath);
    if (res == 0) {
        return true;
    }
    return false;
  }
}