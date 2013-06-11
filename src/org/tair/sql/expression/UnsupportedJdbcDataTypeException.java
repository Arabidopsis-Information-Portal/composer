/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

/**
 * Specific exception for situation where the client supplies a Java object
 * of a type that the Expression system does not support.
 * 
 * @author Robert J. Muller
 */
public class UnsupportedJdbcDataTypeException extends Exception {


  /** Serial version UID */
  private static final long serialVersionUID = 1L;

  /**
   * Create a NoSuchLexicalVariableException object.
   */
  public UnsupportedJdbcDataTypeException() {
    super("The JDBC data type is not supported by TAIR SQL");
  }

  /**
   * Create a NoSuchLexicalVariableException object with a message.
   * 
   * @param message the variable name
   */
  public UnsupportedJdbcDataTypeException(String message) {
    super("The JDBC data type " + message + " is not supported by TAIR SQL.");
  }
  
  /**
   * Create a NoSuchLexicalVariableException object.
   * @param e the causing exception
   */
  public UnsupportedJdbcDataTypeException(Exception e) {
    super("The JDBC data type is not supported by TAIR SQL", e);
  }

  /**
   * Create a NoSuchLexicalVariableException object.
   * @param message the variable name
   * @param e the causing exception
   */
  public UnsupportedJdbcDataTypeException(String message, Exception e) {
    super("The JDBC data type " + message + " is not supported by TAIR SQL.", e);
  }


}
