/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A concrete instantiation and subclass of Expression that implements the value
 * as a BigDecimal
 * 
 * @author Robert J. Muller
 */
public class BigDecimalExpression extends AliasedExpression implements
ISelectExpression<BigDecimal> {


  /**
   * Create a BigDecimalExpression object.
   */
  protected BigDecimalExpression() {
    super();
  }

  /**
   * Create a BigDecimalExpression object.
   * @param expression the String SQL expression
   * @param alias the alias for the expression
   */
  public BigDecimalExpression(String expression, String alias) {
    super(expression, alias);
  }

  /**
   * Create a BigDecimalExpression object.
   * @param expression the String SQL expression
   */
  public BigDecimalExpression(String expression) {
    super(expression);
  }

  @Override
  public BigDecimal getValue(ResultSet rs) throws SQLException {
    BigDecimal decValue = null;
    if (alias != null) {
      decValue = rs.getBigDecimal(alias);
    } else {
      decValue = rs.getBigDecimal(expression);
    }
    return decValue;
  }
}
