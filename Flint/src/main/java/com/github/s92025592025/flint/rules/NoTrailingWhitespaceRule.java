package com.github.s92025592025.flint.rules;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashSet;

import com.github.s92025592025.flint.LintFailure;

public class NoTrailingWhitespaceRule {
  public static Collection<LintFailure> run(RandomAccessFile inputFile) throws IOException {
    Collection<LintFailure> result = new HashSet<>();
    int lineNum = 1;
    String line;
    String trimmed;
    while ((line = inputFile.readLine()) != null) {
      trimmed = line.trim();
      if (trimmed.length() < line.length()) {
        result.add(new LintFailure(lineNum, lineNum, trimmed.length() + 1, line.length() + 1,
            "Please remove trailing whitespace."));
      }
      lineNum++;
    }

    inputFile.seek(0);
    return result;
  }
}