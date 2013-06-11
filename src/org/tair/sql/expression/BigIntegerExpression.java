/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A concrete instantiation and subclass of AliasedExpression that implements
 * the value as a BigInteger
 * 
 * @author Robert J. Muller
 */
public class BigIntegerExpression extends AliasedExpression implements
    ISelectExpression<BigInteger> {

  /**
   * Create a BigIntegerExpression object.
   */
  public BigIntegerExpression() {
    super();
  }

  /**
   * Create a BigIntegerExpression object.
   * @param expression the String SQL expression
   * @param alias the alias for the expression
   */
  public BigIntegerExpression(String expression, String alias) {
    super(expression, alias);
  }

  /**
   * Create a BigIntegerExpression object.
   * @param expression the String SQL expression
   */
  public BigIntegerExpression(String expression) {
    super(expression);
  }

  @Override
  public BigInteger getValue(ResultSet rs) throws SQLException {
    BigInteger intValue = null;
    BigDecimal decValue = null;
    if (alias != null) {
      decValue = rs.getBigDecimal(alias);
    } else {
      decValue = rs.getBigDecimal(expression);
    }
    if (decValue != null) {
      intValue = decValue.toBigInteger();
    }
    return intValue;
  }
}
