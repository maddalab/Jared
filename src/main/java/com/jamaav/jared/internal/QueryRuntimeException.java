package com.jamaav.jared.internal;

import com.jamaav.jared.Ql2.Backtrace;
import com.jamaav.jared.QueryException;

public class QueryRuntimeException extends QueryException {

  private static final long serialVersionUID = -5526814870261874487L;

  public QueryRuntimeException(String message, Backtrace backtrace) {
    super(message, backtrace);
  }

  public QueryRuntimeException(String message) {
    super(message);
  }

}
