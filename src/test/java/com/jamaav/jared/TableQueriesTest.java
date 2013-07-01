package com.jamaav.jared;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jamaav.jared.CreateTableOptions.Durability;
import com.jamaav.jared.db.Connection;
import com.jamaav.jared.db.DriverManager;

public class TableQueriesTest {
  @Before
  public void setUp() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Rethink.r(c).database("superheroes").createDatabase().createTable("marvel");
    } finally {
      c.close();
    }
  }

  @After
  public void tearDown() throws ConnectionException, QueryException {
    Connection c = DriverManager.getConnection("localhost", 28015);

    try {
      Rethink.r(c).database("superheroes").table("marvel").dropTable().dropDatabase();
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

  @Test
  public void testCreateTableWithPrimaryKey() throws ConnectionException,
      QueryException {
    CreateTableOptions options = new CreateTableOptions();
    options.setPrimaryKey("name");
    options.setCacheSize(128);
    options.setDurability(Durability.SOFT);
    Connection c = DriverManager.getConnection("localhost", 28015);
    Rethink r = Rethink.r(c).database("superheroes");
    try {
      r.createTable("dc", options);
      r.dropTable();
    } finally {
      c.close();
    }
  }
}
