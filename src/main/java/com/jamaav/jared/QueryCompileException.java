package com.jamaav.jared;

import com.jamaav.jared.Ql2.Backtrace;

public class QueryCompileException extends QueryException {
  private static final long serialVersionUID = 3100163196615007180L;

  public QueryCompileException(String message, Backtrace backtrace) {
    super(message, backtrace);
  }

  public QueryCompileException(String message) {
    super(message);
  }
}
