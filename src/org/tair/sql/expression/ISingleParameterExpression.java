/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * A SQL expression suitable for use in a SELECT list or as a value expression
 * in a WHERE clause, GROUP BY clause, or ORDER BY clause; the expression may
 * include a single parameter or may be just a parameter itself; the expression
 * always evaluates to a single value of type P
 * 
 * @author Robert J. Muller
 * @param <P> the type of the single parameter value
 */
public interface ISingleParameterExpression<P> extends IExpression {
  /**
   * Bind the value to the expression parameter ? within the prepared statement
   * using the correct JDBC binding method for the type; the concrete
   * instantiation of the template implements this method.
   * 
   * @param stmt the JDBC prepared statement to which to bind the parameter
   *          value
   * @param index the parameter index of the parameter (?) to which to bind the
   *          value
   * @return the parameter index of the next value to bind
   * @throws SQLException when there is a problem binding the value to the
   *           parameter through JDBC
   */
  int bind(PreparedStatement stmt, int index) throws SQLException;

  /**
   * Get the current value of the parameter, if any.
   * 
   * @return the value of type P
   */
  P getParameterValue();

  /**
   * Set the value of the parameter. This method does not bind the value to a
   * SQL statement but rather just places the value into the class for later
   * binding with the bind method.
   * 
   * @param value the value of type P
   */
  void setParameterValue(P value);
}
