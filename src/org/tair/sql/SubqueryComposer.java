package org.tair.sql;
/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

import java.util.List;

import org.tair.sql.expression.AliasedExpression;
import org.tair.sql.expression.IExpression;
import org.tair.sql.expression.ISelectExpression;
import org.tair.sql.expression.ISubqueryExpression;
import org.tair.sql.expression.ParameterizedSubqueryExpression;
import org.tair.sql.expression.SubqueryExpression;
import org.tair.sql.expression.Value;
import org.tair.sql.predicate.Predicate;


/**
 * A Facade pattern class that simplifies the programming interface for the SQL
 * Query Composer system to create a basic SQL statement in the format:
 * 
 * <p>
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
 * </p>
 * 
 * The subquery composer composes a subquery expression. You can use getSql() to
 * get back a subquery expression in parentheses, or you can use the
 * getSelect(), getFrom(), and getWhere() methods to get back the components of
 * the subquery (for example, to use as a standalone SELECT).
 * 
 * @author Robert J. Muller
 */
public class SubqueryComposer {
  /** The subquery being composed */
  protected final ISubqueryExpression subquery;

  /**
   * Create a SubqueryComposer object with a single expression and a source
   * table. Use this method to create a non-JOIN SELECT statement.
   * 
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   */
  public SubqueryComposer(IExpression expression, String table) {
    this.subquery = new SubqueryExpression(expression, table);
  }

  /**
   * Create a new subquery composer with a SQL expression and a from-clause
   * table with an alias, permitting further expansion of the SQL into a
   * multiple-table query (a join). Use this constructor to create a
   * multiple-table subquery for use in a WHERE or ORDER BY clause (not a SELECT
   * list).
   * 
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   * @param correlationName the optional correlation name for the table; if
   *          null, you cannot add more tables to the from clause, each of which
   *          requires a unique correlation name
   */
  public SubqueryComposer(IExpression expression,
                          String table,
                          String correlationName) {
    this.subquery =
      new SubqueryExpression(expression, table, correlationName);
  }

  /**
   * Create a new subquery composer with a subquery alias, a SQL expression, and
   * a from-clause table with no correlation name. This constructor initializes
   * a single-table select subquery with no correlation name and a select-list
   * alias. Use this query to build a single-table subquery that is going into a
   * query select list.
   * 
   * @param alias the alias of the first expression in the SELECT list
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   */
  public SubqueryComposer(String alias, IExpression expression, String table) {
    IExpression newExpression = new AliasedExpression(expression, alias);
    this.subquery = new SubqueryExpression(newExpression, table);
  }

  /**
   * Create a new subquery composer to compose a multiple-table, select-list
   * subquery with a select-list alias, a SQL expression, and a from-clause
   * table with a correlation name, permitting further expansion of the SQL into
   * a multiple-table query (a join). This constructor initializes a
   * multiple-table select subquery with a correlation name on the table and a
   * select-list alias on the whole subquery. Use this constructor to build a
   * multiple-table subquery that is going into a query select list.
   * 
   * @param alias the alias of the first expression in the SELECT list
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   * @param correlationName the optional correlation name for the table; if
   *          null, you cannot add more tables to the from clause, each of which
   *          requires a unique correlation name
   */
  public SubqueryComposer(String alias,
                          IExpression expression,
                          String table,
                          String correlationName) {
    IExpression newExpression = new AliasedExpression(expression, alias);
    this.subquery =
      new SubqueryExpression(newExpression, table, correlationName);
  }

  /**
   * Create a new subquery composer with a parameterized subquery SQL expression
   * and a list of parameter values. This constructor lets you create a composer
   * subclass that contains a user-specified parameterized subquery class
   * instance that has the JDBC code necessary to binding the parameters. The
   * parameter list lets you add parameter values to the expression.
   * 
   * @param expression the subquery expression that contains the parameters;
   *          required
   * @param parameters a list of parameter value objects to bind to the
   *          parameters; if null, the original parameter list in the input
   *          parameterized subquery remains valid
   */
  protected SubqueryComposer(ParameterizedSubqueryExpression expression,
                             List<Value<Object>> parameters) {
    if (expression == null) {
      throw new RuntimeException("Must supply a concrete parameterized subquery instance");
    }
    this.subquery = expression;
    if (parameters != null) {
      expression.setValueList(parameters);
    }
  }

  /**
   * Add a SQL expression to the list of expressions currently making up the
   * SELECT clause of the SQL query
   * 
   * @param expression the expression to add to the SELECT list clause
   */
  public void addExpressionToSelect(ISelectExpression<?> expression) {
    subquery.addExpressionToSelect(expression);
  }

  /**
   * Add a predicate to the current predicate making up the WHERE clause of the
   * SQL statement; this method adds the predicate by combining it with the
   * current predicate with an AND relational operator.
   * 
   * @param predicate the predicate to add to the WHERE clause
   */
  public void addPredicateToWhere(Predicate predicate) {
    subquery.addPredicateToWhere(predicate);
  }

  /**
   * Add a table expression to the ordered list of table expressions currently
   * in the from clause using a JOIN operator with an ON clause; the table
   * expression consists of the table name or SELECT statement in parentheses,
   * an alias, and an optional ON clause predicate. There will always be an
   * existing table in the SQL statement created with the constructor. If that
   * table does not have an alias, this method returns a RuntimeException
   * (single-table query).
   * 
   * @param table the name of a table or a complete SQL statement in parentheses
   *          that returns a table of data
   * @param correlationName the correlation name for the table expression
   * @param on the ON clause predicate that relates the newly JOINed table to a
   *          previously JOINed table; if this value is null, this must be the
   *          first table in the FROM clause
   */
  public void addTableToFrom(String table, String correlationName, Predicate on) {
    subquery.addTableToFrom(table, correlationName, on);
  }

  /**
   * Get the complete SQL subquery expression based on the current select list,
   * from clause, where clause, and order by clause.
   * 
   * @return the SQL subquery expression
   */
  public String getSql() {
    StringBuilder builder = new StringBuilder();
    subquery.getSql(builder);
    return builder.toString();
  }
  
  protected void getSqlWithoutParens(StringBuilder builder) {
    subquery.getSqlWithoutParens(builder);
  }

  /**
   * Get the SELECT clause of the subquery expression.
   * 
   * @return the SELECT clause string
   */
  public String getSelect() {
    return subquery.getSelect();
  }

  /**
   * Get the FROM clause of the subquery expression.
   * 
   * @return the FROM clause string
   */
  public String getFrom() {
    return subquery.getFrom();
  }

  /**
   * Get the WHERE clause of the subquery expression.
   * 
   * @return the WHERE clause string
   */
  public String getWhere() {
    return subquery.getWhere();
  }
}
