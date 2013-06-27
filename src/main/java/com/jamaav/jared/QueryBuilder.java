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
    builder.setType(TermType.DB_DROP)
        .addArgs(Converters.asTermWithDatum(database)).build();
    return this;
  }

  public Term build() {
    return builder.build();
  }

  public QueryBuilder listDatabases() {
    builder.setType(TermType.DB_LIST);
    return this;
  }
}
