package flint;

public class LintFailure {
  private int lineStart, lineEnd, colStart, colEnd;
  private String msg;

  public LintFailure(int lineStart, int lineEnd, int colStart, int colEnd, String msg) {
    this.lineStart = lineStart;
    this.lineEnd = lineEnd;
    this.colStart = colStart;
    this.colEnd = colEnd;
    this.msg = msg;
  }

  public int getLineStart() {
    return this.lineStart;
  }

  public int getLineEnd() {
    return this.lineEnd;
  }

  public int getColStart() {
    return this.colStart;
  }

  public int getColEnd() {
    return this.colEnd;
  }

  public String getMessage() {
    return this.msg;
  }
}