/*
 * Copyright (c) 2012 Carnegie Institution for Science. All rights reserved.
 */

package org.tair.sql.table;


import java.util.ArrayList;
import java.util.List;

import org.tair.sql.predicate.Predicate;

/**
 * A Composite pattern composite class that is a subclass of the parent
 * TableReference. The class represents the various concrete types of SQL join
 * and implements the basic join structure (two table references and a search
 * expression in an ON clause).
 * 
 * @author Robert J. Muller
 */
public abstract class Join extends TableReference {
  protected final List<TableReference> children =
    new ArrayList<TableReference>(2);
  protected final Predicate searchExpression;

  /**
   * Create a Join object.
   * 
   * @param left the left-hand-side table reference of the join
   * @param right the right-hand-side table reference of the join
   * @param searchExpression the predicate to use as as ON clause search
   *          expression
   */
  public Join(TableReference left,
              TableReference right,
              Predicate searchExpression) {
    children.add(left);
    children.add(right);
    this.searchExpression = searchExpression;
  }
}
