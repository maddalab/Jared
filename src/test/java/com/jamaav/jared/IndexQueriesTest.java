package com.jamaav.jared;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jamaav.jared.db.Connection;
import com.jamaav.jared.db.DriverManager;

public class IndexQueriesTest {

  @Before
  public void setUp() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Rethink.r(c).database("superheroes").createDatabase()
          .createTable("marvel");
    } finally {
      c.close();
    }
  }

  @After
  public void tearDown() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);
    try {
      Rethink.r(c).database("superheroes").table("marvel")
          .dropIndex("code_name").dropTable().dropDatabase();
    } finally {
      c.close();
    }
  }

  @Test
  public void testNothing() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Rethink r = Rethink.r(c).database("superheroes").table("marvel");
      r.createIndex("code_name");
      String[] indexes = r.listIndexes();
      Assert.assertEquals(1, indexes.length);
      Assert.assertEquals("code_name", indexes[0]);
    } finally {
      c.close();
    }
  }
}
