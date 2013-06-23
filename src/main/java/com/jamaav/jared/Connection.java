package com.jamaav.jared;

public interface Connection {
  public void close();

  public Statement createStatement() throws ConnectionClosedException;
}
