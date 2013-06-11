/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * A single-parameter expression with a BigDecimal parameter.
 * 
 * @author Robert J. Muller
 */
public class BigDecimalParameterExpression extends
    AbstractSingleParameterExpression<BigDecimal> {

  /**
   * Create a BigDecimalParameterExpression object. This is the default
   * constructor that does nothing, yielding the "null" keyword.
   */
  public BigDecimalParameterExpression() {
    super();
  }

  /**
   * Create a BigDecimalParameterExpression object from an expression string
   * containing a parameter ?.
   * 
   * @param expression the expression string containing a parameter ?
   */
  public BigDecimalParameterExpression(String expression) {
    super(expression);
  }

  /**
   * Create a BigDecimalParameterExpression object from an expression containing
   * a parameter ? and a value to bind to that parameter on demand.
   * 
   * @param expression the expression string
   * @param value the value to bind on demand
   */
  public BigDecimalParameterExpression(String expression, BigDecimal value) {
    super(expression, value);
  }

  @Override
  public int bind(PreparedStatement stmt, int index) throws SQLException {
    if (value == null) {
      stmt.setNull(index, java.sql.Types.NUMERIC);
    } else {
      stmt.setBigDecimal(index, value);
    }
    return index++;
  }
}
