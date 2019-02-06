package flint;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;

import com.github.javaparser.ast.CompilationUnit;

public abstract class FlintConfiguration {
  CompilationUnit astRoot;
  RandomAccessFile inputFile;
  Collection<FlintRule> rules;
  Collection<LintFailure> failures;

  public FlintConfiguration(CompilationUnit astRoot, RandomAccessFile inputFile);

  /**
   * Defines how to run whatever rules it chooses to run (in what order, under what conditions, etc), 
   * passes the CompilationUnit astRoot and RandomAccessFile inputFile to each rule, 
   * runs checks, and returns the collection of consequent failures to the caller.
   *
   * @return Returns a collection of consequent LintFailure
   * */
  public abstract Collection<LintFailure> runChecks();
}