package com.jamaav.jared;

import com.jamaav.jared.Ql2.Backtrace;

public class QueryRuntimeException extends QueryException {

  private static final long serialVersionUID = -5526814870261874487L;

  public QueryRuntimeException(String message, Backtrace backtrace) {
    super(message, backtrace);
  }

  public QueryRuntimeException(String message) {
    super(message);
  }

}
