/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.tair.sql.ConnectionTest;


/**
 * Tests the Integer expression class
 * 
 * @author Robert J. Muller
 */
public class IntegerExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(IntegerExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test alias */
  private static final String ALIAS1 = "col1";

  /** Test database table name */
  private static final String TABLE = "IntegerTest";
  /** Null test database table name */
  private static final String NULL_TABLE = "NullIntegerTest";

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerExpression#IntegerExpression()} .
   * 
   * This is the default constructor, which does nothing, yielding the NULL
   * keyword.
   */
  @Test
  public void testIntegerExpression() {
    IntegerExpression expr = new IntegerExpression();
    assertTrue("Could not create IntegerExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Null expression but expression found: " + builder.toString(),
               "null".equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerExpression#IntegerExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a non-null expression and a non-null alias.
   */
  @Test
  public void testIntegerExpressionStringString() {
    IntegerExpression expr = new IntegerExpression(COL1, ALIAS1);
    assertTrue("Could not create IntegerExpression", expr != null);
    assertTrue("Did not construct correct expr: " + expr.getExpression(),
               COL1.equalsIgnoreCase(expr.getExpression()));
    assertTrue("Did not construct correct alias: " + expr.getAlias(),
               ALIAS1.equalsIgnoreCase(expr.getAlias()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerExpression#IntegerExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a non-null expression and a null alias.
   */
  @Test
  public void testIntegerExpressionStringStringNullAlias() {
    IntegerExpression expr = new IntegerExpression(COL1, null);
    assertTrue("Could not create IntegerExpression", expr != null);
    assertTrue("Did not construct correct expr: " + expr.getExpression(),
               COL1.equalsIgnoreCase(expr.getExpression()));
    assertTrue("Did not construct null alias: " + expr.getAlias(),
               expr.getAlias() == null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerExpression#IntegerExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a null expression and a non-null alias.
   */
  @Test
  public void testIntegerExpressionStringStringNullExpr() {
    try {
      new IntegerExpression(null, ALIAS1);
      fail("Did not generate runtime exception for null expression");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerExpression#IntegerExpression(java.lang.String)}
   * .
   * 
   * This constructor builds an expression with no alias.
   */
  @Test
  public void testIntegerExpressionString() {
    IntegerExpression expr = new IntegerExpression(COL1);
    assertTrue("Could not create expression", expr != null);
    assertTrue("Did not construct correct expression: " + expr.getExpression(),
               COL1.equalsIgnoreCase(COL1));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerExpression#IntegerExpression(java.lang.String)}
   * .
   * 
   * This constructor tests for the exception on getting a null expression
   * string.
   */
  @Test
  public void testIntegerExpressionStringNull() {
    try {
      new IntegerExpression(null);
      fail("Did not generate runtime exception for null expression");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid numeric value.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetValue() {
    IntegerExpression expr = new IntegerExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a IntegerTest table with INTEGER column_1 column
    // containing an integer 12345678.
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    Integer value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Integer getValue(): " + e.getMessage(), e);
      fail("Could not get Integer value");
    }
    assertTrue("Could not query Integer value from database", value != null);
    Integer testValue = new Integer("12345678");
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a null value.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetNullValue() throws SQLException {
    IntegerExpression expr = new IntegerExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a NullIntegerTest table with INTEGER column_1 column
    // containing a null value.
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(NULL_TABLE);
    Integer value = null;
    try {
      PreparedStatement stmt = connection.prepareStatement(builder.toString());
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Integer getValue(): " + e.getMessage(), e);
      fail("Could not get Integer null value");
    }
    assertTrue("Did not get null value from database", value == null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AliasedExpression#getSql(java.lang.StringBuilder)}
   * .
   */
  @Test
  public void testGetSql() {
    IntegerExpression expr = new IntegerExpression(COL1, ALIAS1);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct SQL expression: " + builder.toString(),
               (COL1 + " AS " + ALIAS1).equalsIgnoreCase(builder.toString()));
  }
}
