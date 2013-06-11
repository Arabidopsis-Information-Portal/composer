/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.table;

/**
 * A Composite pattern Component class representing a SQL table reference, which
 * can be a leaf table, a leaf subquery, or a join (the Composite); part of a
 * SQL statement that resolves to a table with columns and rows; used primarily
 * in the FROM clause of a SQL SELECT statement.
 * 
 * @author Robert J. Muller
 */
public abstract class TableReference {
  /** an optional parent Join object that owns this component */
  protected Join parent = null;

  /**
   * Get the complete SQL table reference suitable for inclusion in a FROM
   * clause, including any children of a Join, appending it to the specified
   * builder
   * 
   * @param builder the string builder into which to append the elements of the
   *          SQL expression; the builder may already contain the preceding part
   *          of the SQL statement
   */
  public abstract void getSql(StringBuilder builder);

  /**
   * Set the parent Join for the component.
   * @param parent the parent Join object
   */
  public void setParent(Join parent) {
    this.parent = parent;
  }
}
