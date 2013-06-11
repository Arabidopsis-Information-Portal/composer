/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.tair.sql.predicate.ComparisonPredicate;
import org.tair.sql.predicate.ComparisonPredicate.Operator;


/**
 * A simple implementation of the ParameterizedSubqueryExpression abstract class
 * to be used in testing.
 * 
 * @author Robert J. Muller
 */
public class TestParameterizedSubqueryExpression extends
    ParameterizedSubqueryExpression {

  /**
   * Create a TestParameterizedSubqueryExpression object.
   */
  public TestParameterizedSubqueryExpression() {
    super();
    // Create the subquery using methods.

    // First create the SELECT list.
    ISelectExpression<String> expression = new StringExpression("column_1");
    addExpressionToSelect(expression);

    // Now create the FROM clause.
    String table = "ParameterizedSubqueryTest";
    String correlationName = "t";
    StringExpression left = new StringExpression(correlationName + ".column_1");
    Operator op = Operator.EQ;
    StringParameterExpression right =
      new StringParameterExpression(correlationName + ".column_1");
    ComparisonPredicate<String> on =
      new ComparisonPredicate<String>(left, op, right);
    addTableToFrom(table, correlationName, on);

    // Now build the WHERE clause with multiple parameterized expressions.
    left = new StringExpression("column_1");
    right = new StringParameterExpression("?");
    op = Operator.EQ;
    ComparisonPredicate<String> predicate =
      new ComparisonPredicate<String>(left, op, right);
    addPredicateToWhere(predicate);

    left = new StringExpression("column_2");
    right = new StringParameterExpression("?");
    op = Operator.EQ;
    predicate = new ComparisonPredicate<String>(left, op, right);
    addPredicateToWhere(predicate);
  }

  /**
   * Create a TestParameterizedSubqueryExpression object.
   * 
   * @param expression
   * @param table
   * @param correlationName
   */
  public TestParameterizedSubqueryExpression(IExpression expression,
                                             String table,
                                             String correlationName) {
    super(expression, table, correlationName);
  }

  /**
   * Create a TestParameterizedSubqueryExpression object.
   * 
   * @param expression
   * @param table
   */
  public TestParameterizedSubqueryExpression(IExpression expression,
                                             String table) {
    super(expression, table);
  }

  @Override
  public int bind(PreparedStatement stmt, int index) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

}
