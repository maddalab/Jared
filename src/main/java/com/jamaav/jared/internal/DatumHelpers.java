package com.jamaav.jared.internal;

import com.jamaav.jared.Ql2.Datum;
import com.jamaav.jared.Ql2.Datum.DatumType;
import com.jamaav.jared.Ql2.Term;
import com.jamaav.jared.Ql2.Term.TermType;

class DatumHelpers {
  private DatumHelpers() {

  }

  static Term newStringDatum(String database) {
    return Term
        .newBuilder()
        .setType(TermType.DATUM)
        .setDatum(Datum.newBuilder().setType(DatumType.R_STR).setRStr(database))
        .build();
  }
}
