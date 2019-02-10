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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
                    + "Message: Test 1 Error\n",
                  CLI_OUTPUT.toString().replace("\r", ""));
    
    result.add(new LintFailure(123, 125, 64, 78, "Test 2 Error"));

    CLI_OUTPUT.reset();
    CLIAdapter.resultOutput(result);
    assertEquals("Line 0:1, Col 23:45\n"
                    + "Message: Test 1 Error\n"
                    + "Line 123:125, Col 64:78\n"
                    + "Message: Test 2 Error\n",
                  CLI_OUTPUT.toString().replace("\r", ""));

  }

  @Test
  public void configInitDirSuccessTest() throws Exception {
    FlintConfiguration configObj = null;
    String configPath = "src/test/java/mocks/";
    String className = "mocks.TestConfig";

    configObj = CLIAdapter.configInit(configPath, className);

    assertEquals("FlintConfiguration", configObj.getClass().getSuperclass().getSimpleName());
  }

  @Test
  public void configInitUseJarSuccessTest() throws Exception {
    FlintConfiguration configObj = null;
    String configPath = "src/test/java/mocks/TestConfig.jar";
    String className = "mocks.TestConfig";

    configObj = CLIAdapter.configInit(configPath, className);

    assertEquals("FlintConfiguration", configObj.getClass().getSuperclass().getSimpleName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void configInitUseDirWrongClassTest() throws Exception {
    String configPath = "src/test/java/mocks/";
    String className = "mocks.NotConfig";

    CLIAdapter.configInit(configPath, className);
  }

  @Test(expected = IllegalArgumentException.class)
  public void configInitUseJarWrongClassTest() throws Exception {
    String configPath = "src/test/java/mocks/WrongConfigClass.jar";
    String className = "mocks.NotConfig";

    CLIAdapter.configInit(configPath, className);
  }

  @Test(expected = FileNotFoundException.class)
  public void configInitNonExistPathTest() throws Exception {
    String configPath = "definitely/not/a/path/WrongConfigClass.jar";
    String className = "flint.NotConfig";

    CLIAdapter.configInit(configPath, className);
  }
}