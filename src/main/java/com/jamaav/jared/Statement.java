package com.jamaav.jared;

public interface Statement {
  public abstract DatabaseStatement useDatabase(String database)
      throws ConnectionException, QueryException;

  public abstract DatabaseStatement createDatabase(String database)
      throws ConnectionException, QueryException;
  
  public abstract void dropDatabase(String database)
      throws ConnectionException, QueryException;
}
