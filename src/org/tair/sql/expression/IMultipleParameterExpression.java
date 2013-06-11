/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


/**
 * An interface for SQL expressions that contain multiple bind parameters; these
 * will usually be WHERE clause expressions
 * 
 * @author Robert J. Muller
 */
public interface IMultipleParameterExpression extends IExpression {
  /**
   * Bind the value to the expression parameter ? within the prepared statement
   * using the correct JDBC binding method for the type; the concrete
   * instantiation of the template implements this method. The method returns
   * the last index bound.
   * 
   * @param stmt the JDBC prepared statement containing the SQL statement
   *          parameter to which to bind a value
   * @param index the 1-based index of the parameter to set within the complete
   *          SQL statement
   * @return the last index bound
   * @throws SQLException when there is a problem binding a value to a parameter
   */
  int bind(PreparedStatement stmt, int index) throws SQLException;

  /**
   * Get the list of values ordered by the binding order of the SQL statement
   * parameters to which they map.
   * 
   * @return the value list
   */
  List<Value<Object>> getValueList();

  /**
   * Set the list of values to bind to the SQL expression ordered by the index
   * order of the parameters to which they map.
   * 
   * @param values the list of values to bind
   */
  void setValueList(List<Value<Object>> values);
}
