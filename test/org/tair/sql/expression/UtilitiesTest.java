/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Robert J. Muller
 */
public class UtilitiesTest {

  /**
   * Test method for {@link org.tair.sql.expression.Utilities#convertStringToBoolean(java.lang.String)}.
   */
  @Test
  public void testConvertStringToBoolean() {
    assertTrue("Could not convert 1 to true", Utilities.convertStringToBoolean("1"));
    assertTrue("Could not convert Y to true", Utilities.convertStringToBoolean("Y"));
    assertTrue("Could not convert y to true", Utilities.convertStringToBoolean("y"));
    assertTrue("Could not convert T to true", Utilities.convertStringToBoolean("T"));
    assertTrue("Could not convert t to true", Utilities.convertStringToBoolean("t"));
    assertTrue("Could not convert true to true", Utilities.convertStringToBoolean("true"));
    assertTrue("Could not convert TRUE to true", Utilities.convertStringToBoolean("TRUE"));
    assertTrue("Could not convert 0 to false", !Utilities.convertStringToBoolean("0"));
    assertTrue("Could not convert N to false", !Utilities.convertStringToBoolean("N"));
    assertTrue("Could not convert n to false", !Utilities.convertStringToBoolean("n"));
    assertTrue("Could not convert F to false", !Utilities.convertStringToBoolean("F"));
    assertTrue("Could not convert f to false", !Utilities.convertStringToBoolean("f"));
    assertTrue("Could not convert FALSE to false", !Utilities.convertStringToBoolean("FALSE"));
    assertTrue("Could not convert false to false", !Utilities.convertStringToBoolean("false"));
    // Test "any other" string ("garbage") to make sure it translates to "false"
    assertTrue("Could not convert garbage to false", !Utilities.convertStringToBoolean("garbage"));
  }

}
