/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;


import java.sql.SQLException;
import java.util.List;

import org.tair.sql.expression.IScalarExpression;


/**
 * A predicate with a left-hand-side expression that evaluates to one element in
 * a right-hand-side list of expressions
 * 
 * @author Robert J. Muller
 * @param <T> the type of the scalar expressions in the predicate
 */
public class InListPredicate<T> extends Predicate {

  /** the left-hand-side expression to compare to a list of expressions */
  private IScalarExpression<T> left;

  /**
   * a list of the expressions that appear in the IN clause, all of which must
   * be of the same type as the left expression; there can be at most 1,000
   * expressions in the list
   */
  private List<IScalarExpression<T>> expressions;

  /**
   * Create an InListPredicate with a left-hand-side expression that evaluates
   * to one element in a right-hand-side list of expressions.
   * 
   * @param left the left-hand-side expression to compare to a list of
   *          expressions
   * @param expressions a list of the expressions that appear in the IN clause,
   *          all of which must be of the same type as the left expression;
   *          there can be at most 1,000 expressions in the list
   * @throws SQLException when there are more than 1,000 expressions in the list
   */
  public InListPredicate(IScalarExpression<T> left,
                         List<IScalarExpression<T>> expressions)
      throws SQLException {
    this.left = left;
    if (expressions.size() <= 1000) {
      this.expressions = expressions;
    } else
      throw new SQLException("Too many expressions for IN clause, 1000 max");
  }

  @Override
  public void getSql(StringBuilder builder) {
    left.getSql(builder);
    builder.append(" IN (");
    String sep = "";
    for (IScalarExpression<T> expression : expressions) {
      builder.append(sep);
      expression.getSql(builder);
      sep = ", ";
    }
    builder.append(")");
  }

}
