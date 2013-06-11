/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A scalar subquery expression that returns a String value
 * 
 * @author Robert J. Muller
 */
public class IntegerSubqueryExpression extends
    ScalarSubqueryExpression<Integer> {
  /**
   * Create a SubqueryExpression object. This is the default constructor; you
   * use this when a concrete subclass builds a subquery by a static string
   * variable or some other mechanism that does not require inputs to
   * construction
   */
  protected IntegerSubqueryExpression() {
    super();
  }

  /**
   * Create a SubqueryExpression object with a single SELECT-list expression and
   * a single, non-JOIN table reference. Use this constructor for subqueries of
   * a single table.
   * 
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   */
  public IntegerSubqueryExpression(IExpression expression, String table) {
    super(expression, table);
  }

  /**
   * Create a SubqueryExpression object with a single SELECT-list expression, a
   * single table name, and a correlation name for the table. Use this
   * constructor for JOIN subqueries.
   * 
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   * @param correlationName the optional alias for the table; if null, you
   *          cannot add more tables to the from clause, each of which requires
   *          a unique alias
   */
  public IntegerSubqueryExpression(IExpression expression,
                                   String table,
                                   String correlationName) {
    super(expression, table, correlationName);
  }

  @Override
  protected Integer getValue(ResultSet rs, String alias) throws SQLException {
    return rs.getInt(alias);
  }
}
