package com.jamaav.jared;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jamaav.jared.Ql2.Term;

public class DatabaseQueriesTest {

  @Before
  public void setUp() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Statement s = c.createStatement();
      QueryBuilder qb = new QueryBuilder();
      Term query = qb.createDatabase("superheroes").build();
      s.executeUpdate(query);
    } finally {
      c.close();
    }
  }

  @After
  public void tearDown() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Statement s = c.createStatement();
      QueryBuilder qb = new QueryBuilder();
      Term query = qb.dropDatabase("superheroes").build();
      s.executeUpdate(query);
    } finally {
      c.close();
    }
  }

  @Test
  public void testListDatabases() {

  }
}
