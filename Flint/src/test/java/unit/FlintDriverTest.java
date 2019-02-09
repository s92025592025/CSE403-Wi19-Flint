package unit;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.github.javaparser.ast.CompilationUnit;

import org.junit.Test;

import flint.FlintDriver;
import mocks.TestConfig;

public class FlintDriverTest {
  @Test(expected = FileNotFoundException.class)
  public void throwsExceptionForNonexistentFile() throws Exception {
    FlintDriver.run("invalid-file.java", new TestConfig());
  }

  @Test
  public void callsRunChecks() throws Exception {
    TestConfig tc = new TestConfig();
    FlintDriver.run("src/main/java/flint/FlintDriver.java", tc);
    assertEquals(tc.getCallCount(), 1);
  }

  @Test
  public void callsRunChecksWithRandomAccessFile() throws Exception {
    String filepath = "src/main/java/flint/FlintDriver.java";
    TestConfig tc = new TestConfig();
    FlintDriver.run(filepath, tc);

    assertEquals(tc.getFileClass(), RandomAccessFile.class);

    RandomAccessFile f = new RandomAccessFile(filepath, "r");
    assertEquals(tc.getFileLength(), f.length());
    f.close();
  }

  @Test(expected = IOException.class)
  public void callsRunChecksWithReadOnlyRandomAccessFile() throws Exception {
    String filepath = "src/main/java/flint/FlintDriver.java";
    TestConfig tc = new TestConfig();
    tc.setShouldWriteToFile(true);
    FlintDriver.run(filepath, tc);
  }

  @Test(expected = IOException.class)
  public void closesRandomAccessFile() throws Exception {
    String filepath = "src/main/java/flint/FlintDriver.java";
    TestConfig tc = new TestConfig();
    FlintDriver.run(filepath, tc);

    tc.readFromFile();
  }

  @Test
  public void callsRunChecksWithAST() throws Exception {
    String filepath = "src/main/java/flint/FlintDriver.java";
    TestConfig tc = new TestConfig();
    FlintDriver.run(filepath, tc);

    assertEquals(tc.getAstClass(), CompilationUnit.class);
    assertEquals(tc.getClassName(), "FlintDriver");
  }

  @Test
  public void compilesJavaFile() {

  }
}