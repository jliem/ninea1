package T9A1.common;

import java.util.List;

import T9A1.server.KioskServer;
import T9A1.server.KioskServerFactory;

import junit.framework.TestCase;

public class ConnectionTest extends TestCase {

	private Connection conn;
	private KioskServer ks;

	public void setUp() {
		conn = new Connection();
		ks = KioskServerFactory.getServerInstance();
	}

	public void testSendRequest() {
		Object result = conn.sendRequest(Request.Type.item_search, "foo");

		assertNotNull(result);
	}
}
