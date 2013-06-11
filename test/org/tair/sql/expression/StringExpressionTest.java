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
 * Tests the String expression class
 * 
 * @author Robert J. Muller
 */
public class StringExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(StringExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test alias */
  private static final String ALIAS1 = "col1";

  /** Test database table name */
  private static final String TABLE = "StringTest";
  /** Null test database table name */
  private static final String NULL_TABLE = "NullStringTest";

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringExpression#StringExpression()}
   * .
   * 
   * This is the default constructor, which does nothing, yielding the NULL
   * keyword.
   */
  @Test
  public void testStringExpression() {
    StringExpression expr = new StringExpression();
    assertTrue("Could not create StringExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Null expression but expression found: " + builder.toString(),
               "null".equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringExpression#StringExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a non-null expression and a non-null alias.
   */
  @Test
  public void testStringExpressionStringString() {
    StringExpression expr = new StringExpression(COL1, ALIAS1);
    assertTrue("Could not create StringExpression", expr != null);
    assertTrue("Did not construct correct expr: " + expr.getExpression(),
               COL1.equalsIgnoreCase(expr.getExpression()));
    assertTrue("Did not construct correct alias: " + expr.getAlias(),
               ALIAS1.equalsIgnoreCase(expr.getAlias()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringExpression#StringExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a non-null expression and a null alias.
   */
  @Test
  public void testStringExpressionStringStringNullAlias() {
    StringExpression expr = new StringExpression(COL1, null);
    assertTrue("Could not create StringExpression", expr != null);
    assertTrue("Did not construct correct expr: " + expr.getExpression(),
               COL1.equalsIgnoreCase(expr.getExpression()));
    assertTrue("Did not construct null alias: " + expr.getAlias(),
               expr.getAlias() == null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringExpression#StringExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a null expression and a non-null alias.
   */
  @Test
  public void testStringExpressionStringStringNullExpr() {
    try {
      new StringExpression(null, ALIAS1);
      fail("Did not generate runtime exception for null expression");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringExpression#StringExpression(java.lang.String)}
   * .
   * 
   * This constructor builds an expression with no alias.
   */
  @Test
  public void testStringExpressionString() {
    StringExpression expr = new StringExpression(COL1);
    assertTrue("Could not create expression", expr != null);
    assertTrue("Did not construct correct expression: " + expr.getExpression(),
               COL1.equalsIgnoreCase(COL1));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringExpression#StringExpression(java.lang.String)}
   * .
   * 
   * This constructor tests for the exception on getting a null expression
   * string.
   */
  @Test
  public void testStringExpressionStringNull() {
    try {
      new StringExpression(null);
      fail("Did not generate runtime exception for null expression");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid numeric value.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetValue() {
    StringExpression expr = new StringExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a StringTest table with VARCHAR column_1 column
    // containing the string "a test string".
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    String value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing String getValue(): " + e.getMessage(), e);
      fail("Could not get String value");
    }
    assertTrue("Could not query String value from database", value != null);
    String testValue = "a test string";
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a null value.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetNullValue() throws SQLException {
    StringExpression expr = new StringExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a NullStringTest table with VARCHAR column_1 column
    // containing a null value.
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(NULL_TABLE);
    String value = null;
    try {
      PreparedStatement stmt = connection.prepareStatement(builder.toString());
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing String getValue(): " + e.getMessage(), e);
      fail("Could not get String null value");
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
    StringExpression expr = new StringExpression(COL1, ALIAS1);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct SQL expression: " + builder.toString(),
               (COL1 + " AS " + ALIAS1).equalsIgnoreCase(builder.toString()));
  }

}
