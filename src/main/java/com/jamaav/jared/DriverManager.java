package com.jamaav.jared;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.jamaav.jared.internal.ConnectionImpl;

public final class DriverManager {
  private DriverManager() {
  }

  public static Connection getConnection(String hostname, int port)
      throws ConnectionException {
    return getConnection(new InetSocketAddress(hostname, port), "");
  }

  public static Connection getConnection(String hostname, int port,
      String authorization) throws ConnectionException {
    return getConnection(new InetSocketAddress(hostname, port), authorization);
  }

  public static Connection getConnection(InetAddress addr, int port)
      throws ConnectionException {
    return getConnection(new InetSocketAddress(addr, port), "");
  }

  public static Connection getConnection(InetAddress addr, int port,
      String authorization) throws ConnectionException {
    return getConnection(new InetSocketAddress(addr, port), authorization);
  }

  public static Connection getConnection(InetSocketAddress addr)
      throws ConnectionException {
    return getConnection(addr, "");
  }

  public static Connection getConnection(InetSocketAddress addr,
      String authorization) throws ConnectionException {
    return new ConnectionImpl(addr, authorization);
  }
}
