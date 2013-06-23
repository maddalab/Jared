package com.jamaav.jared;

public interface Statement {
  public DatabaseStatement useDatabase(String database)
      throws ConnectionException, QueryException;

  public DatabaseStatement createDatabase(String database)
      throws ConnectionException, QueryException;

  public void dropDatabase(String database) throws ConnectionException,
      QueryException;

  public void listDatabases() throws ConnectionException, QueryException;
}
