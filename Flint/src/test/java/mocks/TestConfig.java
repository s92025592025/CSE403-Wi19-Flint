package mocks;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashSet;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import flint.FlintConfiguration;
import flint.LintFailure;

public class TestConfig extends FlintConfiguration {
  private boolean shouldWriteToFile = false;

  private int callCount = 0;
  private Class<?> fileClass;
  private long fileLength = 0;
  private Class<?> astClass;
  private String className;

  private RandomAccessFile file;

  public void setShouldWriteToFile(boolean value) {
    this.shouldWriteToFile = value;
  }

  
  public Collection<LintFailure> runChecks(RandomAccessFile inputFile, CompilationUnit astRoot) throws IOException {
    this.callCount++;
    this.file = inputFile;
    this.fileClass = inputFile.getClass();
    try {
      this.fileLength = inputFile.length();
    } catch (IOException ex) {
    }
    this.astClass = astRoot.getClass();
    this.className = astRoot.findFirst(ClassOrInterfaceDeclaration.class).get().getNameAsString();
    if (shouldWriteToFile) {
      inputFile.writeChars("hello world");
    }
    return new HashSet<LintFailure>();
  }
  
  public void readFromFile() throws IOException {
    this.file.seek(0);
    this.file.read();
  }

  public int getCallCount() {
    return callCount;
  }
  
  public Class<?> getFileClass() {
    return fileClass;
  }
  
  public long getFileLength() {
    return fileLength;
  }

  public Class<?> getAstClass() {
    return astClass;
  }
  
  public String getClassName() {
    return className;
  }
}