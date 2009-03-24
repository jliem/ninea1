package T9A1.server;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import T9A1.common.*;

public class KioskServer {
	private DBManager dbm;
	private PreparedStatement full_search;
	
	public static void main(String args[]) {
		new KioskServer();
	}
	
	protected KioskServer() {
		dbm = DBManager.getDBM();
		if (!dbm.connect()) {
			System.err.println("No DB connection, exiting.");
			System.exit(1);
		}
		initPreparedStatements();
		
		int numProducers = 1;
		int numConsumers = 1;
		startThreads(numProducers, numConsumers, this);
		
		/* DEBUG */ handleRequest("hammer");
	}

	public static void startThreads(int p, int c, KioskServer ks){
		BlockingQueue buffer = new LinkedBlockingQueue();
		BlockingQueue connections = new LinkedBlockingQueue();

		ServerConnectionManager scm = new ServerConnectionManager(ks);

		for(int i=0;i<p;i++){
			new Thread(scm.new Producer(buffer, connections)).start();
		}

		for(int i=0;i<c;i++){
			new Thread(scm.new Consumer(buffer, connections)).start();
		}
	}
	
	protected void initPreparedStatements() {
		full_search = dbm.createPreparedStatement("SELECT * FROM PRODUCT_INFO INNER JOIN PRODUCTS ON PRODUCT_INFO.PRODUCT_ID = PRODUCTS.PRODUCT_ID WHERE PRODUCT_INFO.NAME LIKE ?");
		
		if (full_search == null) {
			System.err.println("Couldn't create PreparedStatements, exiting.");
			System.exit(1);
		}
	}
	
	public List<Item> handleRequest(String query) {
		List<Item> results = new LinkedList<Item>();
		
		//TODO: parse search option
		
		synchronized (full_search) {
			try {
				full_search.setString(1, "%" + query + "%");
				
				ResultSet rs = full_search.executeQuery();  // restrict max results?
				Item i;
				while (rs.next()) {
					i = new Item();
					
					i.setId(rs.getLong("PRODUCT_ID"));
					i.setName(rs.getString("NAME"));
					i.setDescription(rs.getString("DESCRIPTION"));
					i.setInStock(rs.getInt("STOCK_STATUS") > 0);
					i.setPrice(rs.getDouble("PRICE"));
					i.setLocation(new Location(rs.getInt("LOCATION_AISLE"), rs.getInt("LOCATION_BIN")));
					
					/* DEBUG */ System.out.println("> " + i);
					
					results.add(i);
				}
			} catch (SQLException e) {
				System.out.println("Error executing search: " + e.getMessage());
				return null;
			}
		}
		
		return results;
	}
}
