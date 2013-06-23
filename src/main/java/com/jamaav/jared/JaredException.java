package com.jamaav.jared;

public class JaredException extends Exception {

  private static final long serialVersionUID = -4509833043698897178L;

  public JaredException() {
    super();
  }

  public JaredException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public JaredException(String message, Throwable cause) {
    super(message, cause);
  }

  public JaredException(String message) {
    super(message);
  }

  public JaredException(Throwable cause) {
    super(cause);
  }
}
