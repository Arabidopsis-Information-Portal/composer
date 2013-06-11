/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test the Value class.
 * 
 * @author Robert J. Muller
 */
public class ValueTest {
  
  private static final String STR1 = "a string value";
  private static final String STR2 = "a different string value";

  /**
   * Test method for {@link org.tair.sql.expression.Value#Value(java.lang.Object)}.
   */
  @Test
  public void testValue() {
    Value<Integer> intVal = new Value<Integer>(1);
    assertTrue(intVal.getValue().equals(1));
    Value<String> strVal = new Value<String>(STR1);
    assertTrue(strVal.getValue().equals(STR1));
  }

  /**
   * Test method for {@link org.tair.sql.expression.Value#getValue()}.
   */
  @Test
  public void testGetValue() {
    Value<Integer> intVal = new Value<Integer>(1);
    Integer internalInteger = intVal.getValue();
    assertTrue(internalInteger.equals(1));
  }

  /**
   * Test method for {@link org.tair.sql.expression.Value#setValue(java.lang.Object)}.
   */
  @Test
  public void testSetValue() {
    Value<String> strVal = new Value<String>("a string value");
    assertTrue(strVal.getValue().equals(STR1));
    strVal.setValue(STR2);
    assertTrue(strVal.getValue().equals(STR2));
  }

}
