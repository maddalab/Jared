package com.jamaav.jared;

import com.jamaav.jared.Ql2.Term;
import com.jamaav.jared.util.Strings;

public class Rethink {
  private final Connection connection;
  private String database;
  private String table;

  private Rethink(Connection connection) {
    this.connection = connection;
  }

  public static Rethink r(Connection connection) {
    return new Rethink(connection);
  }

  public Rethink database(String database) {
    this.database = database;
    return this;
  }

  public void table(String table) {
    this.table = table;
  }

  public String[] listDatabases() throws ConnectionException, QueryException {
    QueryBuilder qb = new QueryBuilder();
    Term q = qb.listDatabases().build();
    Statement statement = connection.createStatement();
    ResultSet rs = statement.execute(q);
    ResultRow row = rs.get();
    return row.getStringArray();
  }

  public Rethink drop() throws ConnectionException, QueryException {
    checkDatabase();
    Statement s = connection.createStatement();
    QueryBuilder qb = new QueryBuilder();
    Term query = qb.dropDatabase(database).build();
    s.executeUpdate(query);
    database(null);
    return this;
  }

  public Rethink createTable(String table) throws ConnectionException,
      QueryException {
    createTable(table, CreateTableOptions.NO_OPTIONS);
    return this;
  }

  private void checkDatabase() {
    if (Strings.isEmpty(database))
      throw new RuntimeException(
          "Attempt to execute table operation without specifying a database");
  }

  public void createTable(String table, CreateTableOptions options)
      throws ConnectionException, QueryException {
    checkDatabase();
    Statement s = connection.createStatement();
    QueryBuilder qb = new QueryBuilder();
    qb.createTable(table);
    if (Strings.isNotEmpty(options.getDatacenter()))
      qb.withOptArg("datacenter", options.getDatacenter());
    if (Strings.isNotEmpty(options.getPrimaryKey()))
      qb.withOptArg("primary_key", options.getPrimaryKey());
    if (options.getCacheSize() != -1)
      qb.withOptArg("cache_size", options.getCacheSize());
    qb.withOptArg("durability", options.getDurability().getValue());
    Term query = qb.build();
    s.executeUpdate(query, database);
    table(table);
  }

  public Rethink dropTable(String table) throws ConnectionException,
      QueryException {
    Statement s = connection.createStatement();
    QueryBuilder qb = new QueryBuilder();
    Term query = qb.dropTable(table).build();
    s.executeUpdate(query, database);
    table(null);
    return this;
  }

  public Rethink create() throws ConnectionException, QueryException {
    Statement s = connection.createStatement();
    QueryBuilder qb = new QueryBuilder();
    Term query = qb.createDatabase(database).build();
    s.executeUpdate(query);
    database(database);
    return this;
  }
}
