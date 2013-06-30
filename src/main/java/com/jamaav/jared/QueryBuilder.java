package com.jamaav.jared;

import com.jamaav.jared.Ql2.Term;
import com.jamaav.jared.Ql2.Term.TermType;
import com.jamaav.jared.internal.Converters;

public final class QueryBuilder {
  private final Term.Builder builder;

  public QueryBuilder() {
    builder = Term.newBuilder();
  }

  public QueryBuilder createDatabase(String database) {
    builder.setType(TermType.DB_CREATE)
        .addArgs(Converters.asTermWithDatum(database)).build();
    return this;
  }

  public QueryBuilder dropDatabase(String database) {
    builder.setType(TermType.DB_DROP).addArgs(
        Converters.asTermWithDatum(database));
    return this;
  }

  public Term build() {
    return builder.build();
  }

  public QueryBuilder listDatabases() {
    builder.setType(TermType.DB_LIST);
    return this;
  }

  public QueryBuilder listTables() {
    builder.setType(TermType.TABLE_LIST);
    return this;
  }

  public QueryBuilder createTable(String table) {
    builder.setType(TermType.TABLE_CREATE).addArgs(
        Converters.asTermWithDatum(table));
    return this;
  }

  private void checkTerm() {
    if (!builder.hasType())
      throw new RuntimeException(
          "The type of query being executed must be specified prior to providing options");
  }

  public QueryBuilder withOptArg(String key, String value) {
    checkTerm();
    builder.addOptargs(Term.AssocPair.newBuilder().setKey(key)
        .setVal(Converters.asTermWithDatum(value)));
    return this;
  }

  public QueryBuilder withOptArg(String key, int value) {
    checkTerm();
    builder.addOptargs(Term.AssocPair.newBuilder().setKey(key)
        .setVal(Converters.asTermWithDatum(value)));
    return this;
  }

  public QueryBuilder dropTable(String table) {
    builder.setType(TermType.TABLE_DROP).addArgs(
        Converters.asTermWithDatum(table));
    return this;
  }
}
