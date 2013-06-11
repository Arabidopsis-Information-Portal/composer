/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.tair.sql.ConnectionTest;
import org.tair.sql.predicate.ComparisonPredicate;
import org.tair.sql.predicate.ComparisonPredicate.Operator;


/**
 * Test the abstract class ParameterizedSubqueryExpression using the concrete
 * test class TestParameterizedSubqueryExpression. There needs to be a MySQL
 * test table called ParameterizedSubqueryExpression with three columns,
 * column_1 VARCHAR(10), column_2 VARCHAR(10), and column_3 INTEGER. The last
 * column is currently unused but can be used to test a correlated subquery to
 * the table MainTest.
 * 
 * @author Robert J. Muller
 */
public class ParameterizedSubqueryExpressionTest extends ConnectionTest {
  private static final Logger logger =
    Logger.getLogger(ParameterizedSubqueryExpressionTest.class);
  /** Test column name */
  private static final String COL1 = "column_1";
  /** Test alias */
  private static final String ALIAS1 = "col1";
  /** Test string */
  private static final String VAL1 = "aaa";

  /** Test string */
  private static final String VAL2 = "bbb";

  /**
   * Test method for
   * {@link org.tair.sql.expression.ParameterizedSubqueryExpression#ParameterizedSubqueryExpression()}
   * .
   */
  @Test
  public void testParameterizedSubqueryExpression() {
    ParameterizedSubqueryExpression expr =
      new TestParameterizedSubqueryExpression();
    assertTrue("Could not create hard-coded TestParameterizedSubqueryExpression",
               expr != null);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.ParameterizedSubqueryExpression#ParameterizedSubqueryExpression(org.tair.sql.expression.IExpression, java.lang.String)}
   * .
   */
  @Test
  public void testParameterizedSubqueryExpressionIExpressionString() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    TestParameterizedSubqueryExpression expr =
      new TestParameterizedSubqueryExpression(stringExpr,
                                              "ParameterizedSubqueryTest");
    assertTrue("Could not create SubqueryExpression", expr != null);
    String testString =
      "(SELECT column_1 AS COL1 FROM ParameterizedSubqueryTest)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.ParameterizedSubqueryExpression#ParameterizedSubqueryExpression(org.tair.sql.expression.IExpression, java.lang.String, java.lang.String)}
   * .
   */
  @Test
  public void testParameterizedSubqueryExpressionIExpressionStringString() {
    StringExpression stringExpr = new StringExpression(COL1, ALIAS1);
    TestParameterizedSubqueryExpression expr =
      new TestParameterizedSubqueryExpression(stringExpr,
                                              "ParameterizedSubqueryTest",
                                              "t");
    assertTrue("Could not create SubqueryExpression", expr != null);
    String testString =
      "(SELECT column_1 AS COL1 FROM ParameterizedSubqueryTest t)";
    StringBuilder builder = new StringBuilder();
    expr.getSql(builder);
    assertTrue("Did not build correct subquery expression: "
                   + builder.toString(),
               builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.ParameterizedSubqueryExpression#bind(java.sql.PreparedStatement, int)}
   * .
   */
  @Test
  public void testBind() {
    ParameterizedSubqueryExpression expr =
      new TestParameterizedSubqueryExpression();
    StringBuilder builder =
      new StringBuilder("SELECT column_1 FROM MainTest WHERE EXISTS ");
    expr.getSql(builder);
    
    // Create the value list for two parameter values and set it into the expr.
    Value<Object> value1 = new Value<Object>(VAL1);
    Value<Object> value2 = new Value<Object>(VAL2);
    List<Value<Object>> values = new ArrayList<Value<Object>>(2);
    values.add(value1);
    values.add(value2);
    expr.setValueList(values);

    // Bind the values and execute.
    PreparedStatement stmt;
    try {
      stmt = connection.prepareStatement(builder.toString());
      expr.bind(stmt, 1);
      assertTrue(true);
    } catch (SQLException e) {
      logger.error("Could not bind Subquery values", e);
      fail(e.getMessage());
    }
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.ParameterizedSubqueryExpression#getValueList()}
   * .
   */
  @Test
  public void testGetValueList() {
    ParameterizedSubqueryExpression expr =
      new TestParameterizedSubqueryExpression();
    StringBuilder builder =
      new StringBuilder("SELECT column_1 FROM MainTest WHERE EXISTS ");
    expr.getSql(builder);
    
    // Create the value list for two parameter values and set it into the expr.
    Value<Object> value1 = new Value<Object>(VAL1);
    Value<Object> value2 = new Value<Object>(VAL2);
    List<Value<Object>> values = new ArrayList<Value<Object>>(2);
    values.add(value1);
    values.add(value2);
    expr.setValueList(values);
    
    int which = 1;
    for (Value<Object> value : expr.getValueList()) {
      String stringObject = (String)value.getValue();
      if (which == 1) {
        assertTrue("Not correct value: " + stringObject, VAL1.equals(stringObject));
      } else {
        assertTrue("Not correct value: " + stringObject, VAL2.equals(stringObject));
      }
      which++;
    }
    assertTrue("Retrieved " + (which - 1) + " values, not 2.", which == 3);
  }

  /**
   * Test method for
   * {@link org.tair.sql.expression.ParameterizedSubqueryExpression#setValueList(java.util.List)}
   * .
   */
  @Test
  public void testSetValueList() {
    ParameterizedSubqueryExpression expr =
      new TestParameterizedSubqueryExpression();
    StringBuilder builder =
      new StringBuilder("SELECT column_1 FROM MainTest WHERE EXISTS ");
    expr.getSql(builder);
    
    // Create the value list for two parameter values and set it into the expr.
    Value<Object> value1 = new Value<Object>(VAL1);
    Value<Object> value2 = new Value<Object>(VAL2);
    List<Value<Object>> values = new ArrayList<Value<Object>>(2);
    values.add(value1);
    values.add(value2);
    expr.setValueList(values);
    List<Value<Object>> list = expr.getValueList();
    assertTrue("No list", list != null);
    assertTrue("Not correct number of values in list", list.size() == 2);
  }

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

}
