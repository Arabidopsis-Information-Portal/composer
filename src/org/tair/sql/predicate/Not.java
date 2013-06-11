/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;

/**
 * The concrete composite that represents a logical negation of another
 * predicate.
 * 
 * @author Robert J. Muller
 */
public class Not extends RelationalPredicate {

  /**
   * Create a NOT negation of a predicate.
   * 
   * @param predicate the predicate to negate
   */
  public Not(Predicate predicate) {
    children.add(predicate);
  }

  @Override
  public void getSql(StringBuilder builder) {
	
    builder.append("NOT ");
    //builder.append(children.get(0));
  //ADD 0822121454, for write out String instead of Predicate Object
    children.get(1).getSql(builder);
	
	  
  }
}
