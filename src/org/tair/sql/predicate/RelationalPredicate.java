/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;


import java.util.ArrayList;
import java.util.List;


/**
 * A Composite pattern composite class that owns a set of predicates related
 * with a relational operator. The concrete subclasses implement the getSql
 * method for the individual relational operators.
 * 
 * @author Robert J. Muller
 */
public abstract class RelationalPredicate extends Predicate {
  /**
   * the children predicates of a relational predicate; NOT implies one child,
   * AND and OR imply two children
   */
  protected List<Predicate> children = new ArrayList<Predicate>(2);
}
