/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A SQL expression suitable for use in a SELECT list or as a value expression
 * in a WHERE clause, GROUP BY clause, or ORDER BY clause. The expression may
 * contain any number of lexical variables in the format ${variable_name}, and
 * the getSQL() method will substitute in the current value from the lexical
 * variable map. Lexical variable names must be a sequence of word characters
 * (\w regular expression match), alphanumeric with no whitespace.
 * 
 * @author Robert J. Muller
 */
public abstract class Expression implements IExpression {
  /** the String SQL expression */
  protected final String expression;

  /** the lexical variable map */
  protected final Map<String, String> lexicalVariables;

  private static final Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}");

  /**
   * Create an Expression using a private expression assignment (static member
   * declaration) or other method in a subclass. This is the default
   * constructor. You use this constructor when constructing an object of a
   * subclass but never when constructing just a straight expression; you should
   * always use the string constructor for that.
   */
  protected Expression() {
    lexicalVariables = new HashMap<String, String>();
    expression = null; // Assumes will be set by later constructor of child
  }

  /**
   * Create an Expression object with a specified expression. If there are
   * lexical variables, the concrete subclass constructor should add the keys to
   * the map with null values in the subclass constructor that calls this one.
   * 
   * @param expression the String SQL expression
   */
  public Expression(String expression) {
    if (expression == null) {
      throw new RuntimeException("Must supply non-null expression string");
    }
    lexicalVariables = new HashMap<String, String>();
    addVariablesToMap(expression);
    this.expression = expression;
  }

  /**
   * Create a Expression object from another expression object. You can use
   * this constructor to copy an expression or to create a different type of
   * expression.
   * 
   * @param expression the expression object
   */
  public Expression(IExpression expression) {
    if (expression == null) {
      throw new RuntimeException("Must supply non-null expression string");
    }
    lexicalVariables = expression.getLexicalVariables();
    this.expression = expression.getExpression();
  }

  /**
   * Parse out the variables from the input expression and put the variable
   * names into the internal map of lexical variables.
   * 
   * @param expression
   */
  private void addVariablesToMap(String expression) {
    // Tokenize the expression to extract the variables.
    Matcher matcher = pattern.matcher(expression);
    boolean matched = true;
    while (matched) {
      matched = matcher.matches();
      if (matched) {
        String variable = matcher.group(1);
        // Set the initial value to null.
        lexicalVariables.put(variable, null);
        // end the loop, only one lexical variable permitted
        matched = false;
      }
    }
  }

  @Override
  public void getSql(StringBuilder builder) {
    String replaced = expression;
    // Substitute any lexical variables into the string.
    for (String key : lexicalVariables.keySet()) {
      Pattern pattern = Pattern.compile("\\$\\{" + key + "\\}");
      Matcher matcher = pattern.matcher(expression);
      Boolean matched = matcher.matches();
      if (matched) {
        String value = lexicalVariables.get(key);
        if (value == null) {
          throw new RuntimeException("Null value for variable "
                                     + key
                                     + " in expression "
                                     + expression);
        }
        replaced = matcher.replaceAll(value);
      } else {
        throw new RuntimeException("No such variable "
                                   + key
                                   + " in expression "
                                   + expression);
      }
    }
    // Append the substituted string to the builder.
    builder.append(replaced);
  }

  @Override
  public String getVariable(String variable)
      throws NoSuchLexicalVariableException {
    if (!lexicalVariables.containsKey(variable)) {
      throw new NoSuchLexicalVariableException(variable);
    }
    return lexicalVariables.get(variable);
  }

  @Override
  public void setVariable(String variable, String value)
      throws NoSuchLexicalVariableException {
    if (!lexicalVariables.containsKey(variable)) {
      throw new NoSuchLexicalVariableException(variable);
    }
    lexicalVariables.put(variable, value);
  }

  @Override
  public String getExpression() {
    return expression;
  }

  @Override
  public Map<String, String> getLexicalVariables() {
    Map<String, String> copy = Collections.unmodifiableMap(lexicalVariables);
    return copy;
  }
}
