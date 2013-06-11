/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

/**
 * A parameter value of type V; used in multiple-parameter expression value
 * lists
 * 
 * @author Robert J. Muller
 * @param <V> the type of the value
 */
public class Value<V> {
  /** the value */
  private V value = null;

  /**
   * Create a Value object.
   * 
   * @param value the value of type V
   */
  public Value(V value) {
    this.value = value;
  }

  /**
   * Get the value.
   * 
   * @return the value of type V
   */
  public V getValue() {
    return value;
  }

  /**
   * Set the value.
   * 
   * @param value the value of type V
   */
  public void setValue(V value) {
    this.value = value;
  }
}
