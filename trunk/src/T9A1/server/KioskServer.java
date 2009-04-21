package T9A1.server;

import T9A1.common.*;

/**
 * Entry point for the kiosk server.
 *
 * @author JL
 *
 */
public class KioskServer {
	private DBManager dbm;

	public static void main(String args[]) {
		new KioskServer(DBManager.getDBM());
	}

	protected KioskServer(DBManager dbm) {
		this.dbm = dbm;

		if (!dbm.connect()) {
			System.err.println("No DB connection, exiting.");
			System.exit(1);
		}

		new ServerConnection(this);
	}

	/**
	 * Handles a request from the client.
	 *
	 * @param req the request
	 * @return a result object
	 */
	public Object handleRequest(Request req) {
		switch (req.type) {
			case item_search:
				return dbm.itemSearch((String)req.get(Request.Key.query), (Integer)req.get(Request.Key.store_id));
			case sale_search:
				return dbm.saleSearch((Integer)req.get(Request.Key.store_id));
			case project_search:
				return dbm.projectSearch((String)req.get(Request.Key.query));
			case update_request:
		}

		return null;  // any unknown requests
	}
}
