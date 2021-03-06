package com.jamaav.jared;

public class ConnectionException extends JaredException {
  private static final long serialVersionUID = -8309457942906695101L;

  public ConnectionException() {
    super();
  }

  public ConnectionException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ConnectionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConnectionException(String message) {
    super(message);
  }

  public ConnectionException(Throwable cause) {
    super(cause);
  }
}
