package unit;

import flint.CLIAdapter;
import flint.FlintConfiguration;
import flint.LintFailure;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class CLIAdapterTest {
  private static ByteArrayOutputStream CLI_OUTPUT;

  @Before
  public void setup() {
    CLI_OUTPUT = new ByteArrayOutputStream();
    System.setOut(new PrintStream(CLI_OUTPUT));
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
                  CLI_OUTPUT.toString().trim());

     // Commenting this out since we do not have a unified testing platform,
     // \r\n and \n depends on the system and will cause false negative testing
     // result
    result.add(new LintFailure(123, 125, 64, 78, "Test 2 Error"));

    CLI_OUTPUT.reset();
    CLIAdapter.resultOutput(result);
    assertEquals("Line 0:1, Col 23:45\n"
                    + "Message: Test 1 Error\n"
                    + "Line 123:125, Col 64:78\n"
                    + "Message: Test 2 Error",
                  CLI_OUTPUT.toString().trim());

  }

  @Test
  public void configInitDirSuccessTest() throws Exception {
    FlintConfiguration configObj = null;
    String configPath = "src/main/java/flint/";
    String className = "flint.testConfig";

    configObj = CLIAdapter.configInit(configPath, className);

    assertEquals("FlintConfiguration", configObj.getClass().getSuperclass().getSimpleName());
  }

  @Test
  public void configInitUseJarSuccessTest() throws Exception {
    FlintConfiguration configObj = null;
    String configPath = "src/main/java/flint/testConfig.jar";
    String className = "flint.testConfig";

    configObj = CLIAdapter.configInit(configPath, className);

    assertEquals("FlintConfiguration", configObj.getClass().getSuperclass().getSimpleName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void configInitUseDirWrongClass() throws Exception {
    FlintConfiguration configObj = null;
    String configPath = "src/main/java/flint/";
    String className = "flint.NotConfig";

    configObj = CLIAdapter.configInit(configPath, className);
  }

  @Test(expected = IllegalArgumentException.class)
  public void configInitUseJarWrongClass() throws Exception {
    FlintConfiguration configObj = null;
    String configPath = "src/main/java/flint/wrongConfigClass.jar";
    String className = "flint.NotConfig";

    configObj = CLIAdapter.configInit(configPath, className);
  }

  //@Test(expected = FileNotFoundException.class)
}