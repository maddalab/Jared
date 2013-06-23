package com.jamaav.jared;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.jamaav.jared.Ql2.VersionDummy;

class ConnectionImpl implements Connection {
  private static final String SUCCESS = "SUCCESS";
  private final Socket socket;

  ConnectionImpl(InetSocketAddress addr) throws IOException,
      ConnectionException {
    this(addr, "");
  }

  ConnectionImpl(InetSocketAddress addr, String authorization)
      throws ConnectionException {
    socket = new Socket();
    openConnection(addr);
    try {
      initConnection();
      writeAuthorization(authorization);
      finalizeHandshake();
    } catch (IOException ex) {
      closeConnectionWithException(new ConnectionException(ex));
    } catch (ConnectionException ex) {
      closeConnectionWithException(ex);
    }
  }

  private void closeConnectionWithException(ConnectionException primary)
      throws ConnectionException {
    safeCloseConnection();
    throw primary;
  }

  private void safeCloseConnection() {
    if (!socket.isClosed()) {
      try {
        socket.close();
      } catch (IOException ex) {
        // ignore this exception we will throw the primary exception passed in
      }
    }
  }

  private void openConnection(InetSocketAddress addr)
      throws ConnectionException {
    try {
      socket.connect(addr);
    } catch (IOException ex) {
      throw new ConnectionException(ex);
    }
  }

  private void initConnection() throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(4);
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    buffer.putInt(VersionDummy.Version.V0_2.getNumber());
    writeBuffer(buffer);
  }

  private void writeAuthorization(String authorization) throws IOException {
    byte[] authBytes = authorization.getBytes();
    ByteBuffer buffer = ByteBuffer.allocate(4 + authBytes.length);
    buffer.order(ByteOrder.LITTLE_ENDIAN);
    buffer.putInt(authBytes.length);
    buffer.put(authBytes);
    writeBuffer(buffer);
  }

  private void finalizeHandshake() throws IOException, ConnectionException {
    InputStream is = socket.getInputStream();
    ByteBuffer buffer = ByteBuffer.allocate(16);
    byte[] bytes = new byte[16];
    int i = is.read(bytes);
    while (i > 0) {
      buffer.put(bytes, 0, i);
      if (i < 16)
        break;
      else {
        ByteBuffer nextGenerationBuffer = ByteBuffer
            .allocate(buffer.capacity() * 2);
        nextGenerationBuffer.put(buffer);
        buffer = nextGenerationBuffer;
      }
      i = is.read(bytes);
    }
    String response = new String(buffer.array(), 0, buffer.position() - 1);
    if (!SUCCESS.equals(response)) {
      String msg = "Server declined authorization code";
      if (!"".equals(response))
        msg = "Server declined authorization code, responded with " + response;
      throw new InvalidAuthorizationException(msg);
    }
  }

  private void writeBuffer(ByteBuffer buffer) throws IOException {
    OutputStream out = socket.getOutputStream();
    out.write(buffer.array());
    out.flush();
  }

  @Override
  public void close() {
    safeCloseConnection();
  }
  
  @Override
  public void finalize() {
    // if we close a connection here, it is bad, someone leaked a socket connection
    safeCloseConnection();
  }
}
