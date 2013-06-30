package com.jamaav.jared.db;

public interface ResultSet {

  public boolean next();

  public ResultRow get();

  public void close();
}
