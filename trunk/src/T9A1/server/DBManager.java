package T9A1.server;


import java.sql.*;


/**
 * Singleton that handles requests to the database
 *
 * @author James Poje
 */
public class DBManager {
	// singleton instance
	private static DBManager instance = null;

	// whether we can do work
	int status = 0;
	// the db connection
	Connection conn = null;


	/**
	 * Initializes the JDBC driver
	 */
	protected DBManager() {
		try {
			initializeDriver();
			status = 1;
		} catch (Exception e) {
			System.err.println("Couldn't load JDBC driver: " + e.getMessage());
		}
	}

	/**
	 * Connects to the default db
	 */
	public boolean connect() {
		return connect("javajunkies.webhop.net", 3306, "inventory", "t9a1", "****");
	}

	/**
	 * Connects up to the specified db
	 *
	 * @param url url of the db
	 * @param port port of the db
	 * @param db name of the db
	 * @param user user to login as
	 * @param pass user's password
	 */
	public boolean connect(String url, int port, String db, String user, String pass) {
		if (status < 1) {
			// driver isn't loaded, can't do anything
			System.err.println("JDBC driver not loaded, can't configure.");
			return false;
		}

		try {
			if (conn != null) {
				conn.close();
			}
			conn = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port
					+ "/" + db, user, pass);
			status = 2;
		} catch (SQLException e) {
			status = 1;
			System.err.println("Couldn't initialize db connection: " + e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * Gets the DBM instance - must call connect() on it before using for
	 *	the first time.
	 *
	 * @return DBM singleton
	 */
	public static DBManager getDBM() {
		if (instance == null) {
			// set up with defaults
			instance = new DBManager();
		}

		return instance;
	}

	/**
	 * Executes a query to the db
	 * - use getResultSet() on the returned Statement to get results
	 * - call close() on the returned Statement when done
	 *
	 * @param q the SQL query to execute
	 *
	 * @return the executed Statement
	 */
	public Statement execQuery(String q) {
		Statement statement = null;

		if (status < 2) {
			// not connected, can't do anything
			System.out.println("Error: trying to query non-connected DBManager");
			return null;
		}

		try {
			statement = conn.createStatement();
			statement.executeQuery(q);
		} catch (SQLException e) {
			System.out.println("Error executing query: " + e.getMessage());

			if (statement != null) {
				try {
					statement.close();
				} catch (Exception _e) {}
			}

			return null;
		}

		return statement;
	}


	// does a quick functionality check
	public static void quickTest() {
		DBManager dbm = DBManager.getDBM();
		if (!dbm.connect()) return;

		Statement s = dbm.execQuery("select * from testing");
		
		if (s == null) return;
		try {
			ResultSet rs = s.getResultSet();
			while (rs.next()) {
				
				System.out.println("result: [" + rs.getString(1) + "][" + rs.getInt(2) + "]");
			}
		} catch (Exception e) {
			System.out.println("SQL error: " + e.getMessage());
		}
		
		try {
			s.close();
		} catch (SQLException e) {}
	}

	/**
	 * Initialize the driver. Method is protected to allow for unit testing.
	 *
	 * @throws Exception
	 */
	protected static void initializeDriver() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	}

	/**
	 * Create and return a new DBManager instance (even if there is
	 * an existing instance).
	 *
	 * @return a new DBManager instance
	 */
	protected static DBManager getNewDBM() {
		instance = null;

		return getDBM();
	}
}
