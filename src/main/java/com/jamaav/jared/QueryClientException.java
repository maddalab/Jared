package com.jamaav.jared;

import com.jamaav.jared.Ql2.Backtrace;

public class QueryClientException extends QueryException {

  private static final long serialVersionUID = -4835061281247403023L;

  public QueryClientException(String message) {
    super(message);
  }

  public QueryClientException(String message, Backtrace backtrace) {
    super(message, backtrace);
  }
}
