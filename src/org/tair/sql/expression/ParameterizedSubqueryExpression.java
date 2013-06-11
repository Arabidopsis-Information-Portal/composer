/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * An abstract subquery expression that has one or more parameters (JDBC ?
 * expressions) in the query, usually in the WHERE clause; subclass this class
 * and override the bind() method with the appropriate JDBC binding code.
 * 
 * @author Robert J. Muller
 */
public abstract class ParameterizedSubqueryExpression extends
    SubqueryExpression implements IMultipleParameterExpression {

  /** the list of values for the parameters */
  private List<Value<Object>> values = new ArrayList<Value<Object>>();

  /**
   * Create a ParameterizedSubqueryExpression object. Use this constructor
   * to create an object that has its SQL statement hard-coded in the 
   * concrete class constructor so that you don't pass any constructor
   * arguments to the object to create it.
   */
  protected ParameterizedSubqueryExpression() {
    super();
  }

  /**
   * Create a ParameterizedSubqueryExpression object.
   * 
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   * @param correlationName the optional alias for the table; if null, you
   *          cannot add more tables to the from clause, each of which requires
   *          a unique alias
   */
  public ParameterizedSubqueryExpression(IExpression expression,
                                         String table,
                                         String correlationName) {
    super(expression, table, correlationName);
  }

  /**
   * Create a ParameterizedSubqueryExpression object.
   * 
   * @param expression the first expression in the SELECT list being composed
   * @param table the first table in the FROM clause being composed
   */
  public ParameterizedSubqueryExpression(IExpression expression, String table) {
    super(expression, table);
  }

  @Override
  public abstract int bind(PreparedStatement stmt, int index)
      throws SQLException;

  @Override
  public List<Value<Object>> getValueList() {
    return Collections.unmodifiableList(values);
  }

  @Override
  public void setValueList(List<Value<Object>> values) {
    this.values = values;
  }
}
