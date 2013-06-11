/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;


/**
 * Tests the non-abstract methods of the Expression abstract class and the
 * methods of the AliasedExpression class
 * 
 * @author Robert J. Muller
 */
public class AliasedExpressionTest {

  private final static String COL1 = "column_1";
  private final static String VARNAME = "var";
  private final static String COL_LEX = "${" + VARNAME + "}";
  private final static String COL3 = "column_3";

  private final static String ALIAS1 = "alias1";
  private final static String ALIAS2 = "alias2";

  /**
   * Test method for
   * {@link org.tair.sql.expression.AliasedExpression#getSql(java.lang.StringBuilder)}
   * .
   */
  @Test
  public void testGetSql() {
    AliasedExpression expr = new AliasedExpression(COL1, ALIAS1);
    StringBuilder builder = new StringBuilder("");
    expr.getSql(builder);
    String sql = builder.toString();
    assertTrue("Invalid SQL: " + sql, sql.equals(COL1 + " AS " + ALIAS1));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AliasedExpression#AliasedExpression()}.
   */
  @Test
  public void testAliasedExpression() {
    AliasedExpression expr = new AliasedExpression(COL1, ALIAS1);
    assertTrue("No expression instantiated", expr != null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AliasedExpression#AliasedExpression(java.lang.String)}
   * .
   * 
   * This tests a single expression, no lexical variables, and no alias.
   */
  @Test
  public void testAliasedExpressionString() {
    AliasedExpression expr = new AliasedExpression(COL1);
    StringBuilder builder = new StringBuilder("");
    expr.getSql(builder);
    assertTrue(builder.toString().equals(COL1));
    String alias = expr.getAlias();
    assertTrue(alias == null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AliasedExpression#AliasedExpression(java.lang.String)}
   * .
   * 
   * This tests instantiating a single expression, a single lexical variable,
   * and no alias.
   */
  @Test
  public void testAliasedExpressionStringLexicalVar() {
    AliasedExpression expr = new AliasedExpression(COL_LEX);
    StringBuilder builder = new StringBuilder("");
    try {
      assertTrue(expr.getVariable(VARNAME) == null);
    } catch (NoSuchLexicalVariableException e) {
      fail("Could not get lexical variable 'var'");
    }
    try {
      expr.setVariable(VARNAME, COL3);
    } catch (NoSuchLexicalVariableException e) {
      fail("Could not find lexical variable 'var' to set value");
    }
    expr.getSql(builder);
    assertTrue(builder.toString().equals(COL3));
    String alias = expr.getAlias();
    assertTrue(alias == null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AliasedExpression#AliasedExpression(java.lang.String, java.lang.String)}
   * . This method tests a single expression and an alias.
   */
  @Test
  public void testAliasedExpressionStringString() {
    AliasedExpression expr = new AliasedExpression(COL1, ALIAS1);
    StringBuilder builder = new StringBuilder("");
    expr.getSql(builder);
    assertTrue(builder.toString().equals(COL1 + " AS " + ALIAS1));
    String alias = expr.getAlias();
    assertTrue("Wrong alias: " + alias, alias.equalsIgnoreCase(ALIAS1));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AliasedExpression#getAlias()}.
   */
  @Test
  public void testGetAlias() {
    AliasedExpression expr = new AliasedExpression(COL1, ALIAS1);
    String alias = expr.getAlias();
    assertTrue("Wrong alias: " + alias, alias.equalsIgnoreCase(ALIAS1));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.AliasedExpression#setAlias(java.lang.String)}
   * .
   */
  @Test
  public void testSetAlias() {
    AliasedExpression expr = new AliasedExpression(COL1, ALIAS1);
    String alias = expr.getAlias();
    assertTrue("Wrong alias: " + alias, alias.equalsIgnoreCase(ALIAS1));
    expr.setAlias(ALIAS2);
    alias = expr.getAlias();
    assertTrue("Wrong alias set: " + alias, alias.equalsIgnoreCase(ALIAS2));
  }

  /**
   * Test method for {@link org.tair.sql.expression.Expression#Expression()}.
   */
  @Test
  public void testExpression() {
    AliasedExpression expr = new AliasedExpression();
    assertTrue("Expression set but not specified: " + expr.expression,
               expr.expression == null);
    assertTrue("Alias set but not specified", expr.getAlias() == null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.Expression#Expression(java.lang.String)}.
   * 
   * This method tests the superclass constructor in the abstract superclass.
   */
  @Test
  public void testExpressionString() {
    AliasedExpression expr = new AliasedExpression(COL1);
    assertTrue("Superclass expression not set",
               COL1.equalsIgnoreCase(expr.expression));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.Expression#getVariable(java.lang.String)}.
   */
  @Test
  public void testGetVariable() {
    AliasedExpression expr = new AliasedExpression(COL_LEX);
    try {
      expr.setVariable(VARNAME, COL3);
    } catch (NoSuchLexicalVariableException e) {
      fail("Could not find lexical variable 'var' to set value");
    }
    try {
      String value = expr.getVariable(VARNAME);
      assertTrue(value != null);
    } catch (NoSuchLexicalVariableException e) {
      fail("Could not find lexical variable 'var' to get value");
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.Expression#setVariable(java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testSetVariable() {
    AliasedExpression expr = new AliasedExpression(COL_LEX, ALIAS1);
    try {
      expr.setVariable(VARNAME, COL3);
    } catch (NoSuchLexicalVariableException e) {
      fail("Could not find lexical variable 'var' to set value");
    }
    // Get the variable value.
    try {
      String value = expr.getVariable(VARNAME);
      assertTrue(value != null);
    } catch (NoSuchLexicalVariableException e) {
      fail("Could not find lexical variable 'var' to get value");
    }
    // Get the right SQL string.
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    String expression = builder.toString();
    assertTrue("SQL with lexical substitution failed: " + expression,
               expression.equalsIgnoreCase(COL3 + " AS " + ALIAS1));
  }
}
