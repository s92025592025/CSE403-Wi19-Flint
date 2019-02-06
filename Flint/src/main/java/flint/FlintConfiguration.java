package flint;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;

import com.github.javaparser.ast.CompilationUnit;

public abstract class FlintConfiguration {
  public Collection<LintFailure> runChecks(RandomAccessFile inputFile, CompilationUnit astRoot) throws IOException {
    System.out.println("Got here!");
    return null;
  }
}