package com.jamaav.jared;

public class NoTableException extends JaredRuntimeException {
  private static final long serialVersionUID = 8861915090039141919L;

  public NoTableException() {
    super();
  }

  public NoTableException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public NoTableException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoTableException(String message) {
    super(message);
  }

  public NoTableException(Throwable cause) {
    super(cause);
  }
}
