package flint;

import java.util.Collection;
import java.io.RandomAccessFile;
import java.io.IOException;

import com.github.javaparser.ast.CompilationUnit;

public abstract class FlintRule {

  /**
  * Runs this FlintRule instance and returns consequent LintFailures to the caller.
  * 
  * @param astRoot - the root of the abstract syntax tree of the input file
  * @param inputFile - the input file to be run rules on
  * @return Returns a collection of the consequent LintFailures of this FlintRule
  * */
  public static Collection<LintFailure> run(CompilationUnit astRoot, RandomAccessFile inputFile) {
    System.out.println("static method can't be abstract.");
    return null;
  }
}