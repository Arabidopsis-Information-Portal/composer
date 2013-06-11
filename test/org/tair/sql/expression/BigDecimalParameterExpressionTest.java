/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.tair.sql.ConnectionTest;


/**
 * Test the BigDecimalParameterExpression class.
 * 
 * @author Robert J. Muller
 */
public class BigDecimalParameterExpressionTest extends ConnectionTest {
  /** Logger for this class */
  private static final Logger logger =
    Logger.getLogger(BigDecimalExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test BigDecimal value */
  private static final BigDecimal bD1 = new BigDecimal("1234.5678");
  /** Test BigDecimal value */
  private static final BigDecimal bD2 = new BigDecimal("5678.9012");

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalParameterExpression#bind(java.sql.PreparedStatement, int)}
   * .
   */
  @Test
  public void testBind() {
    String exprString = COL1 + " = ?";
    BigDecimalParameterExpression expr =
      new BigDecimalParameterExpression(exprString, bD1);
    assertTrue("Could not create BigDecimalParameterExpression", expr != null);
    StringBuilder builder =
      new StringBuilder("SELECT column_1 FROM BigDecimalTest WHERE ");
    expr.getSql(builder);

    PreparedStatement stmt;
    try {
      stmt = connection.prepareStatement(builder.toString());
      expr.bind(stmt, 1);
      assertTrue(true);
    } catch (SQLException e) {
      logger.error("Could not bind BigDecimal value", e);
      fail(e.getMessage());
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalParameterExpression#BigDecimalParameterExpression()}
   * .
   */
  @Test
  public void testBigDecimalParameterExpression() {
    BigDecimalParameterExpression expr = new BigDecimalParameterExpression();
    assertTrue("Could not create BigDecimalParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Null expression but expression found: " + builder.toString(),
               "null".equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalParameterExpression#BigDecimalParameterExpression(java.lang.String)}
   * .
   */
  @Test
  public void testBigDecimalParameterExpressionString() {
    String exprString = COL1 + " = ?";
    BigDecimalParameterExpression expr =
      new BigDecimalParameterExpression(exprString);
    assertTrue("Could not create BigDecimalParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.BigDecimalParameterExpression#BigDecimalParameterExpression(java.lang.String, java.math.BigDecimal)}
   * .
   */
  @Test
  public void testBigDecimalParameterExpressionStringBigDecimal() {
    String exprString = COL1 + " = ?";
    BigDecimalParameterExpression expr =
      new BigDecimalParameterExpression(exprString, bD1);
    assertTrue("Could not create BigDecimalParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
    assertTrue("Wrong value: " + expr.getParameterValue(),
               bD1.equals(expr.getParameterValue()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AbstractSingleParameterExpression#getParameterValue()}
   * .
   */
  @Test
  public void testGetParameterValue() {
    String exprString = COL1 + " = ?";
    BigDecimalParameterExpression expr =
      new BigDecimalParameterExpression(exprString, bD1);
    assertTrue("Could not create BigDecimalParameterExpression", expr != null);
    assertTrue("Wrong value: " + expr.getParameterValue(),
               bD1.equals(expr.getParameterValue()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AbstractSingleParameterExpression#setParameterValue(java.lang.Object)}
   * .
   */
  @Test
  public void testSetParameterValue() {
    String exprString = COL1 + " = ?";
    BigDecimalParameterExpression expr =
      new BigDecimalParameterExpression(exprString, bD1);
    assertTrue("Could not create BigDecimalParameterExpression", expr != null);
    expr.setParameterValue(bD2);
    assertTrue("Wrong value: " + expr.getParameterValue(),
               bD2.equals(expr.getParameterValue()));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.Expression#getSql(java.lang.StringBuilder)}.
   */
  @Test
  public void testGetSql() {
    String exprString = COL1 + " = ?";
    BigDecimalParameterExpression expr =
      new BigDecimalParameterExpression(exprString, bD1);
    assertTrue("Could not create BigDecimalParameterExpression", expr != null);
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Wrong expression: " + builder.toString(),
               (COL1 + " = ?").equalsIgnoreCase(builder.toString()));
  }
}
