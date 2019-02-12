package com.github.s92025592025.flint.rules;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashSet;

import com.github.s92025592025.flint.LintFailure;

public class ConsecutiveEmptyLineLimitRule {
  public static Collection<LintFailure> run(RandomAccessFile inputFile, int lineLimit) throws IOException {
    Collection<LintFailure> result = new HashSet<>();
    int lineNum = 1;
    int prevLineLength = 0;
    int consecutive = 0;
    String line;
    while ((line = inputFile.readLine()) != null) {
      if (line.trim().length() == 0) {
        consecutive++;
      } else {
        if (consecutive > lineLimit) {
          result.add(new LintFailure(lineNum - consecutive, lineNum - 1, 1, prevLineLength + 1,
              "There are over " + lineLimit + " consecutive empty lines."));
        }
        consecutive = 0;
      }
      lineNum++;
      prevLineLength = line.length();
    }

    inputFile.seek(0);
    return result;
  }
}