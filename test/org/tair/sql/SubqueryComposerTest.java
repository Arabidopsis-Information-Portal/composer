/**
 * 
 */
package org.tair.sql;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tair.sql.expression.*;
import org.tair.sql.*;

/**
 * @author dkhuang
 *
 */
public class SubqueryComposerTest {

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#SubqueryComposer(org.tair.sql.expression.IExpression, java.lang.String)}.
	 */
	@Test
	public void testSubqueryComposerIExpressionString() {
		//fail("Not yet implemented");
		StringExpression sepure = new StringExpression("xyz");	
		SubqueryComposer sqrc = new SubqueryComposer(sepure, "table_2");
		assertTrue("Could not create SubqueryExpression", sqrc != null);
		String testString = "(SELECT xyz FROM table_2)";
		String sqrc_sql = sqrc.getSql();
		assertTrue("Did not build correct subquery expression: "
                + sqrc_sql,
                sqrc_sql.equalsIgnoreCase(testString));
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#SubqueryComposer(org.tair.sql.expression.IExpression, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSubqueryComposerIExpressionStringString() {
		//fail("Not yet implemented");
		StringExpression sepure = new StringExpression("xyz");
		SubqueryComposer sqrc_correlation = new SubqueryComposer(sepure, "table_3", "correlation");
		assertTrue("Could not create SubqueryExpression", sqrc_correlation != null);
		String testString = "(SELECT xyz FROM table_3 correlation)";
		String sqrc_sql_correlation = sqrc_correlation.getSql();
		assertTrue("Did not build correct subquery expression: "
                + sqrc_sql_correlation,
                sqrc_sql_correlation.equalsIgnoreCase(testString));
		
	}
	
	

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#SubqueryComposer(java.lang.String, org.tair.sql.expression.IExpression, java.lang.String)}.
	 */
	@Test
	public void testSubqueryComposerStringIExpressionString() {
		//fail("Not yet implemented");
		StringExpression sepure = new StringExpression("xyz");
		SubqueryComposer sqrc_alias = new SubqueryComposer("alias", sepure, "table_4");
		assertTrue("Could not create SubqueryExpression", sqrc_alias != null);
		String testString = "(SELECT xyz AS alias FROM table_4)";
		String sqrc_sql_alias = sqrc_alias.getSql();
		assertTrue("Did not build correct subquery expression: "
                + sqrc_sql_alias,
                sqrc_sql_alias.equalsIgnoreCase(testString));
		
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#SubqueryComposer(java.lang.String, org.tair.sql.expression.IExpression, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSubqueryComposerStringIExpressionStringString() {
		//fail("Not yet implemented");
		StringExpression sepure = new StringExpression("xyz");
		SubqueryComposer sqrc_alias_correlation = new SubqueryComposer("alias", sepure, "table_5", "correlation");
		assertTrue("Could not create SubqueryExpression", sqrc_alias_correlation != null);
		String testString = "(SELECT xyz AS alias FROM table_5 correlation)";
		String sqrc_sql_alias_correlation = sqrc_alias_correlation.getSql();
		assertTrue("Did not build correct subquery expression: "
                + sqrc_sql_alias_correlation,
                sqrc_sql_alias_correlation.equalsIgnoreCase(testString));
		
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#SubqueryComposer(org.tair.sql.expression.ParameterizedSubqueryExpression, java.util.List)}.
	 */
	@Test
	public void testSubqueryComposerParameterizedSubqueryExpressionListOfValueOfObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#addExpressionToSelect(org.tair.sql.expression.ISelectExpression)}.
	 */
	@Test
	public void testAddExpressionToSelect() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#addPredicateToWhere(org.tair.sql.predicate.Predicate)}.
	 */
	@Test
	public void testAddPredicateToWhere() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#addTableToFrom(java.lang.String, java.lang.String, org.tair.sql.predicate.Predicate)}.
	 */
	@Test
	public void testAddTableToFrom() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#getSql()}.
	 */
	@Test
	public void testGetSql() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#getSqlWithoutParens(java.lang.StringBuilder)}.
	 */
	@Test
	public void testGetSqlWithoutParens() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#getSelect()}.
	 */
	@Test
	public void testGetSelect() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#getFrom()}.
	 */
	@Test
	public void testGetFrom() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tair.sql.SubqueryComposer#getWhere()}.
	 */
	@Test
	public void testGetWhere() {
		fail("Not yet implemented");
	}

}
