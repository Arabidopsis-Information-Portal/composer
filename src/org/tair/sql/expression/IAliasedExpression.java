/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

/**
 * An aliased expression is an expression that has a SQL alias (AS clause). This
 * is a value expression used as an element of a SELECT list or an ORDER BY
 * clause. Aliases are case insensitive.
 * 
 * @author Robert J. Muller
 */
public interface IAliasedExpression extends IExpression {
  /**
   * Get the alias for the expression (30 characters or less, a SQL identifier)
   * 
   * @return the alias
   */
  String getAlias();

  /**
   * Set the alias for the expression (a SQL identifier with up to 30 characters
   * and following the other identifier rules)
   * 
   * @param alias the alias to set
   */
  void setAlias(String alias);
}
