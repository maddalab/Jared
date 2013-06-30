package com.jamaav.jared;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jamaav.jared.db.Connection;
import com.jamaav.jared.db.DriverManager;

public class TableQueriesTest {
  @Before
  public void setUp() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Rethink.r(c).database("superheroes").create().createTable("marvel");
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
  public void testListTables() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);
    try {
      String[] tables = Rethink.r(c).database("superheroes").listTables();
      Assert.assertEquals(1, tables.length);
      Assert.assertEquals("marvel", tables[0]);
    } finally {
      c.close();
    }
  }
}
