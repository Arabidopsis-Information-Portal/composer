/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.expression;

/**
 * An aliased, scalar expression that appears in a SELECT list of expressions;
 * if nothing is constructed, the expression will be the NULL keyword.
 * 
 * @author Robert J. Muller
 * @param <T> the type of the value resulting from evaluation of the expression
 *          in SQL
 */
public interface ISelectExpression<T> extends IAliasedExpression,
    IScalarExpression<T> {

}
