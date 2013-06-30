package com.jamaav.jared;

import org.junit.Assert;
import org.junit.Test;

import com.jamaav.jared.db.Connection;
import com.jamaav.jared.db.DriverManager;

/**
 * Required an instance of rethink db server running on localhost at port 28015
 * with no authorization key
 */
public class ConnectionInitializationTest {

  @Test
  public void testBasicConnection() throws ConnectionException {
    Connection c = DriverManager.getConnection("localhost", 28015);
    c.close();
  }

  @Test
  public void testConnectionInvalidAuthorization() throws ConnectionException {
    try {
      Connection c = DriverManager
          .getConnection("localhost", 28015, "fake-key");
      c.close();
      Assert.fail("Exception an authorization exception");
    } catch (InvalidAuthorizationException ex) {
      // ok to ignore expect this exception, we expect it for this test
    }
  }
}
