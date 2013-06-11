/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.util.ArrayList;
import java.util.List;

import org.tair.sql.predicate.And;
import org.tair.sql.predicate.Predicate;
import org.tair.sql.table.InnerJoin;
import org.tair.sql.table.Table;
import org.tair.sql.table.TableReference;


/**
 * An implementation of the ISubqueryExpression interface and a concrete
 * subclass of the Expression class that represents a SQL expression that is a
 * subquery. The methods let you build the subquery in parts. Use objects of
 * this class for subqueries that return row data rather than a scalar value;
 * use the subclasses for scalar-value subqueries.
 * 
 * @author Robert J. Muller
 */
public class SubqueryExpression extends Expression implements
    ISubqueryExpression {

  /** the list of expressions in the SELECT list */
  private final List<IExpression> select = new ArrayList<IExpression>(1);

  /** the table reference that constitutes the FROM clause */
  private TableReference from;

  /** the search condition that constitutes the WHERE clause */
  private Predicate where;

  /**
   * Create a SubqueryExpression object. This is the default constructor; you
   * use this when a concrete subclass builds a subquery by a static string
   * variable or some other mechanism that does not require inputs to
   * construction
   */
  protected SubqueryExpression() {
    super();
    from = null;
  }
 
//ADD 0827121700
  public SubqueryExpression(IExpression expression) {
	    super();
	    if (expression == null ) {
	      throw new RuntimeException("Must supply an expression");
	    }
	    select.add(expression);
	    from = null;
	  }

  /**
   * Create a SubqueryExpression object with a single SELECT-list expression and
   * a single, non-JOIN table reference. Use this constructor for subqueries of
   * a single table.
   * 
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   */
  public SubqueryExpression(IExpression expression, String table) {
    super();
    if (expression == null || table == null) {
      throw new RuntimeException("Must supply an expression and source table");
    }
    select.add(expression);
    from = new Table(table, null);
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
  public SubqueryExpression(IExpression expression,
                            String table,
                            String correlationName) {
    super();
    if (expression == null || table == null) {
      throw new RuntimeException("Must supply an expression and source table");
    }
    select.add(expression);
    from = new Table(table, correlationName);
  }

  @Override
  public void addExpressionToSelect(ISelectExpression<?> expression) {
    select.add(expression);

  }

  @Override
  public void addPredicateToWhere(Predicate predicate) {
    /*
	if (where == null) {
      where = predicate;
    } else {
      where = new And(where, predicate);
    }
    */
	  //ADD 0822121818 just want a whole composite.
    where = predicate;
  }

//ADD 0827121607
  @Override
  public void addTableToFrom(TableReference tablereference) {
	  from = tablereference;
	  
  }
  
  @Override
  public void addTableToFrom(String table, String correlationName, Predicate on) {
    TableReference tableRef = new Table(table, correlationName);
    if (from == null) {
      // No table yet, add the table reference with no join at this point.
      from = tableRef;
    } else {
      // Existing table, add a joined table using the default inner join.
      from = new InnerJoin(from, tableRef, on);
    }
  }

  @Override
  public String getSelect() {
    StringBuilder builder = new StringBuilder("");
    generateSelectClause(builder);
    return builder.toString();
  }

  /**
   * Generate the SELECT clause with a list of all the expressions.
   * 
   * @param builder the string builder to which to append the SELECT clause
   */
  private void generateSelectClause(StringBuilder builder) {
    builder.append("SELECT ");
    String sep = "";
    for (IExpression expr : select) {
      builder.append(sep);
      expr.getSql(builder);
      sep = ", ";
    }
  }

  @Override
  public String getFrom() {
    StringBuilder builder = new StringBuilder("FROM ");
    // Append the SQL table expression.
    from.getSql(builder);
    return builder.toString();
  }

  @Override
  public String getWhere() {
    String clause = "";
    if (where != null) {
      StringBuilder builder = new StringBuilder("WHERE ");
      // Append the SQL search expression.
      where.getSql(builder);
      clause = builder.toString();
    }
    return clause;
  }

  @Override
  public void getSql(StringBuilder builder) {
    // Generate complete query surrounded by parentheses
    builder.append("(");
    getSqlWithoutParens(builder);
    builder.append(")");
  }

  @Override
  public void getSqlWithoutParens(StringBuilder builder) {
    if (from == null) {
      throw new RuntimeException("Invalid subquery expression: no FROM clause present");
    }
    generateSelectClause(builder);
    builder.append(" FROM ");
    from.getSql(builder);
    if (where != null) {
      builder.append(" WHERE ");
      where.getSql(builder);
    }
  }
}
