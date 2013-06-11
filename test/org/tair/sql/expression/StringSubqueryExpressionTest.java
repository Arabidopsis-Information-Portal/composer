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
import org.tair.sql.predicate.ComparisonPredicate;
import org.tair.sql.predicate.ComparisonPredicate.Operator;


/**
 * Tests the StringSubqueryExpression class.
 * 
 * This test requires a MySQL database with two tables, the main table and the
 * subquery table. The main table must have one column, column_2 INTEGER. The
 * subquery table must have two columns, column_1 VARCHAR(100) and column_2 INTEGER.
 * The idea is to retrieve the row from the subquery table as a select
 * expression in a correlated subquery with the main table.
 * 
 * @author Robert J. Muller
 */
public class StringSubqueryExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(StringSubqueryExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test column name for join column */
  private static final String COL2 = "column_2";
  /** Test column alias */
  private static final String ALIAS1 = "col1";
  /** Test subquery alias */
  private static final String SUBQUERY_ALIAS = "subquery";
  /** Test value for comparison, in database table */
  private static final String TEST_VALUE = new String("a test string");

  /** Test database table name */
  private static final String TABLE = "MainTest";
  /** Test correlation name for main table */
  private static final String MAIN_CORR = "m";
  /** Test database table name for subquery table */
  private static final String SUBQUERY_TABLE = "StringSubqueryTest";
  /** Test correlation name for subquery table */
  private static final String SUBQUERY_CORR = "t";

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringSubqueryExpression#StringSubqueryExpression()}
   * .
   */
  @Test
  public void testStringSubqueryExpression() {
    StringSubqueryExpression expr = new StringSubqueryExpression();
    assertTrue("Could not create StringSubqueryExpression", expr != null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringSubqueryExpression#StringSubqueryExpression(org.tair.sql.expression.IExpression, java.lang.String)}
   * .
   */
  @Test
  public void testStringSubqueryExpressionIExpressionString() {
    StringExpression bdExpr = new StringExpression(COL1, ALIAS1);
    StringSubqueryExpression expr =
      new StringSubqueryExpression(bdExpr, SUBQUERY_TABLE);
    assertTrue("Could not create StringExpression", expr != null);
    String testString =
      "(SELECT " + COL1 + " AS " + ALIAS1 + " FROM " + SUBQUERY_TABLE + ")";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringSubqueryExpression#StringSubqueryExpression(org.tair.sql.expression.IExpression, java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testStringSubqueryExpressionIExpressionStringString() {
    StringExpression bdExpr =
      new StringExpression(SUBQUERY_CORR + "." + COL1, ALIAS1);
    StringSubqueryExpression expr =
      new StringSubqueryExpression(bdExpr, SUBQUERY_TABLE, SUBQUERY_CORR);
    assertTrue("Could not create StringExpression", expr != null);
    String testString =
      "(SELECT "
          + SUBQUERY_CORR
          + "."
          + COL1
          + " AS "
          + ALIAS1
          + " FROM "
          + SUBQUERY_TABLE
          + " "
          + SUBQUERY_CORR
          + ")";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringSubqueryExpression#getValue(java.sql.ResultSet, java.lang.String)}
   * .
   */
  @Test
  public void testGetValue() {
    StringExpression bdExpr =
      new StringExpression(SUBQUERY_CORR + "." + COL1, ALIAS1);
    StringSubqueryExpression expr =
      new StringSubqueryExpression(bdExpr, SUBQUERY_TABLE, SUBQUERY_CORR);
    // Set an alias on the subquery expression within the SELECT.
    expr.setAlias(SUBQUERY_ALIAS);
    assertTrue("Could not create StringExpression", expr != null);
    // Add correlation predicate in WHERE clause.
    IntegerExpression left = new IntegerExpression(MAIN_CORR + "." + COL2);
    Operator op = Operator.EQ;
    IntegerParameterExpression right =
      new IntegerParameterExpression(SUBQUERY_CORR + "." + COL2);
    ComparisonPredicate<Integer> predicate =
      new ComparisonPredicate<Integer>(left, op, right);
    expr.addPredicateToWhere(predicate);
    String testString =
      "SELECT (SELECT t.column_1 AS COL1 FROM StringSubqueryTest t WHERE m.column_2 = t.column_2) AS subquery FROM "
          + TABLE
          + " "
          + MAIN_CORR;
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    // Put the subquery into the SELECT as the expression, with alias.
    expr.getSql(builder);
    builder.append(" FROM ");
    builder.append(TABLE);
    builder.append(" ");
    builder.append(MAIN_CORR);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));

    // Now execute the SQL and use getValue to get the returned scalar value.
    try {
      String sql = builder.toString();
      PreparedStatement stmt = connection.prepareStatement(sql);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        String value = expr.getValue(rs);
        assertTrue("Could not get String value", value != null);

        // Compare the values to make sure we got back the real value.
        assertTrue("Wrong value retrieved: " + value, TEST_VALUE.equals(value));
      } else {
        fail("Retrieved no test data from database");
      }
    } catch (SQLException e) {
      logger.error("Could not parse or execute SQL statement: "
                       + builder.toString(),
                   e);
      fail("Could not parse SQL statement: " + builder.toString());
    }

  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#getSql(java.lang.StringBuilder)}
   * .
   */
  @Test
  public void testGetSql() {
    String correlationName = "t1";
    StringExpression bdExpr =
      new StringExpression(correlationName + ".column_1", "col1");
    StringSubqueryExpression expr =
      new StringSubqueryExpression(bdExpr, "SubqueryTest", correlationName);
    assertTrue("Could not create StringExpression", expr != null);
    String testString = "(SELECT t1.column_1 AS COL1 FROM SubqueryTest t1)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }
}
