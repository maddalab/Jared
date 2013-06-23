package com.jamaav.jared;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseQueriesTest {

  @Before
  public void setUp() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Statement s = c.createStatement();
      s.createDatabase("superheroes");
    } finally {
      c.close();
    }
  }

  @After
  public void tearDown() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Statement s = c.createStatement();
      s.dropDatabase("superheroes");
    } finally {
      c.close();
    }
  }

  @Test
  public void testListDatabases() {

  }
}
