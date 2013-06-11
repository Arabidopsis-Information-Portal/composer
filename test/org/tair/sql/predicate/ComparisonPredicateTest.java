/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.predicate;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.tair.sql.expression.StringExpression;
import org.tair.sql.expression.StringParameterExpression;
import org.tair.sql.predicate.ComparisonPredicate.Operator;

/**
 * 
 * @author Robert J. Muller
 */
public class ComparisonPredicateTest {

  /**
   * Test method for {@link org.tair.sql.predicate.ComparisonPredicate#getSql(java.lang.StringBuilder)}.
   */
  @Test
  public void testGetSql() {
    StringExpression left = new StringExpression("column_1");
    StringParameterExpression right = new StringParameterExpression("?");
    Operator op = Operator.EQ;
    ComparisonPredicate<String> predicate = new ComparisonPredicate<String>(left, op, right);
    assertTrue("Could not instantiate ComparisonPredicate", predicate != null);
    StringBuilder builder = new StringBuilder();
    predicate.getSql(builder);
    String testString = "column_1 = ?";
    assertTrue("Did not get correct SQL string: " + builder.toString(), builder.toString().equalsIgnoreCase(testString));
  }

  /**
   * Test method for {@link org.tair.sql.predicate.ComparisonPredicate#ComparisonPredicate(org.tair.sql.expression.IScalarExpression, org.tair.sql.predicate.ComparisonPredicate.Operator, org.tair.sql.expression.ISingleParameterExpression)}.
   */
  @Test
  public void testComparisonPredicate() {
    StringExpression left = new StringExpression("column_1");
    StringParameterExpression right = new StringParameterExpression("?");
    Operator op = Operator.EQ;
    ComparisonPredicate<String> predicate = new ComparisonPredicate<String>(left, op, right);
    assertTrue("Could not instantiate ComparisonPredicate", predicate != null);
  }

}
