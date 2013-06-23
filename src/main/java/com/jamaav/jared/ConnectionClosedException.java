package com.jamaav.jared;

public class ConnectionClosedException extends ConnectionException {
  private static final long serialVersionUID = 2319776154941155442L;

  public ConnectionClosedException() {
    super();
  }

  public ConnectionClosedException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ConnectionClosedException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConnectionClosedException(String message) {
    super(message);
  }

  public ConnectionClosedException(Throwable cause) {
    super(cause);
  }
}
