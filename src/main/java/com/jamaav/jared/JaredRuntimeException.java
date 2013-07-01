package com.jamaav.jared;

public class JaredRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 5523026395608944356L;

  public JaredRuntimeException() {
    super();
  }

  public JaredRuntimeException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public JaredRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public JaredRuntimeException(String message) {
    super(message);
  }

  public JaredRuntimeException(Throwable cause) {
    super(cause);
  }
}
