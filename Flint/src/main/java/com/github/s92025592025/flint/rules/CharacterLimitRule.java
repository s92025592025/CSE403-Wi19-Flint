package com.github.s92025592025.flint.rules;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.HashSet;

import com.github.s92025592025.flint.LintFailure;

public class CharacterLimitRule {
  public static Collection<LintFailure> run(RandomAccessFile inputFile, int charLimit) throws IOException {
    Collection<LintFailure> result = new HashSet<>();
    int lineNum = 1;
    String line;
    while ((line = inputFile.readLine()) != null) {
      if (line.length() > charLimit) {
        result.add(new LintFailure(lineNum, lineNum, 1, line.length() + 1,
            "This line is over " + charLimit + " characters long."));
      }
      lineNum++;
    }

    inputFile.seek(0);
    return result;
  }
}