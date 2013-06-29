package com.jamaav.jared;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TableQueriesTest {
  @Before
  public void setUp() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Rethink.r(c).createDatabase("superheroes").createTable("marvel");
    } finally {
      c.close();
    }
  }

  @After
  public void tearDown() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Rethink.r(c).database("superheroes").dropTable("marvel").drop();
    } finally {
      c.close();
    }
  }

  @Test
  public void testListTables() {

  }
}
