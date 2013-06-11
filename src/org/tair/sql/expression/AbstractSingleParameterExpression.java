/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

/**
 * An abstract implementation of the basic methods of the single parameter
 * expression interface, which is suitable for expressions in a WHERE or FROM
 * clause that have a single parameter, or for expressions that constitute a
 * parameter by itself (expression "?", in other words). The concrete
 * implementations specify the data type P and implement the bind() method
 * with the appropriate JDBC code necessary for that data type.
 * 
 * @author Robert J. Muller
 * @param <P> the data type of the parameter
 */
public abstract class AbstractSingleParameterExpression<P> extends Expression
    implements ISingleParameterExpression<P> {

  /**
   * Create a AbstractSingleParameterExpression object.
   */
  public AbstractSingleParameterExpression() {
    super();
  }

  /**
   * Create a AbstractSingleParameterExpression object with an expression.
   * 
   * @param expression the SQL expression
   */
  public AbstractSingleParameterExpression(String expression) {
    super(expression);
  }

  /**
   * Create a AbstractSingleParameterExpression object with an expression and a
   * value to bind to the parameter in the expression.
   * 
   * @param expression the SQL expression
   * @param value the value to bind to the expression
   */
  public AbstractSingleParameterExpression(String expression, P value) {
    super(expression);
    this.value = value;
  }

  /** the optional parameter value */
  protected P value;

  @Override
  public P getParameterValue() {
    return value;
  }

  @Override
  public void setParameterValue(P value) {
    this.value = value;
  }
}
