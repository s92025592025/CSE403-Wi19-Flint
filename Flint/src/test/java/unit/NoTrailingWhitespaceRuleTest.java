package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashMap;

import com.github.s92025592025.flint.LintFailure;
import com.github.s92025592025.flint.rules.NoTrailingWhitespaceRule;

import org.junit.Test;

public class NoTrailingWhitespaceRuleTest {
  private static final String FILE_PATH = "src/test/java/mocks/TestLintingFile.java";

  private static void checkOutputs(HashMap<LintFailure, Boolean> expectedOutput)
      throws FileNotFoundException, IOException {
    RandomAccessFile inputFile = new RandomAccessFile(FILE_PATH, "r");
    Collection<LintFailure> output = NoTrailingWhitespaceRule.run(inputFile);
    inputFile.close();

    assertTrue(output.size() == expectedOutput.size());

    for (LintFailure failure : output) {
      for (LintFailure expected : expectedOutput.keySet()) {
        if (failure.failurePositionEquals(expected)) {
          assertFalse(expectedOutput.get(expected));
          expectedOutput.put(expected, true);
        }
      }
    }

    for (LintFailure expected : expectedOutput.keySet()) {
      assertTrue(expectedOutput.get(expected));
    }
  }

  @Test
  public void findsAllFailures() throws FileNotFoundException, IOException {
    HashMap<LintFailure, Boolean> expectedOutput = new HashMap<>();
    expectedOutput.put(new LintFailure(1, 1, 15, 17, ""), false);
    expectedOutput.put(new LintFailure(7, 7, 31, 32, ""), false);
    expectedOutput.put(new LintFailure(10, 10, 29, 33, ""), false);
    expectedOutput.put(new LintFailure(15, 15, 1, 3, ""), false);

    checkOutputs(expectedOutput);
  }
}