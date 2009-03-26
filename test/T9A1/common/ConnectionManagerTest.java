package T9A1.common;

import java.util.List;

import T9A1.server.KioskServer;
import T9A1.server.KioskServerFactory;

import junit.framework.TestCase;

public class ConnectionManagerTest extends TestCase {

	private ConnectionManager cm;
	private KioskServer ks;

	public void setUp() {
		cm = new ConnectionManager();
		ks = KioskServerFactory.getServerInstance();
	}

	public void testSendRequest() {
		List<Item> result = cm.sendRequest("foo");

		assertNotNull(result);
	}
}
