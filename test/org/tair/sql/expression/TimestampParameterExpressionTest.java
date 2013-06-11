/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.tair.sql.ConnectionTest;


/**
 * Test the TimestampParameterExpression class.
 * 
 * @author Robert J. Muller
 */
public class TimestampParameterExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(StringExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test String value */
  private static final Timestamp bT1 =
    new Timestamp(System.currentTimeMillis());
  /** Test String value */
  private static final Timestamp bT2 = new Timestamp(System.currentTimeMillis()
                                                     + 1000
                                                     * 60
                                                     * 60
                                                     * 24);

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampParameterExpression#bind(java.sql.PreparedStatement, int)}
   * .
   */
  @Test
  public void testBind() {
    String exprString = COL1 + " = ?";
    TimestampParameterExpression expr =
      new TimestampParameterExpression(exprString, bT1);
    assertTrue("Could not create StringParameterExpression", expr != null);
    StringBuilder builder =
      new StringBuilder("SELECT column_1 FROM StringTest WHERE ");
    expr.getSql(builder);

    PreparedStatement stmt;
    try {
      stmt = connection.prepareStatement(builder.toString());
      expr.bind(stmt, 1);
      assertTrue(true);
    } catch (SQLException e) {
      logger.error("Could not bind String value", e);
      fail(e.getMessage());
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampParameterExpression#TimestampParameterExpression()}
   * .
   */
  @Test
  public void testStringParameterExpression() {
    StringParameterExpression expr = new StringParameterExpression();
    assertTrue("Could not create StringParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Null expression but expression found: " + builder.toString(),
               "null".equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampParameterExpression#TimestampParameterExpression(java.lang.String)}
   * .
   */
  @Test
  public void testStringParameterExpressionString() {
    String exprString = COL1 + " = ?";
    StringParameterExpression expr = new StringParameterExpression(exprString);
    assertTrue("Could not create StringParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.TimestampParameterExpression#TimestampParameterExpression(java.lang.String, java.math.String)}
   * .
   */
  @Test
  public void testStringParameterExpressionStringString() {
    String exprString = COL1 + " = ?";
    TimestampParameterExpression expr =
      new TimestampParameterExpression(exprString, bT1);
    assertTrue("Could not create TimestampParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
    assertTrue("Wrong value: " + expr.getParameterValue(),
               bT1.equals(expr.getParameterValue()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AbstractSingleParameterExpression#getParameterValue()}
   * .
   */
  @Test
  public void testGetParameterValue() {
    String exprString = COL1 + " = ?";
    TimestampParameterExpression expr =
      new TimestampParameterExpression(exprString, bT1);
    assertTrue("Could not create TimestampParameterExpression", expr != null);
    assertTrue("Wrong value: " + expr.getParameterValue(),
               bT1.equals(expr.getParameterValue()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AbstractSingleParameterExpression#setParameterValue(java.lang.Object)}
   * .
   */
  @Test
  public void testSetParameterValue() {
    String exprString = COL1 + " = ?";
    TimestampParameterExpression expr =
      new TimestampParameterExpression(exprString, bT1);
    assertTrue("Could not create TimestampParameterExpression", expr != null);
    expr.setParameterValue(bT2);
    assertTrue("Wrong value: " + expr.getParameterValue(),
               bT2.equals(expr.getParameterValue()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.Expression#getSql(java.lang.StringBuilder)}.
   */
  @Test
  public void testGetSql() {
    String exprString = COL1 + " = ?";
    TimestampParameterExpression expr =
      new TimestampParameterExpression(exprString, bT1);
    assertTrue("Could not create TimestampParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
  }
}
