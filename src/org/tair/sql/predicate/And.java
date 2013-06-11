/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;

/**
 * The concrete composite that represents a logical conjunction of two
 * predicates.
 * 
 * @author Robert J. Muller
 */
public class And extends RelationalPredicate {

  /**
   * Create an And relational predicate between two predicates.
   * 
   * @param left the left-hand side of the AND predicate
   * @param right the right-hand side of the AND predicate
   */
  public And(Predicate left, Predicate right) {
    children.add(left);
    children.add(right);
  }

  @Override
  public void getSql(StringBuilder builder) {
	/*
    builder.append(children.get(0));
    builder.append(" AND ");
    builder.append(children.get(1));
    */
	  
	//ADD 0822121445, for write out String instead of Predicate Object
	  children.get(0).getSql(builder);
	  builder.append(" AND ");
	  children.get(1).getSql(builder);
	  
  }
}
