package com.jamaav.jared.internal;

import com.jamaav.jared.ConnectionException;
import com.jamaav.jared.Ql2.Query;
import com.jamaav.jared.Ql2.Response;
import com.jamaav.jared.QueryException;

public interface Connection extends com.jamaav.jared.Connection {

  public long nextToken();

  public Response execute(Query query) throws ConnectionException,
      QueryException;

  public void executeUpdate(Query query) throws ConnectionException,
      QueryException;
}
