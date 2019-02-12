package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashMap;

import com.github.s92025592025.flint.LintFailure;
import com.github.s92025592025.flint.rules.CharacterLimitRule;

import org.junit.Test;

public class CharacterLimitRuleTest {
  private static final String FILE_PATH = "src/test/java/mocks/TestLintingFile.java";

  private static void checkOutputs(int charLimit, HashMap<LintFailure, Boolean> expectedOutput)
      throws FileNotFoundException, IOException {
    RandomAccessFile inputFile = new RandomAccessFile(FILE_PATH, "r");
    Collection<LintFailure> output = CharacterLimitRule.run(inputFile, charLimit);
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
  public void findsAllOver50() throws FileNotFoundException, IOException {
    HashMap<LintFailure, Boolean> expectedOutput = new HashMap<>();
    expectedOutput.put(new LintFailure(11, 11, 1, 106, ""), false);
    expectedOutput.put(new LintFailure(12, 12, 1, 55, ""), false);

    checkOutputs(50, expectedOutput);
  }

  @Test
  public void findsOver100() throws FileNotFoundException, IOException {
    HashMap<LintFailure, Boolean> expectedOutput = new HashMap<>();
    expectedOutput.put(new LintFailure(11, 11, 1, 106, ""), false);

    checkOutputs(100, expectedOutput);
  }
}