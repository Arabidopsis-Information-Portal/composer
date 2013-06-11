/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 * A concrete instantiation and subclass of Expression that implements the value
 * as a BigDecimal
 * 
 * @author Robert J. Muller
 */
public class TimestampExpression extends AliasedExpression implements
ISelectExpression<Timestamp> {

  /**
   * Create a TimestampExpression object.
   */
  public TimestampExpression() {
    super();
  }

  /**
   * Create a TimestampExpression object.
   * @param expression the String SQL expression
   * @param alias the alias for the expression
   */
  public TimestampExpression(String expression, String alias) {
    super(expression, alias);
  }

  /**
   * Create a TimestampExpression object.
   * @param expression the String SQL expression
   */
  public TimestampExpression(String expression) {
    super(expression);
  }

  @Override
  public Timestamp getValue(ResultSet rs) throws SQLException {
    Timestamp tValue = null;
    if (alias != null) {
      tValue = rs.getTimestamp(alias);
    } else {
      tValue = rs.getTimestamp(expression);
    }
    return tValue;
  }
}
