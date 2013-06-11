/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

//ADD 0828121642
import java.math.BigDecimal;
import java.math.BigInteger;
import java.lang.Boolean;
import java.sql.Timestamp;


/**
 * An abstract implementation of the IMultipleParameterExpression interface and
 * a subclass of the Expression class that implements the value binding
 * mechanism. Concrete subclasses must implement the abstract method getSql().
 * 
 * @author Robert J. Muller
 */
public abstract class AbstractMultipleParameterExpression extends Expression
    implements IMultipleParameterExpression {
  private List<Value<Object>> values;

  //ADD and Comment out     0828121553
  /*
  public AbstractMultipleParameterExpression(List<Value<Object>> values) {
	    this.values = values;
	    if (values == null ) {
	      throw new RuntimeException("Must supply an expression");
	    }
	   
	  }
  */
  /*
  @Override
  public int bind(PreparedStatement stmt, int index) throws SQLException {
    for (Value<Object> value : values) {
      if (value.getValue() instanceof Integer) {
        stmt.setInt(index, (Integer)value.getValue());
      } else if (value.getValue() instanceof String) {
        stmt.setString(index, (String)value.getValue());
      }
      index++;
    }
    // Back the index up to the last one set.
    return --index;
  }
  */
  //ADD 0828121655, add BigDecimal, Boolean, Timestamp in existing Integer & String 
  @Override
  public int bind(PreparedStatement stmt, int index) throws SQLException {
    for (Value<Object> value : values) {
      if (value.getValue() instanceof Integer) {
        stmt.setInt(index, (Integer)value.getValue());
      } else if (value.getValue() instanceof String) {
        stmt.setString(index, (String)value.getValue());
      }
      else if (value.getValue() instanceof BigDecimal) {
          stmt.setBigDecimal(index, (BigDecimal)value.getValue());
        }
      else if (value.getValue() instanceof Boolean) {
          stmt.setBoolean(index, (Boolean)value.getValue());
        } 
      else if (value.getValue() instanceof Timestamp) {
          stmt.setTimestamp(index, (Timestamp)value.getValue());
        }      

      index++;
    }
    // Back the index up to the last one set.
    return --index;
  }

  
  @Override
  public List<Value<Object>> getValueList() {
    return Collections.unmodifiableList(values);
  }

  @Override
  public void setValueList(List<Value<Object>> values) {
    this.values = values;
  }

  /*
  @Override
  public abstract void getSql(StringBuilder builder);
  */
  //ADD 0828121426, implement it. 
  @Override
  public void getSql(StringBuilder builder) {
	  for (Value<Object> value : values) {
		  String stringObject = (String) value.getValue();
		  builder.append(stringObject);
		  builder.append(" ");
        }
  }
  
}
