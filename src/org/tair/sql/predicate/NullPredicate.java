/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;


import org.tair.sql.expression.ISingleParameterExpression;


/**
 * A predicate that tests a scalar expression for being NULL or NOT NULL
 * 
 * @author Robert J. Muller
 * @param <P> the type of the parameter in the single-parameter expression
 */
public class NullPredicate<P> extends Predicate {

  /** the expression to test for being NULL or NOT NULL */
  private ISingleParameterExpression<P> expression;

  /**
   * a boolean value indicating whether to test for NULL (true) or NOT NULL
   * (false)
   */
  private Boolean isNull = Boolean.TRUE;

  /**
   * Create a NullPredicate object that tests for NULL.
   * 
   * @param expression the expression to test for being NULL or NOT NULL
   */
  public NullPredicate(ISingleParameterExpression<P> expression) {
    this.expression = expression;
  }

  /**
   * Create a NullPredicate object that tests for NULL or NOT NULL as specified.
   * 
   * @param expression the expression to test for being NULL or NOT NULL
   * @param isNull true to test for NULL, false to test for NOT NULL
   */
  public NullPredicate(ISingleParameterExpression<P> expression, Boolean isNull) {
    this.expression = expression;
    this.isNull = isNull;
  }

  @Override
  public void getSql(StringBuilder builder) {
    expression.getSql(builder);
    if (isNull) {
      builder.append(" IS NULL");
    } else {
      builder.append("IS NOT NULL");
    }
  }
}
