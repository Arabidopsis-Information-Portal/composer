/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * A single-parameter expression with a Timestamp (date) parameter
 * 
 * @author Robert J. Muller
 */
public class TimestampParameterExpression extends
    AbstractSingleParameterExpression<Timestamp> {

  /**
   * Create a TimestampParameterExpression object. This is the default
   * constructor that does nothing, yielding the "null" keyword.
   */
  public TimestampParameterExpression() {
    super();
  }

  /**
   * Create a TimestampParameterExpression object from an expression containing
   * a parameter ? and a value to bind to that parameter on demand.
   * 
   * @param expression the expression string
   * @param value the value to bind on demand
   */
  public TimestampParameterExpression(String expression, Timestamp value) {
    super(expression, value);
  }

  /**
   * Create a TimestampParameterExpression object from an expression string
   * containing a parameter ?.
   * 
   * @param expression the expression string containing a parameter ?
   */
  public TimestampParameterExpression(String expression) {
    super(expression);
  }

  @Override
  public int bind(PreparedStatement stmt, int index) throws SQLException {
    if (value == null) {
      stmt.setNull(index, java.sql.Types.TIMESTAMP);
    } else {
      stmt.setTimestamp(index, value);
    }
    return index++;
  }
}
