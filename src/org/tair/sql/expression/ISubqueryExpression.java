/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import org.tair.sql.predicate.Predicate;
//ADD 0827121457
import org.tair.sql.table.TableReference;


/**
 * An interface for a SQL expression that is a subquery of some kind
 * 
 * @author Robert J. Muller
 */
public interface ISubqueryExpression extends IExpression {
  /**
   * Add a SQL expression to the list of expressions currently making up the
   * SELECT clause of the SQL query.
   * 
   * @param expression the expression of a specific type to add to the SELECT
   *          list clause
   */
  void addExpressionToSelect(ISelectExpression<?> expression);

  /**
   * Add a predicate to the current predicate making up the WHERE clause of the
   * SQL statement. If the current predicate is null, this method sets it to the
   * specified predicate; if the current predicate is not null, then this method
   * adds the predicate by combining it with the current predicate with an AND
   * relational operator.
   * 
   * @param predicate the predicate to add to the WHERE clause
   */
  void addPredicateToWhere(Predicate predicate);

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
  void addTableToFrom(String table, String correlationName, Predicate on);

  //ADD 0827121457
  void addTableToFrom(TableReference tablereference);

  /**
   * Get the SELECT clause of the subquery as a String.
   * 
   * @return the SELECT clause
   */
  String getSelect();

  /**
   * Get the FROM clause of the subquery as a String.
   * 
   * @return the FROM clause
   */
  String getFrom();

  /**
   * Get the WHERE clause of the subquery as a String.
   * 
   * @return the WHERE clause
   */
  String getWhere();

  /**
   * Get the subquery SQL without the parentheses, enabling a subclass to get
   * the SQL for a standalone SQL SELECT.
   * 
   * @param builder the builder to which to add the SQL
   */
  void getSqlWithoutParens(StringBuilder builder);
}
