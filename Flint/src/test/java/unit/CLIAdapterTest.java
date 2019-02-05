package unit;

import flint.CLIAdapter;
import flint.LintFailure;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class CLIAdapterTest {
  private static ByteArrayOutputStream CLI_OUTPUT;

  @Before
  public void setup() {
    CLI_OUTPUT = new ByteArrayOutputStream();
    System.setOut(System.out);
  }

  @Test
  public void isJavaNonJavaFileTest() {
    assertFalse(CLIAdapter.isJava("definitely not java"));
    assertFalse(CLIAdapter.isJava("isJava.java.class"));
    assertFalse(CLIAdapter.isJava("isJava.jav"));
    assertFalse(CLIAdapter.isJava("isTXT.txt"));
  }

  @Test
  public void isJavaFileNotExistTest () {
    assertFalse(CLIAdapter.isJava("apple.java"));
  }

  @Test
  public void isJavaFileExistTest () {
    assertTrue(CLIAdapter.isJava("src/test/java/unit/CLIAdapterTest.java"));
  }

  @Test(expected = NullPointerException.class)
  public void resultOutputNullResultTest () {
    CLIAdapter.resultOutput(null);
  }

  @Test
  public void resultOutputNoResultTest () {
    List<LintFailure> result = new ArrayList<LintFailure>();

    assertEquals(0, result.size());

    CLIAdapter.resultOutput(result);
    assertEquals("", CLI_OUTPUT.toString());
  }

  @Test
  public void resultOutputHasResultTest () {
    List<LintFailure> result = new ArrayList<LintFailure>();

    result.add(new LintFailure(0, 1, 23, 45, "Test 1 Error"));

    CLIAdapter.resultOutput(result);
    assertEquals("Line 0:1, Col 23:45\n"
                  + "Message: Test 1 Error",
                  CLI_OUTPUT.toString());
  }
}