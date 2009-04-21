package T9A1.server;


import java.sql.*;
import java.util.*;

import T9A1.common.*;


/**
 * Singleton that handles requests to the database
 *
 * @author James Poje
 */
public class DBManager {
	// singleton instance
	private static DBManager instance = null;

	// whether we can do work (2=ready)
	int status = 0;
	// the db connection
	java.sql.Connection conn = null;

	/** sql search statements */
	// searching for an item
	private PreparedStatement item_search;
	private static final String item_search_q = "SELECT * FROM PRODUCT_INFO INNER JOIN PRODUCTS ON PRODUCT_INFO.PRODUCT_ID = PRODUCTS.PRODUCT_ID WHERE (PRODUCT_INFO.NAME LIKE ? OR PRODUCT_INFO.DESCRIPTION LIKE ?)";

	// grab sale items
	private PreparedStatement sale_search;
	private static final String sale_search_q = "SELECT * FROM PRODUCT_INFO INNER JOIN PRODUCTS ON PRODUCT_INFO.PRODUCT_ID = PRODUCTS.PRODUCT_ID WHERE PRODUCTS.SALE_PRICE IS NOT NULL";

	private PreparedStatement project_search;
	private static final String project_search_q = "SELECT * FROM PROJECTS INNER JOIN PROJECT_STEPS ON PROJECTS.PROJECT_ID = PROJECT_STEPS.PROJECT_ID WHERE (PROJECT_TITLE LIKE ?) ORDER BY PROJECTS.PROJECT_ID, PROJECT_STEPS.STEP_NUM";

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

	/**
	 * Connects to the default db
	 */
	public boolean connect() {
		boolean connected = false;

		try {
			Properties props = new Properties();
			props.load(this.getClass().getClassLoader().getResourceAsStream("T9A1/server/db_config.txt"));

			connected = connect(props.getProperty("db_url"), Integer.parseInt(props.getProperty("db_port"))
					, props.getProperty("db_name"), props.getProperty("db_user"), props.getProperty("db_pass"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connected;
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
			initPreparedStatements();
		} catch (Exception e) {
			status = 1;
			System.err.println("Couldn't initialize db connection: " + e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * Executes a query to the db
	 * - use getResultSet() on the returned Statement to get results
	 * - call close() on the returned Statement when done
	 *
	 * @param q the SQL query to execute
	 *
	 * @return the executed Statement or null on failure
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

	/**
	 * Uses the server connection to create a PreparedStatement for given query string
	 *
	 * @param query the query string
	 *
	 * @return the created PreparedStatement or null on failure
	 */
	protected PreparedStatement createPreparedStatement(String query) {
		if (status < 2) {
			return null;
		}

		try {
			return conn.prepareStatement(query);
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Create prepared statements.
	 */
	protected void initPreparedStatements() {
		item_search = createPreparedStatement(item_search_q);
		sale_search = createPreparedStatement(sale_search_q);
		project_search = createPreparedStatement(project_search_q);

		if (item_search == null || sale_search == null
				|| project_search == null) {
			System.err.println("Couldn't create PreparedStatements, exiting.");
			System.exit(1);
		}
	}

	/**
	 * Searches the db for specified item
	 *
	 * @param query String containing the item to search for
	 *
	 * @return a list of Items matching the query
	 */
	public List<Item> itemSearch(String query) {
		List<Item> results = new LinkedList<Item>();

		synchronized (item_search) {
			try {
				item_search.setString(1, "%" + query + "%");
				item_search.setString(2, "%" + query + "%");

/* DEBUG */ System.out.println("item search for " + query);
				ResultSet rs = item_search.executeQuery();
				// restrict max results?
				// TODO(poje): strip trailing s from query if no results are returned on first try
				while (rs.next()) {
					results.add(inflateItemFromRS(rs));
				}
			} catch (SQLException e) {
				System.out.println("Error executing item search: " + e.getMessage());
				return null;
			}
		}

		return results;
	}

	/**
	 * Searches the db for items on sale
	 *
	 * @param query String containing the tag to search for
	 *
	 * @return a list of Items matching the query
	 */
	public List<Item> saleSearch() {
		List<Item> results = new LinkedList<Item>();

		synchronized (sale_search) {
			try {
/* DEBUG */ System.out.println("sale search for ");
				ResultSet rs = sale_search.executeQuery();
				// restrict max results?
				// TODO(poje): strip trailing s from query if no results are returned on first try
				while (rs.next()) {
					results.add(inflateItemFromRS(rs));
				}
			} catch (SQLException e) {
				System.out.println("Error executing tag search: " + e.getMessage());
				return null;
			}
		}

		return results;
	}

	public List<Project> projectSearch(String query) {
		List<Project> results = new LinkedList<Project>();
		HashMap<Long, Project> projectMap = new HashMap<Long, Project>();

		synchronized (project_search) {
			try {
				project_search.setString(1, "%" + query + "%");

				System.out.println("project search for " + query);
				ResultSet rs = project_search.executeQuery();
				// restrict max results?
				// TODO(poje): strip trailing s from query if no results are returned on first try


				while (rs.next()) {
					inflateProjectFromRS(rs, projectMap);
				}
			} catch (SQLException e) {
				System.out.println("Error executing project search: " + e.getMessage());
				return null;
			}
		}

		// Add results from the map back to the results list
		results.addAll(projectMap.values());

		return results;
	}

	/**
	 * Inflates an Item from the current result in the ResultSet
	 *
	 * @param rs ResultSet to inflate from
	 *
	 * @return an inflated Item
	 */
	protected Item inflateItemFromRS(ResultSet rs) throws SQLException {
		Item i = new Item();

		i.setId(rs.getLong("PRODUCT_ID"));
		i.setName(rs.getString("NAME"));
		i.setDescription(rs.getString("DESCRIPTION"));
		i.setInStock(rs.getInt("STOCK_STATUS") > 0);
		i.setPrice(rs.getDouble("PRICE"));
		i.setSalePrice(rs.getDouble("SALE_PRICE"));
		i.setLocation(new Location(rs.getInt("LOCATION_AISLE"), rs.getInt("LOCATION_BIN")));
		i.setImageID(rs.getLong("IMAGE_ID"));

		return i;
	}

	/**
	 * Inflates an Project from the current result in the ResultSet
	 *
	 * @param rs ResultSet to inflate from
	 *
	 * @return an inflated Project
	 */
	protected void inflateProjectFromRS(ResultSet rs,
			HashMap<Long, Project> projectMap) throws SQLException {

		long id = rs.getLong("PROJECTS.PROJECT_ID");
		String instruction = rs.getString("STEP");

		// Look up whether we've already added this project
		if (projectMap.containsKey(id)) {
			// The project's already been added, so just add instructions
			// to the existing one
			Project proj = projectMap.get(id);
			proj.addInstruction(instruction);
		} else {
			String title = rs.getString("PROJECT_TITLE");
			String toolString = rs.getString("TOOLS");
			String[] tools = toolString.split(",");

			String materialString = rs.getString("MATERIALS");
			String[] materials = materialString.split(",");

			int minutes = rs.getInt("ESTIMATED_TIME");

			Project p = new Project(title, tools, materials, null, id, minutes);
			p.addInstruction(instruction);

			projectMap.put(id, p);
		}
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
}
