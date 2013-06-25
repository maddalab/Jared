package com.jamaav.jared.internal;

import com.jamaav.jared.ConnectionException;
import com.jamaav.jared.Ql2.Term;
import com.jamaav.jared.QueryException;
import com.jamaav.jared.ResultSet;
import com.jamaav.jared.Statement;

class StatementImpl implements Statement {
  private final ConnectionImpl connection;

  StatementImpl(ConnectionImpl connection) {
    this.connection = connection;
  }

  @Override
  public void executeUpdate(Term query) throws ConnectionException,
      QueryException {
    connection.executeUpdate(query);
  }

  @Override
  public ResultSet execute(Term query) throws ConnectionException, QueryException {
    return connection.execute(query);
  }
}