/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.tair.sql.predicate.ComparisonPredicate;
import org.tair.sql.predicate.ComparisonPredicate.Operator;


/**
 * Test the SubqueryExpression class.
 * 
 * @author Robert J. Muller
 */
public class SubqueryExpressionTest {

  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test alias */
  private static final String ALIAS1 = "col1";

  /** Test column name */
  private static final String COL2 = "column_2";
  /** Test alias */
  private static final String ALIAS2 = "col2";

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#getSql(java.lang.StringBuilder)}
   * .
   */
  @Test
  public void testGetSql() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest", "t");
    assertTrue("Could not create SubqueryExpression", expr != null);
    StringExpression left = new StringExpression(COL1);
    StringParameterExpression right = new StringParameterExpression("?");
    Operator op = Operator.EQ;
    ComparisonPredicate<String> predicate =
      new ComparisonPredicate<String>(left, op, right);
    expr.addPredicateToWhere(predicate);
    String testString =
      "(SELECT column_1 AS COL1 FROM SubqueryTest t WHERE column_1 = ?)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }


  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#getSqlWithoutParens(java.lang.StringBuilder)}
   * .
   */
  @Test
  public void testGetSqlWithoutParens() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest", "t");
    assertTrue("Could not create SubqueryExpression", expr != null);
    StringExpression left = new StringExpression(COL1);
    StringParameterExpression right = new StringParameterExpression("?");
    Operator op = Operator.EQ;
    ComparisonPredicate<String> predicate =
      new ComparisonPredicate<String>(left, op, right);
    expr.addPredicateToWhere(predicate);
    String testString =
      "SELECT column_1 AS COL1 FROM SubqueryTest t WHERE column_1 = ?";
    StringBuilder builder = new StringBuilder();
    expr.getSqlWithoutParens(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#SubqueryExpression()}.
   */
  @Test
  public void testSubqueryExpression() {
    SubqueryExpression expr = new SubqueryExpression();
    assertTrue("Could not create SubqueryExpression", expr != null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#SubqueryExpression(org.tair.sql.expression.IExpression, java.lang.String)}
   * .
   */
  @Test
  public void testSubqueryExpressionIExpressionString() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest");
    assertTrue("Could not create SubqueryExpression", expr != null);
    String testString = "(SELECT column_1 AS COL1 FROM SubqueryTest)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#SubqueryExpression(org.tair.sql.expression.IExpression, java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testSubqueryExpressionIExpressionStringString() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest", "t");
    assertTrue("Could not create SubqueryExpression", expr != null);
    String testString = "(SELECT column_1 AS COL1 FROM SubqueryTest t)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#addExpressionToSelect(org.tair.sql.expression.ISelectExpression)}
   * .
   */
  @Test
  public void testAddExpressionToSelect() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest", "t");
    assertTrue("Could not create SubqueryExpression", expr != null);
    IntegerExpression intExpr = new IntegerExpression(COL2 + " + 23", ALIAS2);
    expr.addExpressionToSelect(intExpr);
    String testString =
      "(SELECT column_1 AS COL1, column_2 + 23 AS COL2 FROM SubqueryTest t)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#addPredicateToWhere(org.tair.sql.predicate.Predicate)}
   * .
   */
  @Test
  public void testAddPredicateToWhere() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest", "t");
    assertTrue("Could not create SubqueryExpression", expr != null);
    StringExpression left = new StringExpression(COL1);
    StringParameterExpression right = new StringParameterExpression("?");
    Operator op = Operator.EQ;
    ComparisonPredicate<String> predicate =
      new ComparisonPredicate<String>(left, op, right);
    expr.addPredicateToWhere(predicate);
    String testString =
      "(SELECT column_1 AS COL1 FROM SubqueryTest t WHERE column_1 = ?)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#addTableToFrom(java.lang.String, java.lang.String, org.tair.sql.predicate.Predicate)}
   * .
   */
  @Test
  public void testAddTableToFrom() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    String correlationName1 = "t1";
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest", correlationName1);
    assertTrue("Could not create SubqueryExpression", expr != null);
    String correlationName2 = "t2";
    StringExpression left =
      new StringExpression(correlationName1 + ".column_1");
    Operator op = Operator.EQ;
    StringParameterExpression right =
      new StringParameterExpression(correlationName2 + ".column_1");
    ComparisonPredicate<String> on =
      new ComparisonPredicate<String>(left, op, right);
    expr.addTableToFrom("SubqueryTest2", correlationName2, on);
    String testString =
      "(SELECT column_1 AS COL1 FROM SubqueryTest t1 JOIN SubqueryTest2 t2 ON t1.column_1 = t2.column_1)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#addTableToFrom(java.lang.String, java.lang.String, org.tair.sql.predicate.Predicate)}
   * .
   * 
   * This method tests adding two separate JOIN clauses.
   */
  @Test
  public void testAddTableToFrom2() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    String correlationName1 = "t1";
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest", correlationName1);
    assertTrue("Could not create SubqueryExpression", expr != null);
    String correlationName2 = "t2";
    StringExpression left =
      new StringExpression(correlationName1 + ".column_1");
    Operator op = Operator.EQ;
    StringParameterExpression right =
      new StringParameterExpression(correlationName2 + ".column_1");
    ComparisonPredicate<String> on =
      new ComparisonPredicate<String>(left, op, right);
    expr.addTableToFrom("SubqueryTest2", correlationName2, on);
    String correlationName3 = "t3";
    StringExpression left3 =
      new StringExpression(correlationName2 + ".column_1");
    Operator op3 = Operator.EQ;
    StringParameterExpression right3 =
      new StringParameterExpression(correlationName3 + ".column_1");
    ComparisonPredicate<String> on3 =
      new ComparisonPredicate<String>(left3, op3, right3);
    expr.addTableToFrom("SubqueryTest3", correlationName3, on3);
    String testString =
      "(SELECT column_1 AS COL1 FROM SubqueryTest t1 JOIN SubqueryTest2 t2 ON t1.column_1 = t2.column_1 JOIN SubqueryTest3 t3 ON t2.column_1 = t3.column_1)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#getSelect()}.
   */
  @Test
  public void testGetSelect() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest", "t");
    assertTrue("Could not create SubqueryExpression", expr != null);
    String testString = "SELECT column_1 AS COL1";
    String select = expr.getSelect();
    assertTrue("Did not build correct SELECT clause: " + select,
               select.equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#getFrom()}.
   */
  @Test
  public void testGetFrom() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest", "t");
    assertTrue("Could not create SubqueryExpression", expr != null);
    String testString = "FROM SubqueryTest t";
    String from = expr.getFrom();
    assertTrue("Did not build correct FROM clause: " + from,
               from.equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.SubqueryExpression#getWhere()}.
   */
  @Test
  public void testGetWhere() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    SubqueryExpression expr =
      new SubqueryExpression(stringExpr, "SubqueryTest", "t");
    assertTrue("Could not create SubqueryExpression", expr != null);
    StringExpression left = new StringExpression(COL1);
    StringParameterExpression right = new StringParameterExpression("?");
    Operator op = Operator.EQ;
    ComparisonPredicate<String> predicate =
      new ComparisonPredicate<String>(left, op, right);
    expr.addPredicateToWhere(predicate);
    String where = expr.getWhere();
    String testString = "WHERE column_1 = ?";
    assertTrue("Did not build correct WHERE clause: " + where,
               where.equalsIgnoreCase(testString));
  }
}
