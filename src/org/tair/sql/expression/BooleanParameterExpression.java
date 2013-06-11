/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * A single-parameter expression with a Boolean parameter. You need to create
 * the object with a boolean type value that tells the object how to store the
 * value in the database. A boolean value is a SQL BIT VARYING type. Most
 * booleans are stored as 1/0, "y"/"n", or "t"/"f" values, but you can also use
 * "yes"/"no" or "true"/"false". This strategy allows you to deal with real Java
 * Boolean values outside this class but translate the data going into and out
 * of the database in the correct manner for your particular schema design, as
 * SQL does not directly support the BOOLEAN data type yet.
 * 
 * @author Robert J. Muller
 */
public class BooleanParameterExpression extends
    AbstractSingleParameterExpression<Boolean> {

  /**
   * Enumerated type specifying the kind of persistent value stored in the
   * database. Strings are case-insensitive.
   */
  public enum Type {
    /** a boolean value (SQL BIT VARYING) */
    BOOLEAN,
    /** an integer value (1 or 0) */
    INTEGER,
    /** a "yes" or "no" value */
    YES_NO,
    /** a "y" or "n" value */
    Y_N,
    /** a "true" or "false" value */
    TRUE_FALSE,
    /** a "t" or "f" value */
    T_F
  }

  private Type type = Type.INTEGER;

  /**
   * Create a BooleanParameterExpression object. This is the default constructor
   * that does nothing, yielding the "null" keyword. Leaves the default Boolean
   * type as INTEGER.
   */
  public BooleanParameterExpression() {
    super();
  }

  /**
   * Create a BooleanParameterExpression object from an expression containing a
   * parameter ? and a value to bind to that parameter on demand. Set the type
   * of the persistent boolean in the database.
   * 
   * @param expression the expression string
   * @param value the value to bind on demand
   * @param type the type of the value in the database
   */
  public BooleanParameterExpression(String expression, Boolean value, Type type) {
    super(expression, value);
    this.value = value;
    this.type = type;
  }

  /**
   * Create a BooleanParameterExpression object from an expression string
   * containing a parameter ?.
   * 
   * @param expression the expression string containing a parameter ?
   * @param type the type of the value in the database
   */
  public BooleanParameterExpression(String expression, Type type) {
    super(expression);
    this.type = type;
  }

  /**
   * Create a BooleanParameterExpression object from a String representation of
   * the Boolean value. This constructor converts the string into a true Boolean
   * value.
   * 
   * @param expression the string expression with a parameter ?
   * @param value the string representation of the value
   */
  public BooleanParameterExpression(String expression, String value) {
    super(expression);
    this.value = Utilities.convertStringToBoolean(value);
  }

  @Override
  public int bind(PreparedStatement stmt, int index) throws SQLException {
    if (value == null) {
      stmt.setNull(index, java.sql.Types.BOOLEAN);
    } else {
      switch (type) {
      case BOOLEAN:
        stmt.setBoolean(index, value);
        break;
      case INTEGER:
        stmt.setInt(index, value ? 1 : 0);
        break;
      case YES_NO:
        stmt.setString(index, value ? "YES" : "NO");
        break;
      case Y_N:
        stmt.setString(index, value ? "Y" : "N");
        break;
      case TRUE_FALSE:
        stmt.setString(index, value ? "TRUE" : "FALSE");
        break;
      case T_F:
        stmt.setString(index, value ? "T" : "F");
        break;
      default:
        stmt.setBoolean(index, value);
      }

    }
    return index++;
  }
}
