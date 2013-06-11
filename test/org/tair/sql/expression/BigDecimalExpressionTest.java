/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.tair.sql.ConnectionTest;


/**
 * Tests the BigDecimal expression class
 * 
 * @author Robert J. Muller
 */
public class BigDecimalExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(BigDecimalExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test alias */
  private static final String ALIAS1 = "col1";

  /** Test database table name */
  private static final String TABLE = "BigDecimalTest";
  /** Null test database table name */
  private static final String NULL_TABLE = "NullBigDecimalTest";

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalExpression#BigDecimalExpression()}
   * .
   * 
   * This is the default constructor, which does nothing, yielding the NULL
   * keyword.
   */
  @Test
  public void testBigDecimalExpression() {
    BigDecimalExpression expr = new BigDecimalExpression();
    assertTrue("Could not create BigDecimalExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Null expression but expression found: " + builder.toString(),
               "null".equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalExpression#BigDecimalExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a non-null expression and a non-null alias.
   */
  @Test
  public void testBigDecimalExpressionStringString() {
    BigDecimalExpression expr = new BigDecimalExpression(COL1, ALIAS1);
    assertTrue("Could not create BigDecimalExpression", expr != null);
    assertTrue("Did not construct correct expr: " + expr.getExpression(),
               COL1.equalsIgnoreCase(expr.getExpression()));
    assertTrue("Did not construct correct alias: " + expr.getAlias(),
               ALIAS1.equalsIgnoreCase(expr.getAlias()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalExpression#BigDecimalExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a non-null expression and a null alias.
   */
  @Test
  public void testBigDecimalExpressionStringStringNullAlias() {
    BigDecimalExpression expr = new BigDecimalExpression(COL1, null);
    assertTrue("Could not create BigDecimalExpression", expr != null);
    assertTrue("Did not construct correct expr: " + expr.getExpression(),
               COL1.equalsIgnoreCase(expr.getExpression()));
    assertTrue("Did not construct null alias: " + expr.getAlias(),
               expr.getAlias() == null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalExpression#BigDecimalExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a null expression and a non-null alias.
   */
  @Test
  public void testBigDecimalExpressionStringStringNullExpr() {
    try {
      new BigDecimalExpression(null, ALIAS1);
      fail("Did not generate runtime exception for null expression");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalExpression#BigDecimalExpression(java.lang.String)}
   * .
   * 
   * This constructor builds an expression with no alias.
   */
  @Test
  public void testBigDecimalExpressionString() {
    BigDecimalExpression expr = new BigDecimalExpression(COL1);
    assertTrue("Could not create expression", expr != null);
    assertTrue("Did not construct correct expression: " + expr.getExpression(),
               COL1.equalsIgnoreCase(COL1));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalExpression#BigDecimalExpression(java.lang.String)}
   * .
   * 
   * This constructor tests for the exception on getting a null expression
   * string.
   */
  @Test
  public void testBigDecimalExpressionStringNull() {
    try {
      new BigDecimalExpression(null);
      fail("Did not generate runtime exception for null expression");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid numeric value.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetValue() {
    BigDecimalExpression expr = new BigDecimalExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BigDecimalTest table with NUMERIC column_1 column
    // containing a floating point number 1234.5678.
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    BigDecimal value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing BigDecimal getValue(): " + e.getMessage(), e);
      fail("Could not get BigDecimal value");
    }
    assertTrue("Could not query BigDecimal value from database", value != null);
    BigDecimal testValue = new BigDecimal("1234.5678");
    value.setScale(4, RoundingMode.HALF_UP);
    testValue.setScale(4, RoundingMode.HALF_UP);
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a null value.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetNullValue() throws SQLException {
    BigDecimalExpression expr = new BigDecimalExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a NullBigDecimalTest table with NUMERIC column_1 column
    // containing a null value.
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(NULL_TABLE);
    BigDecimal value = null;
    try {
      PreparedStatement stmt = connection.prepareStatement(builder.toString());
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing BigDecimal getValue(): " + e.getMessage(), e);
      fail("Could not get BigDecimal null value");
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
    BigDecimalExpression expr = new BigDecimalExpression(COL1, ALIAS1);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct SQL expression: " + builder.toString(),
               (COL1 + " AS " + ALIAS1).equalsIgnoreCase(builder.toString()));
  }

}
