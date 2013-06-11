package org.tair.sql;


import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;


/**
 * An abstract superclass that provides a MySQL connection for testing. Use the
 * protected connection class member to connect to the test database. Each test
 * should use a distinct table in the database if possible.
 * 
 * This class assumes a local MySQL database on port 3306 named "test",
 * accessed by a "test" user with password "test". It's for testing :).
 * 
 * @author Robert J. Muller
 */
public abstract class ConnectionTest {

  protected static Connection connection = null;
  //private static String URL = "jdbc:mysql://localhost:3306/test";
  private static String URL = "jdbc:mysql://localhost:3306/Composer2";
  //private static String USER = "test";
  //private static String PW = "test";
  private static String USER = "root";
  private static String PW = "";

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    connection = DriverManager.getConnection(URL, USER, PW);
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    if (connection != null) {
      connection.close();
    }
  }

}