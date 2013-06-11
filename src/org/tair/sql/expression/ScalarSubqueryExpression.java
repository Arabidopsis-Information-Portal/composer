/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A scalar subquery expression that returns a value of a specified type
 * 
 * @author Robert J. Muller
 * @param <V> the type of the scalar value the expression returns
 */
public abstract class ScalarSubqueryExpression<V> extends SubqueryExpression
    implements ISelectExpression<V> {

  /** the optional SELECT-list alias for the expression */
  private String alias = null;

  /**
   * Create a SubqueryExpression object. This is the default constructor; you
   * use this when a concrete subclass builds a subquery by a static string
   * variable or some other mechanism that does not require inputs to
   * construction
   */
  protected ScalarSubqueryExpression() {
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
  public ScalarSubqueryExpression(IExpression expression, String table) {
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
  public ScalarSubqueryExpression(IExpression expression,
                                  String table,
                                  String correlationName) {
    super(expression, table, correlationName);
  }

  @Override
  public String getAlias() {
    return alias;
  }

  @Override
  public void setAlias(String alias) {
    this.alias = alias;
  }
  
  @Override
  public void getSql(StringBuilder builder) {
    // Generate complete query surrounded by parentheses
    builder.append("(");
    getSqlWithoutParens(builder);
    builder.append(")");
    // Since this is a select expression, add the alias if it's there.
    if (alias != null && alias.length() > 0) {
      builder.append(" AS ");
      builder.append(alias);
    }
  }

  @Override
  public V getValue(ResultSet rs) throws SQLException {
    if (rs == null) {
      // Result set must be present.
      throw new SQLException("No SQL result set for string subquery retrieval");
    }
    if (alias == null) {
      // Need to have an alias to get a value from a result set
      throw new SQLException("No string subquery alias set");
    }

    // Get the value from the result set, test for null value, and set the value
    // to null if the value was null in the result set.
    V value = getValue(rs, alias);
    if (rs.wasNull()) {
      value = null;
    }
    return value;
  }

  /**
   * Get the value of type V from a SQL ResultSet using an alias
   * 
   * @param rs the result set
   * @param alias the alias
   * @return the value of type V
   * @throws SQLException when there is a problem extracting the value from the
   *           ResultSet
   */
  abstract protected V getValue(ResultSet rs, String alias) throws SQLException;
}
