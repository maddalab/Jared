package com.jamaav.jared;

import org.junit.Assert;
import org.junit.Test;

/**
 * Required an instance of rethink db server running on localhost at port 28015
 * with no authorization key
 */
public class ConnectionInitializationTest {

  @Test
  public void testConnectionInitialization() throws ConnectionException {
    DriverManager.getConnection("localhost", 28015);
  }

  @Test
  public void testConnectionInvalidAuthorization() throws ConnectionException {
    try {
      DriverManager.getConnection("localhost", 28015, "fake-key");
      Assert.fail("Exception an authorization exception");
    } catch (InvalidAuthorizationException ex) {
      // ok to ignore expect this exception, we expect it for this test
    }
  }
}