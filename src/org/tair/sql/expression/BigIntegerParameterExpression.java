/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * A single-parameter expression with a BigInteger parameter
 * 
 * @author Robert J. Muller
 */
public class BigIntegerParameterExpression extends
    AbstractSingleParameterExpression<BigInteger> {

  /**
   * Create a BigIntegerParameterExpression object. This is the default
   * constructor that does nothing, yielding the "null" keyword.
   */
  public BigIntegerParameterExpression() {
    super();
  }

  /**
   * Create a BigIntegerParameterExpression object from an expression containing
   * a parameter ? and a value to bind to that parameter on demand.
   * 
   * @param expression the expression string
   * @param value the value to bind on demand
   */
  public BigIntegerParameterExpression(String expression, BigInteger value) {
    super(expression, value);
  }

  /**
   * Create a BigIntegerParameterExpression object from an expression string
   * containing a parameter ?.
   * 
   * @param expression the expression string containing a parameter ?
   */
  public BigIntegerParameterExpression(String expression) {
    super(expression);
  }

  @Override
  public int bind(PreparedStatement stmt, int index) throws SQLException {
    if (value == null) {
      stmt.setNull(index, java.sql.Types.BIGINT);
    } else {
      // Use the BigDecimal version of the integer, as JDBC does not handle
      // BigInteger values directly.
      stmt.setBigDecimal(index, new BigDecimal(value));
    }
    return index++;
  }
}
