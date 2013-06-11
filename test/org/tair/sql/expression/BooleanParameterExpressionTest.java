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
import org.tair.sql.expression.BooleanParameterExpression.Type;


/**
 * Test the BooleanParameterExpression class.
 * 
 * @author Robert J. Muller
 */
public class BooleanParameterExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(BooleanExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test Boolean value */
  private static final Boolean bI1 = Boolean.TRUE;
  /** Test Boolean value */
  private static final Boolean bI2 = Boolean.FALSE;

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanParameterExpression#bind(java.sql.PreparedStatement, int)}
   * .
   */
  @Test
  public void testBind() {
    String exprString = COL1 + " = ?";
    BooleanParameterExpression expr =
      new BooleanParameterExpression(exprString, bI1, Type.INTEGER);
    assertTrue("Could not create BooleanParameterExpression", expr != null);
    StringBuilder builder =
      new StringBuilder("SELECT column_1 FROM BooleanTest WHERE ");
    expr.getSql(builder);

    PreparedStatement stmt;
    try {
      stmt = connection.prepareStatement(builder.toString());
      expr.bind(stmt, 1);
      assertTrue(true);
    } catch (SQLException e) {
      logger.error("Could not bind Boolean value", e);
      fail(e.getMessage());
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanParameterExpression#BooleanParameterExpression()}
   * .
   */
  @Test
  public void testBooleanParameterExpression() {
    BooleanParameterExpression expr = new BooleanParameterExpression();
    assertTrue("Could not create BooleanParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Null expression but expression found: " + builder.toString(),
               "null".equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanParameterExpression#BooleanParameterExpression(java.lang.String)}
   * .
   */
  @Test
  public void testBooleanParameterExpressionString() {
    String exprString = COL1 + " = ?";
    BooleanParameterExpression expr =
      new BooleanParameterExpression(exprString, Type.INTEGER);
    assertTrue("Could not create BooleanParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BooleanParameterExpression#BooleanParameterExpression(java.lang.String, java.math.Boolean)}
   * .
   */
  @Test
  public void testBooleanParameterExpressionStringBoolean() {
    String exprString = COL1 + " = ?";
    BooleanParameterExpression expr =
      new BooleanParameterExpression(exprString, bI1, Type.INTEGER);
    assertTrue("Could not create BooleanParameterExpression", expr != null);
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
    BooleanParameterExpression expr =
      new BooleanParameterExpression(exprString, bI1, Type.INTEGER);
    assertTrue("Could not create BooleanParameterExpression", expr != null);
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
    BooleanParameterExpression expr =
      new BooleanParameterExpression(exprString, bI1, Type.INTEGER);
    assertTrue("Could not create BooleanParameterExpression", expr != null);
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
    BooleanParameterExpression expr =
      new BooleanParameterExpression(exprString, bI1, Type.INTEGER);
    assertTrue("Could not create BooleanParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
  }
}
