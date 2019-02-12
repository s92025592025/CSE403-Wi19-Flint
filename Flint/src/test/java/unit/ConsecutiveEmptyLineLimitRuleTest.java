package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashMap;

import com.github.s92025592025.flint.LintFailure;
import com.github.s92025592025.flint.rules.ConsecutiveEmptyLineLimitRule;

import org.junit.Test;

public class ConsecutiveEmptyLineLimitRuleTest {
  private static final String FILE_PATH = "src/test/java/mocks/TestLintingFile.java";

  private static void checkOutputs(HashMap<LintFailure, Boolean> expectedOutput, int lineLimit)
      throws FileNotFoundException, IOException {
    RandomAccessFile inputFile = new RandomAccessFile(FILE_PATH, "r");
    Collection<LintFailure> output = ConsecutiveEmptyLineLimitRule.run(inputFile, lineLimit);
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
  public void findsAllEmptyLines() throws FileNotFoundException, IOException {
    HashMap<LintFailure, Boolean> expectedOutput = new HashMap<>();
    expectedOutput.put(new LintFailure(2, 6, 1, 1, ""), false);
    expectedOutput.put(new LintFailure(8, 9, 1, 1, ""), false);
    expectedOutput.put(new LintFailure(14, 16, 1, 1, ""), false);

    checkOutputs(expectedOutput, 0);
  }

  @Test
  public void findsAllEmptyLinesOf3Plus() throws FileNotFoundException, IOException {
    HashMap<LintFailure, Boolean> expectedOutput = new HashMap<>();
    expectedOutput.put(new LintFailure(2, 6, 1, 1, ""), false);
    expectedOutput.put(new LintFailure(14, 16, 1, 1, ""), false);

    checkOutputs(expectedOutput, 2);
  }

  @Test
  public void findsAllEmptyLinesOf4Plus() throws FileNotFoundException, IOException {
    HashMap<LintFailure, Boolean> expectedOutput = new HashMap<>();
    expectedOutput.put(new LintFailure(2, 6, 1, 1, ""), false);

    checkOutputs(expectedOutput, 3);
  }
}