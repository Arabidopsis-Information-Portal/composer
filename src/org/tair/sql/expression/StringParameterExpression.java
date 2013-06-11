/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * A SQL expression with a single String parameter
 * 
 * @author Robert J. Muller
 */
public class StringParameterExpression extends
    AbstractSingleParameterExpression<String> {

  /**
   * Create a StringParameterExpression object. This is the default
   * constructor that does nothing, yielding the "null" keyword.
   */
  public StringParameterExpression() {
    super();
  }

  /**
   * Create a StringParameterExpression object from an expression containing
   * a parameter ? and a value to bind to that parameter on demand.
   * 
   * @param expression the expression string
   * @param value the value to bind on demand
   */
  public StringParameterExpression(String expression, String value) {
    super(expression, value);
  }

  /**
   * Create a StringParameterExpression object from an expression string
   * containing a parameter ?.
   * 
   * @param expression the expression string containing a parameter ?
   */
  public StringParameterExpression(String expression) {
    super(expression);
  }

  @Override
  public int bind(PreparedStatement stmt, int index) throws SQLException {
    if (value == null) {
      stmt.setNull(index, java.sql.Types.VARCHAR);
    } else {
      stmt.setString(index, value);
    }
    return index++;
  }
}
