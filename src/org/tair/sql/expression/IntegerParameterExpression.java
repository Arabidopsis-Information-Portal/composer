/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * A single-parameter expression with an Integer parameter
 * 
 * @author Robert J. Muller
 */
public class IntegerParameterExpression extends
    AbstractSingleParameterExpression<Integer> {

  /**
   * Create an IntegerParameterExpression object. This is the default
   * constructor that does nothing, yielding the "null" keyword.
   */
  public IntegerParameterExpression() {
    super();
  }

  /**
   * Create an IntegerParameterExpression object from an expression containing
   * a parameter ? and a value to bind to that parameter on demand.
   * 
   * @param expression the expression string
   * @param value the value to bind on demand
   */
  public IntegerParameterExpression(String expression, Integer value) {
    super(expression, value);
  }

  /**
   * Create an IntegerParameterExpression object from an expression string
   * containing a parameter ?.
   * 
   * @param expression the expression string containing a parameter ?
   */
  public IntegerParameterExpression(String expression) {
    super(expression);
  }

  @Override
  public int bind(PreparedStatement stmt, int index) throws SQLException {
    if (value == null) {
      stmt.setNull(index, java.sql.Types.INTEGER);
    } else {
      stmt.setInt(index, value);
    }
    return index++;
  }
}
