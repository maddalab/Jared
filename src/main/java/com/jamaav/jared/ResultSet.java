package com.jamaav.jared;

public interface ResultSet extends Iterable<ResultRow>{

  public abstract void close();

}
