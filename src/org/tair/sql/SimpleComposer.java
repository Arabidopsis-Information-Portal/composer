/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql;


import java.util.List;

import org.tair.sql.expression.IExpression;
import org.tair.sql.expression.ISingleParameterExpression;


/**
 * <p>
 * A Facade pattern class that simplifies the programming interface for the SQL
 * Query Composer system to create a basic SQL statement in the format:
 * </p>
 * 
 * <pre>
 * <code>
 * SELECT expression-list
 *   FROM table-list
 *  WHERE predicate AND predicate...
 *  ORDER BY expression-list
 * </code>
 * </pre>
 * 
 * @author Robert J. Muller
 */
public class SimpleComposer extends SubqueryComposer {

  protected List<IExpression> orderBy;

  /**
   * Create a new simple composer with a SQL expression and a from-clause table
   * with no alias. This constructor initializes a single-table query that has
   * no alias for the table in the FROM clause.
   * 
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   */
  public SimpleComposer(ISingleParameterExpression<?> expression, String table) {
    super(expression, table);
  }
  
  //ADD 0814121423; to process StringExpression
  public SimpleComposer(IExpression expression, String table) {
	    super(expression, table);
	  }

  /**
   * Create a new simple composer with a SQL expression and a from-clause table
   * with an alias, permitting further expansion of the SQL into a
   * multiple-table query (a join).
   * 
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   * @param correlationName the optional correlation name for the table; if
   *          null, you cannot add more tables to the from clause, each of which
   *          requires a unique correlation name
   */
  public SimpleComposer(ISingleParameterExpression<?> expression,
                        String table,
                        String correlationName) {
    super(expression, table, correlationName);
  }
  
  //ADD 0816121118
  public SimpleComposer(IExpression expression,
          String table,
          String correlationName) {
super(expression, table, correlationName);
}

  @Override
  public String getSql() {
    StringBuilder builder = new StringBuilder();
    subquery.getSqlWithoutParens(builder);
    // Check that there is an order by specified.
    if (orderBy != null && orderBy.size() > 0) {
      // Add the order by.
      builder.append(" ORDER BY");
      for (IExpression expression : orderBy) {
        builder.append(" ");
        expression.getSql(builder);
      }
    }
    return builder.toString();
  }
}
