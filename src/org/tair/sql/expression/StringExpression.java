/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

import java.sql.ResultSet;
import java.sql.SQLException;




/**
 * A concrete instantiation and subclass of Expression that implements the value
 * as a String
 * 
 * @author Robert J. Muller
 */
public class StringExpression extends AliasedExpression implements ISelectExpression<String> {

  /**
   * Create a StringExpression object.
   */
  protected StringExpression() {
    super();
  }

  /**
   * Create a StringExpression object.
   * @param expression
   */
  public StringExpression(String expression) {
    super(expression);
  }
  
  /**
   * Create a StringExpression object.
   * @param expression the String SQL expression
   * @param alias the alias for the expression
   */
  public StringExpression(String expression, String alias) {
    super(expression, alias);
  }

  @Override
  public String getValue(ResultSet rs) throws SQLException {
    String value = null;
    if (alias != null) {
      value = rs.getString(alias);
    } else {
      // Use the expression itself.
      value = rs.getString(expression);
    }
    return value;
  }
}
