/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * A scalar expression results in a value of type T on execution of the SQL
 * statement. Most expressions are scalar expressions. The scalar expression
 * interface lets you resolve a ResultSet into a value for the expression.
 * 
 * @author Robert J. Muller
 * @param <T> the type of the value resulting from evaluation of the expression
 *          in SQL
 */
public interface IScalarExpression<T> extends IExpression {
  /**
   * Get the expression value from the result set based on the alias or on the
   * expression string itself if the alias is null or nonexistent. The concrete
   * implementation of the template implements the proper JDBC call for the type
   * T. You must call the next() method on the result set before passing it to
   * this method.
   * 
   * @param rs the JDBC result set from which to extract the value using the
   *          alias if it is not null or the expression if the alias is null;
   *          the result-set pointer must be positioned to a valid row using the
   *          next() method before calling this method
   * @return the value of type T from the result set
   * @throws SQLException when there is a problem getting the value from the
   *           result set using the expression or its alias
   */
  T getValue(ResultSet rs) throws SQLException;
}
