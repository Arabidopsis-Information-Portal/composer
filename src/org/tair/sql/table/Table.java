/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.table;

/**
 * A Leaf class in the Composite pattern that represents a single table with an
 * optional correlation name
 * 
 * @author Robert J. Muller
 */
public class Table extends TableReference {
  /** the name of the table */
  private String tableName;
  /** the alias to use for the table in external references to it */
  private String correlationName;

  /**
   * Create a Table object using the specified table name and an optional
   * correlation name. Use the correlation name when you are specifying a table
   * that will become part of a join.
   * 
   * @param tableName the name of the table
   * @param correlationName the alias to use for the table in external
   *          references to it
   */
  public Table(String tableName, String correlationName) {
    this.tableName = tableName;
    this.correlationName = correlationName;
  }

  @Override
  public void getSql(StringBuilder builder) {
    builder.append(tableName);
    if (correlationName != null && correlationName.length() > 0) {
      builder.append(" ");
      builder.append(correlationName);
    }
  }

}
