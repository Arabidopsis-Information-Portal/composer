/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;

/**
 * The concrete composite that represents a logical disjunction of two
 * predicates.
 * 
 * @author Robert J. Muller
 */
public class Or extends RelationalPredicate {

  /**
   * Create an Or relational predicate between two predicates.
   * 
   * @param left the left-hand side of the OR predicate
   * @param right the right-hand side of the OR predicate
   */
  public Or(Predicate left, Predicate right) {
    children.add(left);
    children.add(right);
  }

  @Override
  public void getSql(StringBuilder builder) {
    /*
	builder.append(children.get(0));
    builder.append(" OR ");
    builder.append(children.get(1));
    */
	  
	//ADD 0822121454, for write out String instead of Predicate Object
	  children.get(0).getSql(builder);
	  builder.append(" OR ");
	  children.get(1).getSql(builder);
  }
}
