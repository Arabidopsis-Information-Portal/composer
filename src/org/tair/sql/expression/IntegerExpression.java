/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A concrete instantiation and subclass of Expression that implements the value
 * as an Integer
 * 
 * @author Robert J. Muller
 */
public class IntegerExpression extends AliasedExpression implements
    ISelectExpression<Integer> {

  /**
   * Create an IntegerExpression object.
   */
  protected IntegerExpression() {
    super();
  }

  /**
   * Create an IntegerExpression object.
   * 
   * @param expression the String SQL expression
   * @param alias the alias for the expression
   */
  public IntegerExpression(String expression, String alias) {
    super(expression, alias);
  }

  /**
   * Create an IntegerExpression object.
   * 
   * @param expression the String SQL expression
   */
  public IntegerExpression(String expression) {
    super(expression);
  }

  @Override
  public Integer getValue(ResultSet rs) throws SQLException {
    Integer i = null;
    if (alias != null) {
      i = rs.getInt(alias);
    } else {
      i = rs.getInt(expression);
    }
    if (rs.wasNull()) {
      i = null;
    }
    return i;
  }
}
