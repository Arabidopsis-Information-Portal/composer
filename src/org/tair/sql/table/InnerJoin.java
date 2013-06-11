/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.table;

import org.tair.sql.predicate.Predicate;



/**
 * A concrete Composite pattern Composite class that implements an inner join
 * between two table references using a search expression predicate
 * 
 * @author Robert J. Muller
 */
public class InnerJoin extends Join {

  /**
   * Create a join table reference with a left table reference and a right table
   * reference and a join specification (ON clause predicate or search
   * expression).
   * 
   * @param left the left-hand-side table reference
   * @param right the right-hand-side table reference
   * @param searchExpression the SQL predicate that links the two tables
   */
  public InnerJoin(TableReference left,
                   TableReference right,
                   Predicate searchExpression) {
    super(left, right, searchExpression);
  }

  @Override
  public void getSql(StringBuilder builder) {
    children.get(0).getSql(builder);
    builder.append(" JOIN ");
    children.get(1).getSql(builder);
    builder.append(" ON ");
    searchExpression.getSql(builder);
  }

}
