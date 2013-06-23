package com.jamaav.jared.internal;

import com.jamaav.jared.ConnectionException;
import com.jamaav.jared.DatabaseStatement;
import com.jamaav.jared.Ql2.Term;
import com.jamaav.jared.Ql2.Term.TermType;
import com.jamaav.jared.QueryException;
import com.jamaav.jared.Statement;

class StatementImpl implements Statement {
  private final ConnectionImpl connection;

  StatementImpl(ConnectionImpl connection) {
    this.connection = connection;
  }

  @Override
  public DatabaseStatement useDatabase(String database)
      throws ConnectionException, QueryException {
    return new DatabaseStatementImpl(connection, database);
  }

  @Override
  public DatabaseStatement createDatabase(String database)
      throws ConnectionException, QueryException {
    return new DatabaseStatementImpl(connection, database, true);
  }

  @Override
  public void dropDatabase(String database) throws ConnectionException,
      QueryException {
    Term ddl = Term.newBuilder().setType(TermType.DB_DROP)
        .addArgs(Converters.asTermWithDatum(database)).build();
    connection.executeDDL(ddl);
  }
  
  @Override
  public void listDatabases() throws ConnectionException, QueryException {
    Term query = Term.newBuilder().setType(TermType.DB_DROP).build();
    connection.executeQuery(query);
  }
}


class DatabaseStatementImpl implements DatabaseStatement {
  private final ConnectionImpl connection;
  private final String database;

  public DatabaseStatementImpl(ConnectionImpl connection, String database)
      throws ConnectionException, QueryException {
    this(connection, database, false);
  }

  public DatabaseStatementImpl(ConnectionImpl connection, String database,
      boolean create) throws ConnectionException, QueryException {
    if (create) {
      Term ddl = Term.newBuilder().setType(TermType.DB_CREATE)
          .addArgs(Converters.asTermWithDatum(database)).build();
      connection.executeDDL(ddl);
    }
    this.connection = connection;
    this.database = database;
  }

  @Override
  public boolean createTable(String table) {
    return false;
  }
}