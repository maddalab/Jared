package com.jamaav.jared;

import com.jamaav.jared.Ql2.Term;
import com.jamaav.jared.db.Connection;
import com.jamaav.jared.db.QueryBuilder;
import com.jamaav.jared.db.ResultRow;
import com.jamaav.jared.db.ResultSet;
import com.jamaav.jared.db.Statement;
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

  public Rethink table(String table) {
    this.table = table;
    return this;
  }

  public String[] listDatabases() throws ConnectionException, QueryException {
    QueryBuilder qb = new QueryBuilder();
    Term q = qb.listDatabases().build();
    Statement statement = connection.createStatement();
    ResultSet rs = statement.execute(q);
    ResultRow row = rs.get();
    return row.getStringArray();
  }

  public String[] listTables() throws ConnectionException, QueryException {
    checkDatabase();
    QueryBuilder qb = new QueryBuilder();
    Term q = qb.listTables().build();
    Statement statement = connection.createStatement();
    ResultSet rs = statement.execute(q, database);
    ResultRow row = rs.get();
    return row.getStringArray();
  }

  public Rethink dropDatabase() throws ConnectionException, QueryException {
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
      throw new NoDatabaseException(
          "Attempt to execute database operation without specifying a database");
  }

  private void checkTable() {
    if (Strings.isEmpty(table))
      throw new NoTableException(
          "Attempt to execute table operation without specifying a table");
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

  public Rethink dropTable() throws ConnectionException, QueryException {
    checkTable();
    checkDatabase();
    Statement s = connection.createStatement();
    QueryBuilder qb = new QueryBuilder();
    Term query = qb.dropTable(table).build();
    s.executeUpdate(query, database);
    table(null);
    return this;
  }

  public Rethink dropIndex(String index) throws ConnectionException,
      QueryException {
    Statement s = connection.createStatement();
    QueryBuilder qb = new QueryBuilder();
    Term query = qb.dropIndex(table, index).build();
    s.executeUpdate(query, database);
    return this;
  }

  public Rethink createDatabase() throws ConnectionException, QueryException {
    Statement s = connection.createStatement();
    QueryBuilder qb = new QueryBuilder();
    Term query = qb.createDatabase(database).build();
    s.executeUpdate(query);
    database(database);
    return this;
  }

  public Rethink createIndex(String index) throws ConnectionException,
      QueryException {
    checkDatabase();
    checkTable();
    Statement s = connection.createStatement();
    QueryBuilder qb = new QueryBuilder();
    Term query = qb.createIndex(table, index).build();
    s.executeUpdate(query, database);
    return this;
  }

  public String[] listIndexes() throws ConnectionException, QueryException {
    checkTable();
    checkDatabase();
    QueryBuilder qb = new QueryBuilder();
    Term q = qb.listIndexes(table).build();
    Statement statement = connection.createStatement();
    ResultSet rs = statement.execute(q, database);
    ResultRow row = rs.get();
    return row.getStringArray();
  }
}
