package unit;

import flint.CLIAdapter;
import org.junit.Test;

import static org.junit.Assert.*;

public class CLIAdapterTest {

  @Test
  public void isJavaNonJavaFileTest() {
    assertFalse(CLIAdapter.isJava("definitely not java"));
    assertFalse(CLIAdapter.isJava("isJava.java.class"));
    assertFalse(CLIAdapter.isJava("isJava.jav"));
    assertFalse(CLIAdapter.isJava("isTXT.txt"));
  }

  @Test
  public void isJavaFileNotExist () {
    assertFalse(CLIAdapter.isJava("apple.java"));
  }

  @Test
  public void isJavaFileExist () {
    assertTrue(CLIAdapter.isJava("src/test/java/unit/CLIAdapterTest.java"));
  }
}