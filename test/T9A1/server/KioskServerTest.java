package T9A1.server;

import java.util.List;

import junit.framework.TestCase;

import T9A1.common.Item;

public class KioskServerTest extends TestCase {
	private KioskServer ks;

	public KioskServerTest() {
		ks = KioskServerFactory.getServerInstance();
	}

	public void testHandleRequest() {
		List<Item> result = ks.handleRequest("foo");

		assertNotNull(result);
	}
}
