/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;


import org.tair.sql.expression.IScalarExpression;
import org.tair.sql.expression.ISingleParameterExpression;


/**
 * A predicate with a left-hand-side expression that the predicate compares to a
 * right-hand-side expression with an operator (=, <>, <, <=, >, >=, LIKE)
 * 
 * @author Robert J. Muller
 * @param <P> the type of the expressions to compare (left and right)
 */
public class ComparisonPredicate<P> extends Predicate {
  /**
   * An enumerated type, the values of which represent the SQL comparison
   * operators (=, <>, >, <, >=, <=, LIKE)
   */
  public enum Operator {
    /** equals */
    EQ("="),
    /** not equals */
    NEQ("<>"),
    /** greater than */
    GT(">"),
    /** less than */
    LT("<"),
    /** greater than or equal to */
    GTE(">="),
    /** less than or equal to */
    LTE("<="),
    /** like (basic pattern match) */
    LIKE("LIKE");

    /** String representation of the operator in SQL */
    private String representation;

    /**
     * Create an Operator object.
     * 
     * @param representation the String representation of the operator
     */
    private Operator(String representation) {
      this.representation = representation;
    }

    @Override
    public String toString() {
      return representation;
    }
  }

  /**
   * the left-hand-side expression to compare, which is a scalar expression of
   * the same type as the parameter, type P
   */
  private IScalarExpression<P> left;
  /**
   * the operator with which to compare the two expressions; may be a symmetric
   * operator (=, <>, <, <=, >, >=) where expression order is not important or
   * an assymetric operator (LIKE) where order is important
   */
  private Operator operator;
  /**
   * the right-hand-side expression to compare, which may contain a single
   * parameter
   */
  private ISingleParameterExpression<P> right;

  /**
   * Create a ComparisonPredicate object with the left and right parts and
   * the comparison operator.
   * 
   * @param left the left-hand-side expression
   * @param operator the comparison operator
   * @param right the right-hand-side expression
   */
  public ComparisonPredicate(IScalarExpression<P> left,
                             Operator operator,
                             ISingleParameterExpression<P> right) {
    this.left = left;
    this.operator = operator;
    this.right = right;
  }

  @Override
  public void getSql(StringBuilder builder) {
    left.getSql(builder);
    builder.append(" ");
    builder.append(operator.toString());
    builder.append(" ");
    right.getSql(builder);
  }
}
