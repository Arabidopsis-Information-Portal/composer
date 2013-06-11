/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.tair.sql.ConnectionTest;


/**
 * Tests the Timestamp expression class
 * 
 * @author Robert J. Muller
 */
public class TimestampExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(TimestampExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test alias */
  private static final String ALIAS1 = "col1";

  /** Test database table name */
  private static final String TABLE = "TimestampTest";

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampExpression#TimestampExpression()} .
   * 
   * This is the default constructor, which does nothing, yielding the NULL
   * keyword.
   */
  @Test
  public void testTimestampExpression() {
    TimestampExpression expr = new TimestampExpression();
    assertTrue("Could not create TimestampExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Null expression but expression found: " + builder.toString(),
               "null".equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampExpression#TimestampExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a non-null expression and a non-null alias.
   */
  @Test
  public void testTimestampExpressionStringString() {
    TimestampExpression expr = new TimestampExpression(COL1, ALIAS1);
    assertTrue("Could not create TimestampExpression", expr != null);
    assertTrue("Did not construct correct expr: " + expr.getExpression(),
               COL1.equalsIgnoreCase(expr.getExpression()));
    assertTrue("Did not construct correct alias: " + expr.getAlias(),
               ALIAS1.equalsIgnoreCase(expr.getAlias()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampExpression#TimestampExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a non-null expression and a null alias.
   */
  @Test
  public void testTimestampExpressionStringStringNullAlias() {
    TimestampExpression expr = new TimestampExpression(COL1, null);
    assertTrue("Could not create TimestampExpression", expr != null);
    assertTrue("Did not construct correct expr: " + expr.getExpression(),
               COL1.equalsIgnoreCase(expr.getExpression()));
    assertTrue("Did not construct null alias: " + expr.getAlias(),
               expr.getAlias() == null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampExpression#TimestampExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a null expression and a non-null alias.
   */
  @Test
  public void testTimestampExpressionStringStringNullExpr() {
    try {
      new TimestampExpression(null, ALIAS1);
      fail("Did not generate runtime exception for null expression");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampExpression#TimestampExpression(java.lang.String)}
   * .
   * 
   * This constructor builds an expression with no alias.
   */
  @Test
  public void testTimestampExpressionString() {
    TimestampExpression expr = new TimestampExpression(COL1);
    assertTrue("Could not create expression", expr != null);
    assertTrue("Did not construct correct expression: " + expr.getExpression(),
               COL1.equalsIgnoreCase(COL1));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampExpression#TimestampExpression(java.lang.String)}
   * .
   * 
   * This constructor tests for the exception on getting a null expression
   * string.
   */
  @Test
  public void testTimestampExpressionStringNull() {
    try {
      new TimestampExpression(null);
      fail("Did not generate runtime exception for null expression");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid numeric value.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetValue() {
    TimestampExpression expr = new TimestampExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a IntegerTest table with INTEGER column_1 column
    // containing an integer 12345678.
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    Timestamp value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Timestamp getValue(): " + e.getMessage(), e);
      fail("Could not get Timestamp value");
    }
    assertTrue("Could not query Timestamp value from database", value != null);
    assertTrue("Could not get valid timestamp from database", value.getTime() > 0);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a null value.
   * 
   * @throws SQLException when there is a database problem
   */
  /* MySQL does not support null timestamps. !!!
  @Test
  public void testGetNullValue() throws SQLException {
    TimestampExpression expr = new TimestampExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a NullIntegerTest table with INTEGER column_1 column
    // containing a null value.
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(NULL_TABLE);
    Timestamp value = null;
    try {
      PreparedStatement stmt = connection.prepareStatement(builder.toString());
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Timestamp getValue(): " + e.getMessage(), e);
      fail("Could not get Timestamp null value");
    }
    assertTrue("Did not get null value from database", value == null);
  }
  /*

  /**
   * Test method for
   * {@link org.tair.sql.expression.AliasedExpression#getSql(java.lang.StringBuilder)}
   * .
   */
  @Test
  public void testGetSql() {
    TimestampExpression expr = new TimestampExpression(COL1, ALIAS1);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct SQL expression: " + builder.toString(),
               (COL1 + " AS " + ALIAS1).equalsIgnoreCase(builder.toString()));
  }
}
