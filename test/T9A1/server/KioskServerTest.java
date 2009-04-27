package T9A1.server;

import java.util.List;

import junit.framework.TestCase;

import T9A1.common.Item;
import T9A1.common.Request;

/**
 * Tests for the kiosk server.
 *
 * @author JL
 *
 */
public class KioskServerTest extends TestCase {
	private KioskServer ks;

	public KioskServerTest() {
		ks = KioskServerFactory.getServerInstance();
	}

	public void testHandleRequest() {
		Request r = new Request(Request.Type.item_search);
		r.put(Request.Key.query, "foo");
		r.put(Request.Key.store_id, 1);
		Object result = ks.handleRequest(r);

		assertNotNull(result);
	}
}
