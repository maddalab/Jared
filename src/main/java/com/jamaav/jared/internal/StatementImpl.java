package com.jamaav.jared.internal;

import com.jamaav.jared.ConnectionException;
import com.jamaav.jared.Ql2.Query;
import com.jamaav.jared.Ql2.Query.QueryType;
import com.jamaav.jared.Ql2.Response;
import com.jamaav.jared.Ql2.Response.ResponseType;
import com.jamaav.jared.Ql2.Term;
import com.jamaav.jared.Ql2.Term.TermType;
import com.jamaav.jared.QueryException;
import com.jamaav.jared.ResultSet;
import com.jamaav.jared.Statement;
import com.jamaav.jared.util.Strings;

class StatementImpl implements Statement {
  private final Connection connection;

  StatementImpl(Connection connection) {
    this.connection = connection;
  }

  private ResultSet parseResponse(Response response, Term term, long token) {
    ResponseType type = response.getType();
    switch (type) {
    case SUCCESS_ATOM:
      if (response.getResponseCount() == 0) {
        return new EmptyResultResultSet(term, token);
      }
      return new SingleResultResultSet(term, token, response.getResponse(0));
    case SUCCESS_PARTIAL:
      return new ResultSetImpl(term, token, response.getResponseList(), false);
    default:
      return new ResultSetImpl(term, token, response.getResponseList(), true);
    }
  }

  private Query.Builder newQueryBuilder(String database, long token, Term term) {
    return Query
        .newBuilder()
        .addGlobalOptargs(
            Query.AssocPair
                .newBuilder()
                .setKey("db")
                .setVal(
                    Term.newBuilder().setType(TermType.DB)
                        .addArgs(Converters.asTermWithDatum(database))))
        .setToken(token).setType(QueryType.START).setQuery(term);
  }

  private Query.Builder newQueryBuilder(long token, Term term) {
    return Query.newBuilder().setToken(token).setType(QueryType.START)
        .setQuery(term);
  }

  @Override
  public void executeUpdate(Term term) throws ConnectionException,
      QueryException {
    long token = connection.nextToken();
    Query query = newQueryBuilder(token, term).build();

    connection.executeUpdate(query);
  }

  @Override
  public ResultSet execute(Term term) throws ConnectionException,
      QueryException {
    long token = connection.nextToken();
    Query query = newQueryBuilder(token, term).build();
    Response response = connection.execute(query);
    return parseResponse(response, term, token);
  }

  @Override
  public ResultSet execute(Term term, String database)
      throws ConnectionException, QueryException {
    if (Strings.isEmpty(database)) {
      throw new QueryException("Database is empty");
    }
    long token = connection.nextToken();
    Query query = newQueryBuilder(database, token, term).build();
    Response response = connection.execute(query);
    return parseResponse(response, term, token);
  }

  @Override
  public void executeUpdate(Term term, String database)
      throws ConnectionException, QueryException {
    if (Strings.isEmpty(database)) {
      throw new QueryException("Database is empty");
    }
    long token = connection.nextToken();
    Query query = newQueryBuilder(database, token, term).build();
    connection.executeUpdate(query);
  }
}