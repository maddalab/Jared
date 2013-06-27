package com.jamaav.jared;

import com.jamaav.jared.Ql2.Term;

public class Rethink {
  private final Connection connection;

  private Rethink(Connection connection) {
    this.connection = connection;
  }

  public static Rethink r(Connection connection) {
    return new Rethink(connection);
  }

  public String[] listDatabases() throws ConnectionException, QueryException {
    QueryBuilder qb = new QueryBuilder();
    Term q = qb.listDatabases().build();
    Statement statement = connection.createStatement();
    ResultSet rs = statement.execute(q);
    for (ResultRow row : rs) {
      return row.getStringArray();
    }
    throw new RuntimeException();
  }

  public void createDatabase(String database) throws ConnectionException,
      QueryException {
    Statement s = connection.createStatement();
    QueryBuilder qb = new QueryBuilder();
    Term query = qb.createDatabase(database).build();
    s.executeUpdate(query);
  }

  public void dropDatabase(String database) throws ConnectionException,
      QueryException {
    Statement s = connection.createStatement();
    QueryBuilder qb = new QueryBuilder();
    Term query = qb.dropDatabase(database).build();
    s.executeUpdate(query);

  }
}
