package com.jamaav.jared;

import com.jamaav.jared.Ql2.Term;

public interface Statement {

  public ResultSet execute(Term query) throws ConnectionException,
      QueryException;

  public void executeUpdate(Term query) throws ConnectionException,
      QueryException;

  public ResultSet execute(Term query, String database)
      throws ConnectionException, QueryException;

  public void executeUpdate(Term query, String database)
      throws ConnectionException, QueryException;
}
