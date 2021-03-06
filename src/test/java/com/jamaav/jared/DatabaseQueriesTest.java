package com.jamaav.jared;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jamaav.jared.db.Connection;
import com.jamaav.jared.db.DriverManager;

public class DatabaseQueriesTest {

  @Before
  public void setUp() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Rethink.r(c).database("superheroes").createDatabase();
    } finally {
      c.close();
    }
  }

  @After
  public void tearDown() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Rethink.r(c).database("superheroes").dropDatabase();
    } finally {
      c.close();
    }
  }

  @Test
  public void testListDatabases() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Rethink r = Rethink.r(c);
      String[] dbs = r.listDatabases();
      Set<String> tables = new HashSet<>(Arrays.asList(dbs));
      Assert.assertTrue(tables.contains("test"));
      Assert.assertTrue(tables.contains("superheroes"));
    } finally {
      c.close();
    }
  }
}
