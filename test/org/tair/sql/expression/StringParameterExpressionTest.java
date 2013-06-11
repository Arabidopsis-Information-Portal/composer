/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.tair.sql.ConnectionTest;


/**
 * Test the StringParameterExpression class.
 * 
 * @author Robert J. Muller
 */
public class StringParameterExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(StringExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test String value */
  private static final String bI1 = "a test String";
  /** Test String value */
  private static final String bI2 = "another test String";

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringParameterExpression#bind(java.sql.PreparedStatement, int)}
   * .
   */
  @Test
  public void testBind() {
    String exprString = COL1 + " = ?";
    StringParameterExpression expr =
      new StringParameterExpression(exprString, bI1);
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
   * {@link org.tair.sql.expression.StringParameterExpression#StringParameterExpression()}
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
   * {@link org.tair.sql.expression.StringParameterExpression#StringParameterExpression(java.lang.String)}
   * .
   */
  @Test
  public void testStringParameterExpressionString() {
    String exprString = COL1 + " = ?";
    StringParameterExpression expr =
      new StringParameterExpression(exprString);
    assertTrue("Could not create StringParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.StringParameterExpression#StringParameterExpression(java.lang.String, java.math.String)}
   * .
   */
  @Test
  public void testStringParameterExpressionStringString() {
    String exprString = COL1 + " = ?";
    StringParameterExpression expr =
      new StringParameterExpression(exprString, bI1);
    assertTrue("Could not create StringParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
    assertTrue("Wrong value: " + expr.getParameterValue(),
               bI1.equals(expr.getParameterValue()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AbstractSingleParameterExpression#getParameterValue()}
   * .
   */
  @Test
  public void testGetParameterValue() {
    String exprString = COL1 + " = ?";
    StringParameterExpression expr =
      new StringParameterExpression(exprString, bI1);
    assertTrue("Could not create StringParameterExpression", expr != null);
    assertTrue("Wrong value: " + expr.getParameterValue(),
               bI1.equals(expr.getParameterValue()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AbstractSingleParameterExpression#setParameterValue(java.lang.Object)}
   * .
   */
  @Test
  public void testSetParameterValue() {
    String exprString = COL1 + " = ?";
    StringParameterExpression expr =
      new StringParameterExpression(exprString, bI1);
    assertTrue("Could not create StringParameterExpression", expr != null);
    expr.setParameterValue(bI2);
    assertTrue("Wrong value: " + expr.getParameterValue(),
               bI2.equals(expr.getParameterValue()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.Expression#getSql(java.lang.StringBuilder)}.
   */
  @Test
  public void testGetSql() {
    String exprString = COL1 + " = ?";
    StringParameterExpression expr =
      new StringParameterExpression(exprString, bI1);
    assertTrue("Could not create StringParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
  }
}
