/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;


import org.tair.sql.expression.ISubqueryExpression;


/**
 * A predicate with a nested SELECT that usually contains a correlated subquery
 * reference to the outer query; the predicate evaluates whether the SELECT
 * returns at least one row
 * 
 * @author Robert J. Muller
 */
public class ExistsPredicate extends Predicate {

  /** the subquery that appears in the EXISTS clause */
  private final ISubqueryExpression subquery;

  /**
   * Create a ExistsPredicate object.
   * 
   * @param subquery the subquery that appears in the EXISTS clause
   */
  public ExistsPredicate(ISubqueryExpression subquery) {
    this.subquery = subquery;
  }

  @Override
  public void getSql(StringBuilder builder) {
    builder.append("EXISTS ");
    subquery.getSql(builder);
  }

}
