package com.jamaav.jared.db;

import com.jamaav.jared.ConnectionClosedException;

public interface Connection {
  public void close();

  public Statement createStatement() throws ConnectionClosedException;
}
