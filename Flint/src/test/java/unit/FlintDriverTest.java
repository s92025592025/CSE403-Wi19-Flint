package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.github.javaparser.ast.CompilationUnit;

import org.junit.Test;

import com.github.s92025592025.flint.FlintDriver;
import mocks.TestConfig;

public class FlintDriverTest {
  // @Test(expected = IllegalArgumentException.class)
  // public void throwsExceptionForUncompilableFile() throws Exception {
  //   FlintDriver.run("invalid-file.java", new TestConfig());
  // }

  @Test
  public void callsRunChecks() throws Exception {
    TestConfig tc = new TestConfig();
    FlintDriver.run("src/main/java/com/github/s92025592025/flint/FlintDriver.java", tc);
    assertEquals(tc.getCallCount(), 1);
  }

  @Test
  public void callsRunChecksWithRandomAccessFile() throws Exception {
    String filepath = "src/main/java/com/github/s92025592025/flint/FlintDriver.java";
    TestConfig tc = new TestConfig();
    FlintDriver.run(filepath, tc);

    assertEquals(tc.getFileClass(), RandomAccessFile.class);

    RandomAccessFile f = new RandomAccessFile(filepath, "r");
    assertEquals(tc.getFileLength(), f.length());
    f.close();
  }

  @Test(expected = IOException.class)
  public void callsRunChecksWithReadOnlyRandomAccessFile() throws Exception {
    String filepath = "src/main/java/com/github/s92025592025/flint/FlintDriver.java";
    TestConfig tc = new TestConfig();
    tc.setShouldWriteToFile(true);
    FlintDriver.run(filepath, tc);
  }

  @Test(expected = IOException.class)
  public void closesRandomAccessFile() throws Exception {
    String filepath = "src/main/java/com/github/s92025592025/flint/FlintDriver.java";
    TestConfig tc = new TestConfig();
    FlintDriver.run(filepath, tc);

    tc.readFromFile();
  }

  @Test
  public void callsRunChecksWithAST() throws Exception {
    String filepath = "src/main/java/com/github/s92025592025/flint/FlintDriver.java";
    TestConfig tc = new TestConfig();
    FlintDriver.run(filepath, tc);

    assertEquals(tc.getAstClass(), CompilationUnit.class);
    assertEquals(tc.getClassName(), "FlintDriver");
  }

  @Test
  public void compilesValidJavaFile() {
    String filepath = "src/main/java/com/github/s92025592025/flint/FlintDriver.java";
    assertTrue(FlintDriver.doesCompile(filepath));
  }

  @Test
  public void doesNotCompileInvalidJavaFile() {
    String filepath = "src/test/java/mocks/Invalid.java";
    assertFalse(FlintDriver.doesCompile(filepath));
  }
}