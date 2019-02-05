package flint;

import java.util.Collection;
import java.util.Scanner;

import com.github.javaparser.ast.CompilationUnit;

public abstract class FlintConfiguration {
  public Collection<LintFailure> runChecks(Scanner inputFile, CompilationUnit astRoot) {
    System.out.println("Got here!");
    return null;
  }
}