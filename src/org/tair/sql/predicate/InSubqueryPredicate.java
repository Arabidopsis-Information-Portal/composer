/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;


import org.tair.sql.expression.IScalarExpression;
import org.tair.sql.expression.ScalarSubqueryExpression;


/**
 * A predicate with a nested SELECT that tests whether the scalar value in the
 * left-hand-side expression is in the set of scalar values returned by the
 * nested SELECT: expr IN (SELECT ...)
 * 
 * @author Robert J. Muller
 * @param <T> the type of the scalar values being compared
 */
public class InSubqueryPredicate<T> extends Predicate {

  /** the left-hand-side expression to which to compare the subquery */
  private final IScalarExpression<T> left;

  /** the subquery that appears in the EXISTS clause */
  private final ScalarSubqueryExpression<T> subquery;

  /**
   * Create a ExistsPredicate object.
   * 
   * @param left the left-hand expression to which to compare the subquery
   * 
   * @param subquery the subquery that appears in the EXISTS clause
   */
  public InSubqueryPredicate(IScalarExpression<T> left,
                             ScalarSubqueryExpression<T> subquery) {
    this.left = left;
    this.subquery = subquery;
  }

  @Override
  public void getSql(StringBuilder builder) {
    left.getSql(builder);
    builder.append(" IN ");
    subquery.getSql(builder);
  }
}
