/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.table;

import org.tair.sql.expression.ISubqueryExpression;

/**
 * A Leaf class in the Composite pattern that represents a subquery with an
 * required correlation name
 * 
 * @author Robert J. Muller
 */
public class Subquery extends TableReference {
  /** the name of the table */
  private ISubqueryExpression subquery;
  /** the alias to use for the table in external references to it */
  private String correlationName;

  /**
   * Create a subquery used as a table expression in a from clause. This kind
   * of table expression requires the correlation name, so the constructor will
   * throw an exception if you supply a null or empty correlation name.
   * 
   * @param subquery the subquery expression
   * @param correlationName the alias to use for the subquery in external
   *          references to it; this is required
   */
  public Subquery(ISubqueryExpression subquery, String correlationName) {
    if (correlationName == null || correlationName.length() == 0) {
      throw new RuntimeException("A subquery table reference must have a correlation name");
    }
    this.subquery = subquery;
    this.correlationName = correlationName;
  }

  @Override
  public void getSql(StringBuilder builder) {
    // Get the subquery in parentheses.
    subquery.getSql(builder);
    // Correlation name guaranteed to be present. No AS required by SQL,
    // Oracle fails if you include AS here.
    builder.append(" ");
    builder.append(correlationName);
  }

}
