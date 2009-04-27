package T9A1.common;

import java.util.List;

import T9A1.server.KioskServer;
import T9A1.server.KioskServerFactory;

import junit.framework.TestCase;

/**
 * Tests for the connection class.
 *
 * @author Johannes Liem
 *
 */
public class ConnectionTest extends TestCase {

	private Connection conn;
	private KioskServer ks;

	public void setUp() {
		conn = new Connection();
		ks = KioskServerFactory.getServerInstance();
	}

	/**
	 * Tests sending a request.
	 */
	public void testSendRequest() {
		Request r = new Request(Request.Type.item_search);
		r.put(Request.Key.query, "foo");
		r.put(Request.Key.store_id, 1);
		Object result = conn.sendRequest(r);

		assertNotNull(result);
	}
}
