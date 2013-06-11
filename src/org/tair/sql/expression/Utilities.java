/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

/**
 * 
 * @author Robert J. Muller
 */
public final class Utilities {

  /**
   * Convert a string into an appropriate boolean value, true or false. The
   * method converts 1, T, Y, and true (case insensitive) to "true" and anything
   * else to "false".
   * 
   * @param string the input truth value representation
   * @return true if the string contains one of the representations of "true",
   *         otherwise false
   */
  public static final Boolean convertStringToBoolean(String string) {
    Boolean bValue = null;

    if (string != null) {
      if ("1".equals(string)
          || "T".equalsIgnoreCase(string)
          || "Y".equalsIgnoreCase(string)
          || "true".equalsIgnoreCase(string)
          || "yes".equalsIgnoreCase(string)) {
        bValue = Boolean.TRUE;
      } else {
        bValue = Boolean.FALSE;
      }
    }

    return bValue;
  }

}
