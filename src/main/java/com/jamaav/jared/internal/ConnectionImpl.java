package com.jamaav.jared.internal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

import com.google.protobuf.AbstractMessage;
import com.jamaav.jared.ConnectionClosedException;
import com.jamaav.jared.ConnectionException;
import com.jamaav.jared.InvalidAuthorizationException;
import com.jamaav.jared.InvalidResponseException;
import com.jamaav.jared.Ql2.Query;
import com.jamaav.jared.Ql2.Response;
import com.jamaav.jared.Ql2.Response.ResponseType;
import com.jamaav.jared.Ql2.VersionDummy;
import com.jamaav.jared.QueryException;
import com.jamaav.jared.Statement;

public class ConnectionImpl implements Connection {
  private static final String SUCCESS = "SUCCESS";
  private final Socket socket;
  private final AtomicLong token = new AtomicLong(0);

  public ConnectionImpl(InetSocketAddress addr, String authorization)
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
    OutputStream out = new DataOutputStream(new BufferedOutputStream(
        socket.getOutputStream()));
    out.write(buffer.array());
    out.flush();
  }

  @Override
  public Statement createStatement() throws ConnectionClosedException {
    if (socket.isClosed()) {
      throw new ConnectionClosedException(
          "Cannot create statement on closed connection");
    }

    return new StatementImpl(this);
  }

  @Override
  public void close() {
    safeCloseConnection();
  }

  @Override
  public void finalize() {
    // if we close a connection here, it is bad,
    // someone leaked a socket connection
    safeCloseConnection();
  }

  private void checkResponse(Query query, Response response)
      throws QueryException {
    if (response.getToken() != query.getToken()) {
      throw new QueryException("Tokens on response and query differ.");
    }
    checkErrorCodes(response);
  }

  private void checkConnection() throws ConnectionClosedException {
    if (socket.isClosed()) {
      throw new ConnectionClosedException(
          "Cannot execute query on a closed connection.");
    }
  }

  @Override
  public void executeUpdate(Query query) throws ConnectionException,
      QueryException {
    checkConnection();

    send(query);
    Response response = recv();
    checkResponse(query, response);

    assert response.getType() == ResponseType.SUCCESS_ATOM;
  }

  @Override
  public Response execute(Query query) throws ConnectionException,
      QueryException {
    checkConnection();
    send(query);
    Response response = recv();
    checkResponse(query, response);
    return response;
  }

  private void checkErrorCodes(Response response) throws QueryException {
    ResponseType type = response.getType();
    switch (type) {
    case CLIENT_ERROR:
      throw new QueryClientException(response.getResponse(0).getRStr(),
          response.getBacktrace());
    case COMPILE_ERROR:
      throw new QueryCompileException(response.getResponse(0).getRStr(),
          response.getBacktrace());
    case RUNTIME_ERROR:
      throw new QueryRuntimeException(response.getResponse(0).getRStr(),
          response.getBacktrace());
    default: // ignore -- cases involving success
    }
  }

  private Response recv() throws ConnectionException, InvalidResponseException {
    try {
      InputStream is = new DataInputStream(new BufferedInputStream(
          socket.getInputStream()));
      int length = readLength(is);
      byte[] bytes = new byte[length];
      if (is.read(bytes, 0, length) != length) {
        throw new InvalidResponseException(String.format(
            "Expecting payload of (%d bytes) in message", length));
      }
      return Response.parseFrom(bytes);
    } catch (IOException ex) {
      throw new ConnectionException("Failed to receive response from server",
          ex);
    }
  }

  protected int readLength(InputStream is) throws ConnectionException,
      InvalidResponseException {
    byte[] bytes = new byte[4];
    int len;
    try {
      len = is.read(bytes, 0, 4);
    } catch (IOException ex) {
      throw new ConnectionException(ex);
    }
    if (len < 4) {
      throw new InvalidResponseException(String.format(
          "Expecting length of message (4 bytes), received only %s", len));
    }
    ByteBuffer bb = ByteBuffer.allocate(4);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    bb.put(bytes);
    bb.rewind();
    return bb.getInt();
  }

  private void send(AbstractMessage query) throws ConnectionException {
    try {
      OutputStream os = new DataOutputStream(new BufferedOutputStream(
          socket.getOutputStream()));
      int size = query.getSerializedSize();
      ByteBuffer bb = ByteBuffer.allocate(4);
      bb.order(ByteOrder.LITTLE_ENDIAN);
      bb.putInt(size);
      os.write(bb.array());
      query.writeTo(os);
      os.flush();
    } catch (IOException ex) {
      throw new ConnectionException("Failed to send query to server", ex);
    }
  }

  @Override
  public long nextToken() {
    return token.incrementAndGet();
  }
}
