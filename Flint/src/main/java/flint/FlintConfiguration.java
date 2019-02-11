package flint;

import java.io.RandomAccessFile;
import java.util.Collection;

import com.github.javaparser.ast.CompilationUnit;

public abstract class FlintConfiguration {
  /**
   * Defines how to run whatever rules it chooses to run (in what order, under
   * what conditions, etc), passes the CompilationUnit astRoot and
   * RandomAccessFile inputFile to each rule, runs checks, and returns the
   * collection of consequent failures to the caller.
   *
   * @param astRoot   - the root of the abstract syntax tree of the input file
   * @param inputFile - the input file to be run checks on
   * @return Returns a collection of consequent LintFailure
   */
  public abstract Collection<LintFailure> runChecks(CompilationUnit astRoot, RandomAccessFile inputFile)
      throws Exception;
}