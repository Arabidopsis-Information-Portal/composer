/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

/**
 * An expression that has an alias; aliases appear in expressions in a SELECT
 * list
 * 
 * @author Robert J. Muller
 */
public class AliasedExpression extends Expression implements IAliasedExpression {

  /** the alias for the expression */
  protected String alias = null;

  /**
   * Create an Expression using a private expression assignment (static member
   * declaration) or other method in the concrete subclass. This is the default
   * constructor.
   */
  protected AliasedExpression() {
    super();
  }

  /**
   * Create an AliasedExpression object with a specified expression but no
   * alias. If there are lexical variables, the concrete subclass constructor
   * should add the keys to the map with null values in the subclass constructor
   * that calls this one.
   * 
   * @param expression the String SQL expression
   */
  public AliasedExpression(String expression) {
    super(expression);
  }

  /**
   * Create an AliasedExpression object with a specified expression and an
   * alias. If there are lexical variables, the concrete subclass constructor
   * should add the keys to the map with null values in the subclass constructor
   * that calls this one.
   * 
   * @param expression the String SQL expression
   * @param alias the alias for the expression
   */
  public AliasedExpression(String expression, String alias) {
    super(expression);
    this.alias = alias;
  }
  
  /**
   * Create an AliasedExpression object with a specified expression and an
   * alias. In this constructor, the expression is a complete IExpression
   * object which may be converted to an AliasedExpression.
   * 
   * @param expression the String SQL expression
   * @param alias the alias for the expression
   */
  public AliasedExpression(IExpression expression, String alias) {
    super(expression);
    this.alias = alias;
  }

  @Override
  public String getAlias() {
    return alias;
  }

  @Override
  public void setAlias(String alias) {
    this.alias = alias;
  }

  @Override
  public void getSql(StringBuilder builder) {
    super.getSql(builder);
    if (alias != null && alias.length() > 0) {
      builder.append(" AS ");
      builder.append(alias);
    }
  }
}
