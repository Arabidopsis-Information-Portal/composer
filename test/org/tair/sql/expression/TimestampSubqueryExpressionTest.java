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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.tair.sql.ConnectionTest;
import org.tair.sql.predicate.ComparisonPredicate;
import org.tair.sql.predicate.ComparisonPredicate.Operator;


/**
 * Tests the TimestampSubqueryExpression class.
 * 
 * This test requires a MySQL database with two tables, the main table and the
 * subquery table. The main table must have one column, column_2 INTEGER. The
 * subquery table must have two columns, column_1 TIMESTAMP and column_2 INTEGER.
 * The idea is to retrieve the row from the subquery table as a select
 * expression in a correlated subquery with the main table.
 * 
 * @author Robert J. Muller
 */
public class TimestampSubqueryExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(TimestampSubqueryExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test column name for join column */
  private static final String COL2 = "column_2";
  /** Test column alias */
  private static final String ALIAS1 = "col1";
  /** Test subquery alias */
  private static final String SUBQUERY_ALIAS = "subquery";
  private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  private static final String DATE_STRING = "2012-01-01 01:01:01";

  /** Test database table name */
  private static final String TABLE = "MainTest";
  /** Test correlation name for main table */
  private static final String MAIN_CORR = "m";
  /** Test database table name for subquery table */
  private static final String SUBQUERY_TABLE = "TimestampSubqueryTest";
  /** Test correlation name for subquery table */
  private static final String SUBQUERY_CORR = "t";

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampSubqueryExpression#TimestampSubqueryExpression()}
   * .
   */
  @Test
  public void testTimestampSubqueryExpression() {
    TimestampSubqueryExpression expr = new TimestampSubqueryExpression();
    assertTrue("Could not create TimestampSubqueryExpression", expr != null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampSubqueryExpression#TimestampSubqueryExpression(org.tair.sql.expression.IExpression, java.lang.String)}
   * .
   */
  @Test
  public void testTimestampSubqueryExpressionIExpressionString() {
    TimestampExpression bdExpr = new TimestampExpression(COL1, ALIAS1);
    TimestampSubqueryExpression expr =
      new TimestampSubqueryExpression(bdExpr, SUBQUERY_TABLE);
    assertTrue("Could not create TimestampExpression", expr != null);
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
   * {@link org.tair.sql.expression.TimestampSubqueryExpression#TimestampSubqueryExpression(org.tair.sql.expression.IExpression, java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testTimestampSubqueryExpressionIExpressionStringString() {
    TimestampExpression bdExpr =
      new TimestampExpression(SUBQUERY_CORR + "." + COL1, ALIAS1);
    TimestampSubqueryExpression expr =
      new TimestampSubqueryExpression(bdExpr, SUBQUERY_TABLE, SUBQUERY_CORR);
    assertTrue("Could not create TimestampExpression", expr != null);
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
   * {@link org.tair.sql.expression.TimestampSubqueryExpression#getValue(java.sql.ResultSet, java.lang.String)}
   * .
   * @throws ParseException when the date string can't be parsed
   */
  @Test
  public void testGetValue() throws ParseException {
    TimestampExpression bdExpr =
      new TimestampExpression(SUBQUERY_CORR + "." + COL1, ALIAS1);
    TimestampSubqueryExpression expr =
      new TimestampSubqueryExpression(bdExpr, SUBQUERY_TABLE, SUBQUERY_CORR);
    // Set an alias on the subquery expression within the SELECT.
    expr.setAlias(SUBQUERY_ALIAS);
    assertTrue("Could not create TimestampExpression", expr != null);
    // Add correlation predicate in WHERE clause.
    IntegerExpression left = new IntegerExpression(MAIN_CORR + "." + COL2);
    Operator op = Operator.EQ;
    IntegerParameterExpression right =
      new IntegerParameterExpression(SUBQUERY_CORR + "." + COL2);
    ComparisonPredicate<Integer> predicate =
      new ComparisonPredicate<Integer>(left, op, right);
    expr.addPredicateToWhere(predicate);
    String testString =
      "SELECT (SELECT t.column_1 AS COL1 FROM TimestampSubqueryTest t WHERE m.column_2 = t.column_2) AS subquery FROM "
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
        Timestamp value = expr.getValue(rs);
        assertTrue("Could not get Timestamp value", value != null);
        Timestamp TEST_VALUE =
          new Timestamp(formatter.parse(DATE_STRING).getTime());

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
    TimestampExpression bdExpr =
      new TimestampExpression(correlationName + ".column_1", "col1");
    TimestampSubqueryExpression expr =
      new TimestampSubqueryExpression(bdExpr, "SubqueryTest", correlationName);
    assertTrue("Could not create TimestampExpression", expr != null);
    String testString = "(SELECT t1.column_1 AS COL1 FROM SubqueryTest t1)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }
}
