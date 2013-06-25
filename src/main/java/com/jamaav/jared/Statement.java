package com.jamaav.jared;

import com.jamaav.jared.Ql2.Term;

public interface Statement {

  public abstract void execute(Term query) throws ConnectionException,
      QueryException;

  public abstract void executeUpdate(Term query)
      throws ConnectionException, QueryException;
}
