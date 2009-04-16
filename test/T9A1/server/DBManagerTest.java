package T9A1.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

/**
 * Tests the database manager.
 * @author JL
 *
 */
public class DBManagerTest extends TestCase {

	private DBManager dbm;

	public void setUp() {
		dbm = DBManager.getNewDBM();
	}

	/**
	 * Test initializing the JDBC driver.
	 */
	public void testInitializeJdbcDriver() {
		Exception exception = null;

		try {
			DBManager.initializeDriver();
		} catch (Exception e) {
			exception = e;
		}

		assertNull(exception);
	}

	/**
	 * Test connecting to the database.
	 */
	public void testConnect() {
		boolean hasConnection = dbm.connect();

		assertTrue(hasConnection);
	}

	/**
	 * Test executing a query from the database.
	 */
	public void testExecQuery() throws SQLException {
		String query = "select * from testing";

		// Connect to the DB first
		dbm.connect();

		// Try executing the query
		Statement statement = dbm.execQuery(query);

		assertNotNull(statement);
		assertNotNull(statement.getResultSet());

		statement.close();
	}
}
