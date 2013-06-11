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
 * Tests the Boolean expression class
 * 
 * @author Robert J. Muller
 */
public class BooleanExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(BooleanExpressionTest.class);
  /** Test column name for 1/0 */
  private static final String COL1 = "column_1";
  /** Test column name for T/F */
  private static final String COL2 = "column_2";
  /** Test column name for true/false */
  private static final String COL3 = "column_3";
  /** Test column name for y/n */
  private static final String COL4 = "column_4";
  /** Test column name for yes/no */
  private static final String COL5 = "column_5";

  /** Test alias */
  private static final String ALIAS1 = "col1";

  /** Test database table name */
  private static final String TABLE = "BooleanTest";
  /** Null test database table name */
  private static final String NULL_TABLE = "NullBooleanTest";

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#BooleanExpression()} .
   * 
   * This is the default constructor, which does nothing, yielding the NULL
   * keyword.
   */
  @Test
  public void testBooleanExpression() {
    BooleanExpression expr = new BooleanExpression();
    assertTrue("Could not create BooleanExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Null expression but expression found: " + builder.toString(),
               "null".equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#BooleanExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a non-null expression and a non-null alias.
   */
  @Test
  public void testBooleanExpressionStringString() {
    BooleanExpression expr = new BooleanExpression(COL1, ALIAS1);
    assertTrue("Could not create BooleanExpression", expr != null);
    assertTrue("Did not construct correct expr: " + expr.getExpression(),
               COL1.equalsIgnoreCase(expr.getExpression()));
    assertTrue("Did not construct correct alias: " + expr.getAlias(),
               ALIAS1.equalsIgnoreCase(expr.getAlias()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#BooleanExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a non-null expression and a null alias.
   */
  @Test
  public void testBooleanExpressionStringStringNullAlias() {
    BooleanExpression expr = new BooleanExpression(COL1, null);
    assertTrue("Could not create BooleanExpression", expr != null);
    assertTrue("Did not construct correct expr: " + expr.getExpression(),
               COL1.equalsIgnoreCase(expr.getExpression()));
    assertTrue("Did not construct null alias: " + expr.getAlias(),
               expr.getAlias() == null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#BooleanExpression(java.lang.String, java.lang.String)}
   * .
   * 
   * This constructor takes a null expression and a non-null alias.
   */
  @Test
  public void testBooleanExpressionStringStringNullExpr() {
    try {
      new BooleanExpression(null, ALIAS1);
      fail("Did not generate runtime exception for null expression");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#BooleanExpression(java.lang.String)}
   * .
   * 
   * This constructor builds an expression with no alias.
   */
  @Test
  public void testBooleanExpressionString() {
    BooleanExpression expr = new BooleanExpression(COL1);
    assertTrue("Could not create expression", expr != null);
    assertTrue("Did not construct correct expression: " + expr.getExpression(),
               COL1.equalsIgnoreCase(COL1));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#BooleanExpression(java.lang.String)}
   * .
   * 
   * This constructor tests for the exception on getting a null expression
   * string.
   */
  @Test
  public void testBooleanExpressionStringNull() {
    try {
      new BooleanExpression(null);
      fail("Did not generate runtime exception for null expression");
    } catch (RuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid numeric value for true.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGet1Value() {
    BooleanExpression expr = new BooleanExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BooleanTest table with INTEGER column_1 column
    // containing an integer 1.
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" WHERE column_1 = 1");
    Boolean value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean value");
    }
    assertTrue("Could not query Boolean value from database", value != null);
    Boolean testValue = Boolean.TRUE;
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid numeric value for false.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGet0Value() {
    BooleanExpression expr = new BooleanExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BooleanTest table with INTEGER column_1 column
    // containing an integer 0.
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" WHERE column_1 = 0");
    Boolean value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean value");
    }
    assertTrue("Could not query Boolean value from database", value != null);
    Boolean testValue = Boolean.FALSE;
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid string value for true.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetTValue() {
    BooleanExpression expr = new BooleanExpression(COL2, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BooleanTest table with INTEGER column_1 column
    // containing a STRING "T".
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" WHERE UPPER(column_2) = 'T'");
    Boolean value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean value");
    }
    assertTrue("Could not query Boolean value from database", value != null);
    Boolean testValue = Boolean.TRUE;
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid string value for false.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetFValue() {
    BooleanExpression expr = new BooleanExpression(COL2, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BooleanTest table with INTEGER column_1 column
    // containing a string "F".
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" WHERE UPPER(column_2) = 'F'");
    Boolean value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean value");
    }
    assertTrue("Could not query Boolean value from database", value != null);
    Boolean testValue = Boolean.FALSE;
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid string value for true.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetTrueValue() {
    BooleanExpression expr = new BooleanExpression(COL3, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BooleanTest table with INTEGER column_1 column
    // containing a STRING "T".
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" WHERE UPPER(column_3) = 'TRUE'");
    Boolean value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean value");
    }
    assertTrue("Could not query Boolean value from database", value != null);
    Boolean testValue = Boolean.TRUE;
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid string value for false.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetFalseValue() {
    BooleanExpression expr = new BooleanExpression(COL3, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BooleanTest table with INTEGER column_1 column
    // containing a string "F".
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" WHERE UPPER(column_3) = 'FALSE'");
    Boolean value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean value");
    }
    assertTrue("Could not query Boolean value from database", value != null);
    Boolean testValue = Boolean.FALSE;
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid string value for true.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetYValue() {
    BooleanExpression expr = new BooleanExpression(COL4, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BooleanTest table with INTEGER column_1 column
    // containing a STRING "T".
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" WHERE UPPER(column_4) = 'Y'");
    Boolean value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean value");
    }
    assertTrue("Could not query Boolean value from database", value != null);
    Boolean testValue = Boolean.TRUE;
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid string value for false.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetNValue() {
    BooleanExpression expr = new BooleanExpression(COL4, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BooleanTest table with INTEGER column_1 column
    // containing a string "F".
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" WHERE UPPER(column_4) = 'N'");
    Boolean value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean value");
    }
    assertTrue("Could not query Boolean value from database", value != null);
    Boolean testValue = Boolean.FALSE;
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid string value for true.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetYesValue() {
    BooleanExpression expr = new BooleanExpression(COL5, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BooleanTest table with INTEGER column_1 column
    // containing a STRING "T".
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" WHERE UPPER(column_5) = 'YES'");
    Boolean value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean value");
    }
    assertTrue("Could not query Boolean value from database", value != null);
    Boolean testValue = Boolean.TRUE;
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a valid string value for false.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetNoValue() {
    BooleanExpression expr = new BooleanExpression(COL5, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a BooleanTest table with INTEGER column_1 column
    // containing a string "F".
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" WHERE UPPER(column_5) = 'NO'");
    Boolean value = null;
    try {
      String sql = builder.toString(); // for debugging
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean value");
    }
    assertTrue("Could not query Boolean value from database", value != null);
    Boolean testValue = Boolean.FALSE;
    assertTrue("Did not get correct value: " + value, value.equals(testValue));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanExpression#getValue(java.sql.ResultSet)}
   * .
   * 
   * This method tests for getting a null value.
   * 
   * @throws SQLException when there is a database problem
   */
  @Test
  public void testGetNullValue() throws SQLException {
    BooleanExpression expr = new BooleanExpression(COL1, ALIAS1);
    // Compose a SQL statement, execute it, and get the value.
    // Assumes there is a NullBooleanTest table with INTEGER column_1 column
    // containing a null value.
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(NULL_TABLE);
    Boolean value = null;
    try {
      PreparedStatement stmt = connection.prepareStatement(builder.toString());
      ResultSet rs = stmt.executeQuery();
      rs.next();
      value = expr.getValue(rs);
    } catch (SQLException e) {
      logger.error("Testing Boolean getValue(): " + e.getMessage(), e);
      fail("Could not get Boolean null value");
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
    BooleanExpression expr = new BooleanExpression(COL1, ALIAS1);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct SQL expression: " + builder.toString(),
               (COL1 + " AS " + ALIAS1).equalsIgnoreCase(builder.toString()));
  }
}
