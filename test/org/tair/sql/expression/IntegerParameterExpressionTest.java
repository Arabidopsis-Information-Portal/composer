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
 * Test the IntegerParameterExpression class.
 * 
 * @author Robert J. Muller
 */
public class IntegerParameterExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(IntegerExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test Integer value */
  private static final Integer bI1 = new Integer("12345678");
  /** Test Integer value */
  private static final Integer bI2 = new Integer("56789012");

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerParameterExpression#bind(java.sql.PreparedStatement, int)}
   * .
   */
  @Test
  public void testBind() {
    String exprString = COL1 + " = ?";
    IntegerParameterExpression expr =
      new IntegerParameterExpression(exprString, bI1);
    assertTrue("Could not create IntegerParameterExpression", expr != null);
    StringBuilder builder =
      new StringBuilder("SELECT column_1 FROM IntegerTest WHERE ");
    expr.getSql(builder);

    PreparedStatement stmt;
    try {
      stmt = connection.prepareStatement(builder.toString());
      expr.bind(stmt, 1);
      assertTrue(true);
    } catch (SQLException e) {
      logger.error("Could not bind Integer value", e);
      fail(e.getMessage());
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerParameterExpression#IntegerParameterExpression()}
   * .
   */
  @Test
  public void testIntegerParameterExpression() {
    IntegerParameterExpression expr = new IntegerParameterExpression();
    assertTrue("Could not create IntegerParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Null expression but expression found: " + builder.toString(),
               "null".equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerParameterExpression#IntegerParameterExpression(java.lang.String)}
   * .
   */
  @Test
  public void testIntegerParameterExpressionString() {
    String exprString = COL1 + " = ?";
    IntegerParameterExpression expr =
      new IntegerParameterExpression(exprString);
    assertTrue("Could not create IntegerParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.IntegerParameterExpression#IntegerParameterExpression(java.lang.String, java.math.Integer)}
   * .
   */
  @Test
  public void testIntegerParameterExpressionStringInteger() {
    String exprString = COL1 + " = ?";
    IntegerParameterExpression expr =
      new IntegerParameterExpression(exprString, bI1);
    assertTrue("Could not create IntegerParameterExpression", expr != null);
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
    IntegerParameterExpression expr =
      new IntegerParameterExpression(exprString, bI1);
    assertTrue("Could not create IntegerParameterExpression", expr != null);
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
    IntegerParameterExpression expr =
      new IntegerParameterExpression(exprString, bI1);
    assertTrue("Could not create IntegerParameterExpression", expr != null);
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
    IntegerParameterExpression expr =
      new IntegerParameterExpression(exprString, bI1);
    assertTrue("Could not create IntegerParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
  }
}
