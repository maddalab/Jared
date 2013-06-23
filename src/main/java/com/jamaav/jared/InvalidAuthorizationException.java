package com.jamaav.jared;

public class InvalidAuthorizationException extends ConnectionException {

  private static final long serialVersionUID = 3431567060435458220L;

  public InvalidAuthorizationException() {
    super();
  }

  public InvalidAuthorizationException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public InvalidAuthorizationException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidAuthorizationException(String message) {
    super(message);
  }

  public InvalidAuthorizationException(Throwable cause) {
    super(cause);
  }
}
