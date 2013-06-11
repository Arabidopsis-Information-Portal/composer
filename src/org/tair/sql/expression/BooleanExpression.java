/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A concrete instantiation and subclass of Expression that implements the value
 * as a Boolean
 * 
 * @author Robert J. Muller
 */
public class BooleanExpression extends AliasedExpression implements
ISelectExpression<Boolean> {

  /**
   * Create a BooleanExpression object.
   */
  public BooleanExpression() {
    super();
  }

  /**
   * Create a BooleanExpression object.
   * @param expression the String SQL expression
   * @param alias the alias for the expression
   */
  public BooleanExpression(String expression, String alias) {
    super(expression, alias);
  }

  /**
   * Create a BooleanExpression object.
   * @param expression the String SQL expression
   */
  public BooleanExpression(String expression) {
    super(expression);
  }

  @Override
  public Boolean getValue(ResultSet rs) throws SQLException {
    // Deals with "fake" booleans like Y/N, T/F, 1/0
    String bValue = null;
    if (alias != null) {
      bValue = rs.getString(alias);
    } else {
      bValue = rs.getString(expression);
    }

    return Utilities.convertStringToBoolean(bValue);
  }
}
