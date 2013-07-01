package com.jamaav.jared;

public class NoDatabaseException extends JaredRuntimeException {
  private static final long serialVersionUID = 2919914123316739064L;

  public NoDatabaseException() {
    super();
  }

  public NoDatabaseException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public NoDatabaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoDatabaseException(String message) {
    super(message);
  }

  public NoDatabaseException(Throwable cause) {
    super(cause);
  }

}
