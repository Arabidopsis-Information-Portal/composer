/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

/**
 * A checked exception that indicates that there is no such lexical variable as
 * one that a client has requested.
 * 
 * @author Robert J. Muller
 */
public class NoSuchLexicalVariableException extends Exception {

  /** Serial version UID */
  private static final long serialVersionUID = 1L;

  /**
   * Create a NoSuchLexicalVariableException object.
   */
  public NoSuchLexicalVariableException() {
    super("No such lexical variable");
  }

  /**
   * Create a NoSuchLexicalVariableException object with a message.
   * 
   * @param message the variable name
   */
  public NoSuchLexicalVariableException(String message) {
    super("No such lexical variable as " + message);
  }
}
