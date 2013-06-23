package com.jamaav.jared;

import com.jamaav.jared.Ql2.Backtrace;

public class QueryException extends JaredException {
  private static final long serialVersionUID = 925976766680022810L;
  private Backtrace backtrace;

  public QueryException(String message) {
    super(message);
  }

  public QueryException(String message, Backtrace backtrace) {
    super(message);
    this.backtrace = backtrace;
  }

  public Backtrace getBacktrace() {
    return backtrace;
  }
}
