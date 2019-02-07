package flint;

import java.io.RandomAccessFile;
import java.util.Collection;

import com.github.javaparser.ast.CompilationUnit;

public abstract class FlintConfiguration {
  public abstract Collection<LintFailure> runChecks(RandomAccessFile inputFile, CompilationUnit astRoot)
      throws Exception;
}