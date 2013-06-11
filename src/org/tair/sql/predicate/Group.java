/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;

/**
 * A grouped predicate that represents an explicit grouping of a predicate and
 * its internal components; allows the creator to specify the correct evaluation
 * of a predicate in the presence of ambiguous, inadequate, or confusing
 * operator precedence. In SQL, this means putting parentheses around the child
 * predicate. First construct the components and then group them by creating a
 * group with the component predicate as its child. You must, therefore, create
 * the components in an order appropriate to evaluation.
 * 
 * @author Robert J. Muller
 */
public class Group extends RelationalPredicate {

  /**
   * Create a group around a predicate.
   * 
   * @param predicate the predicate to group
   */
  public Group(Predicate predicate) {
    children.add(predicate);
  }

  @Override
  public void getSql(StringBuilder builder) {
    builder.append("(");
    //builder.append(children.get(0));
  //ADD 0822121502, for write out String instead of Predicate Object
    children.get(0).getSql(builder);
    builder.append(")");
  }
}
