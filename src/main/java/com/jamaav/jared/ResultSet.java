package com.jamaav.jared;

public interface ResultSet {
  
  public boolean next();
  
  public ResultRow get();

  public void close();
}
