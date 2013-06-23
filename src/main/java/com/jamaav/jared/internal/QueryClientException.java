package com.jamaav.jared.internal;

import com.jamaav.jared.Ql2.Backtrace;
import com.jamaav.jared.QueryException;

public class QueryClientException extends QueryException {

  private static final long serialVersionUID = -4835061281247403023L;

  public QueryClientException(String message) {
    super(message);
  }

  public QueryClientException(String message, Backtrace backtrace) {
    super(message, backtrace);
  }
}
