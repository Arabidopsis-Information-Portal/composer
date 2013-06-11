/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;

/**
 * A Composite pattern Component class that abstracts the component predicates
 * of a WHERE clause
 * 
 * @author Robert J. Muller
 */
public abstract class Predicate {
  /**
   * Get the SQL string that represents the predicate; each concrete class
   * implements this method, and the client uses it to get the predicate as a
   * SQL string whatever the particular component predicate is.
   * 
   * @param builder the string builder into which to append the elements of the
   *          SQL predicate; the builder may already contain the preceding part
   *          of the SQL statement
   */
  public abstract void getSql(StringBuilder builder);
}
